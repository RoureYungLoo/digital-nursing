package com.luruoyang.nursing.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.luruoyang.common.core.domain.R;
import com.luruoyang.nursing.entity.dto.HealthAssessmentDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.luruoyang.common.annotation.Log;
import com.luruoyang.common.core.controller.BaseController;
import com.luruoyang.common.core.domain.AjaxResult;
import com.luruoyang.common.enums.BusinessType;
import com.luruoyang.nursing.entity.domain.HealthAssessment;
import com.luruoyang.nursing.service.IHealthAssessmentService;
import com.luruoyang.common.utils.poi.ExcelUtil;
import com.luruoyang.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 健康评估Controller
 *
 * @author luruoyang
 * @date 2025-08-04
 */
@RestController
@RequestMapping("/nursing/healthAssessment")
@Api(tags = "健康评估相关接口")
public class HealthAssessmentController extends BaseController {
  @Autowired
  private IHealthAssessmentService healthAssessmentService;

  /**
   * 查询健康评估列表
   */
  @PreAuthorize("@ss.hasPermi('nursing:healthAssessment:list')")
  @GetMapping("/list")
  @ApiOperation("查询健康评估列表")
  public TableDataInfo<HealthAssessment> list(HealthAssessment healthAssessment) {
    startPage();
    List<HealthAssessment> list = healthAssessmentService.selectHealthAssessmentList(healthAssessment);
    return getDataTable(list);
  }

  /**
   * 导出健康评估列表
   */
  @PreAuthorize("@ss.hasPermi('nursing:healthAssessment:export')")
  @Log(title = "健康评估", businessType = BusinessType.EXPORT)
  @PostMapping("/export")
  @ApiOperation("导出健康评估列表")
  public void export(HttpServletResponse response, HealthAssessment healthAssessment) {
    List<HealthAssessment> list = healthAssessmentService.selectHealthAssessmentList(healthAssessment);
    ExcelUtil<HealthAssessment> util = new ExcelUtil<HealthAssessment>(HealthAssessment.class);
    util.exportExcel(response, list, "健康评估数据");
  }

  /**
   * 获取健康评估详细信息
   */
  @PreAuthorize("@ss.hasPermi('nursing:healthAssessment:query')")
  @GetMapping(value = "/{id}")
  @ApiOperation("获取健康评估详细信息")
  public R<HealthAssessment> getInfo(@ApiParam(value = "健康评估ID", required = true) @PathVariable("id") Long id) {
    return R.ok(healthAssessmentService.selectHealthAssessmentById(id));
  }

  /**
   * 新增健康评估
   */
  @PreAuthorize("@ss.hasPermi('nursing:healthAssessment:add')")
  @Log(title = "健康评估", businessType = BusinessType.INSERT)
  @PostMapping
  @ApiOperation("新增健康评估")
  public AjaxResult add(@ApiParam(value = "健康评估实体", required = true) @RequestBody HealthAssessmentDto dto) {
    Long assessmentId = healthAssessmentService.insertHealthAssessment(dto);
    return success(assessmentId);
  }

  /**
   * 修改健康评估
   */
  @PreAuthorize("@ss.hasPermi('nursing:healthAssessment:edit')")
  @Log(title = "健康评估", businessType = BusinessType.UPDATE)
  @PutMapping
  @ApiOperation("修改健康评估")
  public AjaxResult edit(@ApiParam(required = true, value = "健康评估实体") @RequestBody HealthAssessment healthAssessment) {
    return toAjax(healthAssessmentService.updateHealthAssessment(healthAssessment));
  }

  /**
   * 删除健康评估
   */
  @PreAuthorize("@ss.hasPermi('nursing:healthAssessment:remove')")
  @Log(title = "健康评估", businessType = BusinessType.DELETE)
  @DeleteMapping("/{ids}")
  @ApiOperation("删除健康评估")
  public AjaxResult remove(@PathVariable Long[] ids) {
    return toAjax(healthAssessmentService.deleteHealthAssessmentByIds(ids));
  }

  @ApiOperation("体检报告文档上传")
  @PostMapping("/upload")
  public AjaxResult uploadFile(MultipartFile file, String idCardNo) throws Exception {
    try {
      String url = healthAssessmentService.uploadFile(file, idCardNo);
      AjaxResult ajax = AjaxResult.success();
      ajax.put("url", url);
      ajax.put("fileName", url);
      ajax.put("originalFilename", file.getOriginalFilename());
      return ajax;
    } catch (Exception e) {
      return AjaxResult.error(e.getMessage());
    }
  }
}
