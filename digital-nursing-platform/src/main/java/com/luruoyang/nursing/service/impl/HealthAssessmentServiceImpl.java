package com.luruoyang.nursing.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdcardUtil;
import cn.hutool.json.JSONUtil;
import com.luruoyang.AliOssService;
import com.luruoyang.IBaiduService;
import com.luruoyang.common.constant.StatusConstants;
import com.luruoyang.common.exception.base.BaseException;
import com.luruoyang.common.utils.DateUtils;
import com.luruoyang.common.utils.PDFUtil;
import com.luruoyang.common.utils.StringUtils;
import com.luruoyang.nursing.constants.RedisKey;
import com.luruoyang.nursing.entity.dto.HealthAssessmentDto;
import com.luruoyang.nursing.entity.vo.health.HealthReportVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luruoyang.nursing.mapper.HealthAssessmentMapper;
import com.luruoyang.nursing.entity.domain.HealthAssessment;
import com.luruoyang.nursing.service.IHealthAssessmentService;
import org.springframework.web.multipart.MultipartFile;

/**
 * 健康评估Service业务层处理
 *
 * @author luruoyang
 * @date 2025-08-04
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
    // String res = baiduService.callQianFanAI(prompt);
    String res = "{ \"totalCheckDate\": \"2023-10-10\", \"healthAssessment\": { \"riskLevel\": \"risk\", \"healthIndex\": 65.00 }, \"riskDistribution\": { \"healthy\": 60.00, \"caution\": 25.00, \"risk\": 10.00, \"danger\": 5.00, \"severeDanger\": 0.00 }, \"abnormalData\": [ { \"conclusion\": \"循环系统提示风险\", \"examinationItem\": \"心率\", \"result\": \"92次/分\", \"referenceValue\": \"<100次/分\", \"unit\": \"次/分\", \"interpret\": \"心率略快，偶发早搏，提示可能存在心脏负担加重或心脏疾病风险。\", \"advice\": \"建议进行心电图和心脏科随访，监测心率变化，避免过度劳累和情绪激动，保持健康的生活方式。\" }, { \"conclusion\": \"循环系统提示风险\", \"examinationItem\": \"皮肤弹性，四肢末梢凉\", \"result\": \"皮肤弹性差，四肢末梢凉\", \"referenceValue\": \"皮肤弹性好，四肢末梢温暖\", \"unit\": \"-\", \"interpret\": \"皮肤弹性差，四肢末梢凉，提示可能的循环问题，可能与心脏功能或血管疾病有关。\", \"advice\": \"建议进行血管功能评估，注意保暖，适当锻炼以促进血液循环。\" }, { \"conclusion\": \"感官系统提示风险\", \"examinationItem\": \"视力\", \"result\": \"左眼0.3，右眼0.4\", \"referenceValue\": \"正常视力\", \"unit\": \"-\", \"interpret\": \"视力下降，符合老视特征，提示可能存在年龄相关的视力退化。\", \"advice\": \"建议定期复查视力，考虑配戴老花镜以改善视力。\" }, { \"conclusion\": \"消化系统提示风险\", \"examinationItem\": \"肝脏实质回声\", \"result\": \"略粗糙，考虑轻度脂肪肝可能\", \"referenceValue\": \"实质回声均匀\", \"unit\": \"-\", \"interpret\": \"肝脏实质回声略粗糙，提示可能存在轻度脂肪肝，与饮食习惯、生活方式等因素有关。\", \"advice\": \"建议调整饮食结构，减少油腻食物摄入，增加运动量，定期复查肝脏B超。\" }, { \"conclusion\": \"消化系统提示风险\", \"examinationItem\": \"胆囊壁\", \"result\": \"毛糙，考虑慢性胆囊炎可能\", \"referenceValue\": \"胆囊壁光滑\", \"unit\": \"-\", \"interpret\": \"胆囊壁毛糙，提示可能存在慢性胆囊炎，可能与胆囊结石、胆囊感染等因素有关。\", \"advice\": \"建议避免油腻食物，保持规律饮食，必要时进行胆囊功能检查和治疗。\" }, { \"conclusion\": \"消化系统提示风险\", \"examinationItem\": \"脾脏\", \"result\": \"轻度增大\", \"referenceValue\": \"脾脏大小正常\", \"unit\": \"-\", \"interpret\": \"脾脏轻度增大，可能与感染、血液系统疾病或自身免疫性疾病有关。\", \"advice\": \"建议进一步检查以明确脾脏增大的原因，并监测脾脏情况。\" }, { \"conclusion\": \"泌尿系统提示风险\", \"examinationItem\": \"右肾\", \"result\": \"右肾下极见一大小约5mm的无回声区，考虑小囊肿可能\", \"referenceValue\": \"肾脏无囊肿\", \"unit\": \"mm\", \"interpret\": \"右肾小囊肿可能，一般为良性病变，但需定期监测其大小变化。\", \"advice\": \"建议定期复查肾脏B超，监测囊肿大小变化，如有增大或症状出现，及时就医。\" }, { \"conclusion\": \"泌尿系统提示风险\", \"examinationItem\": \"前列腺\", \"result\": \"形态略增大，回声欠均匀\", \"referenceValue\": \"前列腺形态正常，回声均匀\", \"unit\": \"-\", \"interpret\": \"前列腺形态略增大，回声欠均匀，提示可能存在前列腺增生或炎症，需进一步检查以明确。\", \"advice\": \"建议进行前列腺特异性抗原（PSA）检查，以排除前列腺疾病，必要时进行前列腺超声或MRI检查。\" }, { \"conclusion\": \"口腔系统提示风险\", \"examinationItem\": \"口腔粘膜，唇，牙齿，牙周\", \"result\": \"轻微炎症，唇部干燥脱屑，牙齿部分缺失伴有较多牙石，牙周炎及牙龈退缩\", \"referenceValue\": \"口腔粘膜无炎症，唇部湿润，牙齿完整无牙石，牙周健康\", \"unit\": \"-\", \"interpret\": \"口腔粘膜轻微炎症，唇部干燥脱屑，牙齿部分缺失伴有较多牙石，牙周炎及牙龈退缩，提示口腔卫生状况不佳，可能存在牙周疾病。\", \"advice\": \"建议口腔科进一步检查和治疗牙周炎，定期洁牙，保持口腔卫生，避免吸烟和过量饮酒。\" }, { \"conclusion\": \"耳鼻喉系统提示风险\", \"examinationItem\": \"听力\", \"result\": \"左耳听力轻度下降\", \"referenceValue\": \"听力正常\", \"unit\": \"-\", \"interpret\": \"左耳听力轻度下降，可能与年龄、噪音暴露或耳部疾病有关。\", \"advice\": \"建议耳鼻喉科进一步评估及定期复查听力，避免长时间暴露于噪音环境中。\" }, { \"conclusion\": \"耳鼻喉系统提示风险\", \"examinationItem\": \"鼻中隔\", \"result\": \"轻度弯曲\", \"referenceValue\": \"鼻中隔居中\", \"unit\": \"-\", \"interpret\": \"鼻中隔轻度弯曲，可能导致鼻塞、流涕或头痛等症状，但一般无需特殊治疗。\", \"advice\": \"如无症状，可观察；如有症状，建议耳鼻喉科就诊，必要时进行手术治疗。\" }, { \"conclusion\": \"耳鼻喉系统提示风险\", \"examinationItem\": \"口咽部\", \"result\": \"轻微充血\", \"referenceValue\": \"口咽部无充血\", \"unit\": \"-\", \"interpret\": \"口咽部轻微充血，可能与感染、过敏或刺激有关。\", \"advice\": \"建议保持口腔卫生，避免刺激性食物和饮料，必要时耳鼻喉科就诊。\" } ], \"systemScore\": { \"breathingSystem\": 90, \"digestiveSystem\": 70, \"endocrineSystem\": 85, \"immuneSystem\": 90, \"circulatorySystem\": 60, \"urinarySystem\": 75, \"motionSystem\": 95, \"senseSystem\": 70 }, \"summarize\": \"体检报告中未发现尿蛋白、癌胚抗原、血沉、空腹血糖、总胆固醇、甘油三酯、低密度脂蛋白胆固醇、血清载脂蛋白B、动脉硬化指数、白细胞、平均红细胞体积、平均血红蛋白共12项指标提示异常，尿液常规、血脂、血液常规、尿液常规、糖类抗原、血清酶类等共43项指标提示正常，但心率略快、偶发早搏、皮肤弹性差、四肢末梢凉提示循环系统存在“高危”风险；右肾小囊肿可能、前列腺形态略增大提示泌尿系统存在“中危”风险；肝脏实质回声略粗糙、胆囊壁毛糙提示消化系统存在“低危”风险。综合这些临床指标和数据分析，建议刘爱国先生针对上述异常进行进一步检查和治疗，并保持健康的生活方式，定期体检。\" }";

    log.warn("AI Model Res: " + res);
    redisTemplate.opsForValue().set(RedisKey.HEALTH_REPORT + dto.getIdCard(), res, 2, TimeUnit.HOURS);

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
    if (StringUtils.isEmpty(idCardNo)) {
      throw new BaseException("身份证号不能为空");
    }
    String content = PDFUtil.pdfToString(file.getInputStream());
    if (StringUtils.hasText(content)) {
      // String url = aliOssService.upload(file);
      String url = "https://luruoyang.oss-cn-hangzhou.aliyuncs.com/1/1/15/15/3/0/13/6/体检报告-张芳-女-72岁.pdf";
      // 缓存体检报告
      redisTemplate.opsForValue().set(RedisKey.HEALTH_REPORT + idCardNo, content, 2, TimeUnit.HOURS);
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
