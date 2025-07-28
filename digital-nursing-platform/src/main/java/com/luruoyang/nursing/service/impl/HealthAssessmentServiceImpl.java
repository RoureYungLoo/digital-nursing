package com.luruoyang.nursing.service.impl;

import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.IdcardUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.luruoyang.AliOssService;
import com.luruoyang.IBaiduService;
import com.luruoyang.common.constant.StatusConstants;
import com.luruoyang.common.core.domain.AjaxResult;
import com.luruoyang.common.exception.base.BaseException;
import com.luruoyang.common.utils.PDFUtil;
import com.luruoyang.nursing.constants.RedisKey;
import com.luruoyang.nursing.entity.domain.HealthAssessment;
import com.luruoyang.nursing.entity.dto.HealthAssessmentDto;
import com.luruoyang.nursing.entity.vo.health.HealthReportVo;
import com.luruoyang.nursing.service.IBedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luruoyang.nursing.mapper.HealthAssessmentMapper;
import com.luruoyang.nursing.service.IHealthAssessmentService;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * 健康评估Service业务层处理
 *
 * @author luruoyang
 * @date 2025-07-28
 */
@Service
public class HealthAssessmentServiceImpl extends ServiceImpl<HealthAssessmentMapper, HealthAssessment> implements IHealthAssessmentService {
  @Autowired
  private HealthAssessmentMapper healthAssessmentMapper;

  @Autowired
  private AliOssService aliOssService;

  @Autowired
  private RedisTemplate<Object, Object> redisTemplate;

  @Autowired
  private IBaiduService baiduService;

  /**
   * 查询健康评估
   *
   * @param id 健康评估主键
   * @return 健康评估
   */
  @Override
  public HealthAssessment selectHealthAssessmentById(Long id) {
    return this.getById(id);
  }

  /**
   * 查询健康评估列表
   *
   * @param healthAssessment 健康评估
   * @return 健康评估
   */
  @Override
  public List<HealthAssessment> selectHealthAssessmentList(HealthAssessment healthAssessment) {
    return healthAssessmentMapper.selectHealthAssessmentList(healthAssessment);
  }

  /**
   * 新增健康评估
   *
   * @param dto 健康评估
   * @return 结果
   */
  @Override
  public Long insertHealthAssessment(HealthAssessmentDto dto) {
    // 获取prompt
    String prompt = fillPrompt(dto);

    // 调用模型
    String res = baiduService.callQianFanAI(prompt);
    log.warn("AI Model Res: " + res);
    redisTemplate.opsForValue().set(RedisKey.HEALTH_REPORT + "test", res);

    // 解析数据
    HealthReportVo hrv = parseFromJson(res);

    // 保存数据
    String idCard = dto.getIdCard();
    double healthScore = hrv.getRiskDistribution().getHealthy();
    HealthAssessment assessment = HealthAssessment.builder()
        .elderName(dto.getElderName())
        .idCard(idCard)
        .birthDate(LocalDate.parse(IdcardUtil.getBirth(idCard), DateTimeFormatter.ofPattern("yyyyMMdd")))
        .age(IdcardUtil.getAgeByIdCard(idCard))
        .gender(IdcardUtil.getGenderByIdCard(idCard))
        .healthScore("" + healthScore)
        .riskLevel(hrv.getHealthAssessment().getRiskLevel())
        .suggestionForAdmission(healthScore >= 60 ? StatusConstants.ADMISSION_PASS : StatusConstants.ADMISSION_NOT_PASS)
        .nursingLevelName(getNursingLevelName(healthScore))
        .admissionStatus(StatusConstants.ADMISSION_NOT_CHECKED_IN)
        .totalCheckDate(hrv.getTotalCheckDate())
        .physicalExamInstitution(dto.getPhysicalExamInstitution())
        .physicalReportUrl(dto.getPhysicalReportUrl())
        .assessmentTime(LocalDateTime.now())
        .reportSummary(hrv.getSummarize())
        .diseaseRisk(JSONUtil.toJsonStr(hrv.getRiskDistribution()))
        .abnormalAnalysis(JSONUtil.toJsonStr(hrv.getAbnormalData()))
        .systemScore(JSONUtil.toJsonStr(hrv.getSystemScore()))
        .build();

    this.save(assessment);

    return assessment.getId();
  }


  /**
   * 修改健康评估
   *
   * @param healthAssessment 健康评估
   * @return 结果
   */
  @Override
  public int updateHealthAssessment(HealthAssessment healthAssessment) {
    return this.updateById(healthAssessment) ? 1 : 0;
  }

  /**
   * 批量删除健康评估
   *
   * @param ids 需要删除的健康评估主键
   * @return 结果
   */
  @Override
  public int deleteHealthAssessmentByIds(Long[] ids) {
    return this.removeByIds(Arrays.asList(ids)) ? 1 : 0;

  }

  /**
   * 删除健康评估信息
   *
   * @param id 健康评估主键
   * @return 结果
   */
  @Override
  public int deleteHealthAssessmentById(Long id) {
    return this.removeById(id) ? 1 : 0;
  }

  /**
   * 上传体检报告
   *
   * @param file     体检报告
   * @param idCardNo 身份证号
   * @throws IOException IO异常
   */
  @Override
  public String uploadFile(MultipartFile file, String idCardNo) throws IOException {
    String content = PDFUtil.pdfToString(file.getInputStream());
    if (StringUtils.hasText(content)) {
      String url = aliOssService.upload(file);
      // 缓存体检报告
      redisTemplate.opsForValue().set(RedisKey.HEALTH_REPORT + idCardNo, content);
      return url;
    }
    return null;
  }

  /**
   * 填充 Prompt
   *
   * @param dto dto
   * @return Prompt
   */
  private String fillPrompt(HealthAssessmentDto dto) {
    String idCard = dto.getIdCard();
    String report = (String) redisTemplate.opsForValue().get(RedisKey.HEALTH_REPORT + idCard);
    if (!StringUtils.hasText(report)) {
      throw new BaseException("体检报告内容获取失败, 请重新提交体检报告");
    }

    redisTemplate.delete(RedisKey.HEALTH_REPORT + idCard);

    String prompt = loadPrompt();
    prompt = prompt.replaceAll("promptcontent", report);
    return prompt;
  }

  private static String loadPrompt() {
    InputStream in = ClassLoader.getSystemClassLoader().getResourceAsStream("prompt/PromptHealthAssement.txt");
    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
    StringBuilder sb = new StringBuilder();
    String line;
    try {
      while ((line = reader.readLine()) != null) {
        sb.append(line);
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return sb.toString();
  }

  private HealthReportVo parseFromJson(String jsonStr) {
    jsonStr = jsonStr.replaceAll("```json", "");
    jsonStr = jsonStr.replaceAll("```", "");
    HealthReportVo reportVo = JSONUtil.toBean(jsonStr, HealthReportVo.class);
    return reportVo;
  }

  private String getNursingLevelName(double healthScore) {
    String level = "不建议入住";
    if (healthScore >= 0 && healthScore <= 100) {
      if (healthScore < 60) {
        level = "特技护理等级";
      } else if (healthScore < 70) {
        level = "一级护理等级";
      } else if (healthScore < 80) {
        level = "二级级护理等级";
      } else if (healthScore < 90) {
        level = "三级级护理等级";
      }
    } else {
      throw new BaseException("Invalid HealthScore");
    }
    return level;
  }
}
