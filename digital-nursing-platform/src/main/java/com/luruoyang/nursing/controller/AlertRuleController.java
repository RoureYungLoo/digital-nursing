package com.luruoyang.nursing.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.luruoyang.common.core.domain.R;
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
import com.luruoyang.nursing.entity.domain.AlertRule;
import com.luruoyang.nursing.service.IAlertRuleService;
import com.luruoyang.common.utils.poi.ExcelUtil;
import com.luruoyang.common.core.page.TableDataInfo;

/**
 * 报警规则Controller
 *
 * @author luruoyang
 * @date 2025-08-04
 */
@RestController
@RequestMapping("/nursing/alertRule")
@Api(tags = "报警规则相关接口")
public class AlertRuleController extends BaseController {
  @Autowired
  private IAlertRuleService alertRuleService;

/**
 * 查询报警规则列表
 */
@PreAuthorize("@ss.hasPermi('nursing:alertRule:list')")
@GetMapping("/list")
@ApiOperation("查询报警规则列表")
public TableDataInfo<AlertRule> list(AlertRule alertRule) {
  startPage();
  List<AlertRule> list = alertRuleService.selectAlertRuleList(alertRule);
  return getDataTable(list);
}

  /**
   * 导出报警规则列表
   */
  @PreAuthorize("@ss.hasPermi('nursing:alertRule:export')")
  @Log(title = "报警规则", businessType = BusinessType.EXPORT)
  @PostMapping("/export")
  @ApiOperation("导出报警规则列表")
  public void export(HttpServletResponse response, AlertRule alertRule) {
    List<AlertRule> list = alertRuleService.selectAlertRuleList(alertRule);
    ExcelUtil<AlertRule> util = new ExcelUtil<AlertRule>(AlertRule. class);
    util.exportExcel(response, list, "报警规则数据");
  }

  /**
   * 获取报警规则详细信息
   */
  @PreAuthorize("@ss.hasPermi('nursing:alertRule:query')")
  @GetMapping(value = "/{id}")
  @ApiOperation("获取报警规则详细信息")
      public R<AlertRule> getInfo(@ApiParam(value = "报警规则ID", required = true) @PathVariable("id") Long id) {
        return R.ok(alertRuleService.selectAlertRuleById(id));
  }

  /**
   * 新增报警规则
   */
  @PreAuthorize("@ss.hasPermi('nursing:alertRule:add')")
  @Log(title = "报警规则", businessType = BusinessType.INSERT)
  @PostMapping
  @ApiOperation("新增报警规则")
  public AjaxResult add(@ApiParam(value = "报警规则实体", required = true) @RequestBody AlertRule alertRule) {
    return toAjax(alertRuleService.insertAlertRule(alertRule));
  }

  /**
   * 修改报警规则
   */
  @PreAuthorize("@ss.hasPermi('nursing:alertRule:edit')")
  @Log(title = "报警规则", businessType = BusinessType.UPDATE)
  @PutMapping
  @ApiOperation("修改报警规则")
  public AjaxResult edit(@ApiParam(required = true, value = "报警规则实体") @RequestBody AlertRule alertRule) {
    return toAjax(alertRuleService.updateAlertRule(alertRule));
  }

  /**
   * 删除报警规则
   */
  @PreAuthorize("@ss.hasPermi('nursing:alertRule:remove')")
  @Log(title = "报警规则", businessType = BusinessType.DELETE)
  @DeleteMapping("/{ids}")
  @ApiOperation("删除报警规则")
  public AjaxResult remove(@PathVariable Long[] ids) {
    return toAjax(alertRuleService.deleteAlertRuleByIds(ids));
  }
}
