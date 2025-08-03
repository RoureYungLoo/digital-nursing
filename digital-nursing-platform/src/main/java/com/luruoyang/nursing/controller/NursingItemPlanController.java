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
import com.luruoyang.nursing.entity.domain.NursingItemPlan;
import com.luruoyang.nursing.service.INursingItemPlanService;
import com.luruoyang.common.utils.poi.ExcelUtil;
import com.luruoyang.common.core.page.TableDataInfo;

/**
 * 护理计划和项目关联Controller
 *
 * @author luruoyang
 * @date 2025-07-22
 */
@RestController
@RequestMapping("/nursing/itemplan")
@Api(tags = "护理计划和项目关联相关接口")
public class NursingItemPlanController extends BaseController {
  @Autowired
  private INursingItemPlanService nursingItemPlanService;

  /**
   * 查询护理计划和项目关联列表
   */
  @PreAuthorize("@ss.hasPermi('nursing:itemplan:list')")
  @GetMapping("/list")
  @ApiOperation("查询护理计划和项目关联列表")
  public TableDataInfo<NursingItemPlan> list(NursingItemPlan nursingItemPlan) {
    startPage();
    List<NursingItemPlan> list = nursingItemPlanService.selectNursingItemPlanList(nursingItemPlan);
    return getDataTable(list);
  }

  /**
   * 导出护理计划和项目关联列表
   */
  @PreAuthorize("@ss.hasPermi('nursing:itemplan:export')")
  @Log(title = "护理计划和项目关联", businessType = BusinessType.EXPORT)
  @PostMapping("/export")
  @ApiOperation("导出护理计划和项目关联列表")
  public void export(HttpServletResponse response, NursingItemPlan nursingItemPlan) {
    List<NursingItemPlan> list = nursingItemPlanService.selectNursingItemPlanList(nursingItemPlan);
    ExcelUtil<NursingItemPlan> util = new ExcelUtil<NursingItemPlan>(NursingItemPlan.class);
    util.exportExcel(response, list, "护理计划和项目关联数据");
  }

  /**
   * 获取护理计划和项目关联详细信息
   */
  @PreAuthorize("@ss.hasPermi('nursing:itemplan:query')")
  @GetMapping(value = "/{id}")
  @ApiOperation("获取护理计划和项目关联详细信息")
  public R<NursingItemPlan> getInfo(@ApiParam(value = "护理计划和项目关联ID", required = true) @PathVariable("id") Integer id) {
    return R.ok(nursingItemPlanService.selectNursingItemPlanById(id));
  }

  /**
   * 新增护理计划和项目关联
   */
  @PreAuthorize("@ss.hasPermi('nursing:itemplan:add')")
  @Log(title = "护理计划和项目关联", businessType = BusinessType.INSERT)
  @PostMapping
  @ApiOperation("新增护理计划和项目关联")
  public AjaxResult add(@ApiParam(value = "护理计划和项目关联实体", required = true) @RequestBody NursingItemPlan nursingItemPlan) {
    return toAjax(nursingItemPlanService.insertNursingItemPlan(nursingItemPlan));
  }

  /**
   * 修改护理计划和项目关联
   */
  @PreAuthorize("@ss.hasPermi('nursing:itemplan:edit')")
  @Log(title = "护理计划和项目关联", businessType = BusinessType.UPDATE)
  @PutMapping
  @ApiOperation("修改护理计划和项目关联")
  public AjaxResult edit(@ApiParam(required = true, value = "护理计划和项目关联实体") @RequestBody NursingItemPlan nursingItemPlan) {
    return toAjax(nursingItemPlanService.updateNursingItemPlan(nursingItemPlan));
  }

  /**
   * 删除护理计划和项目关联
   */
  @PreAuthorize("@ss.hasPermi('nursing:itemplan:remove')")
  @Log(title = "护理计划和项目关联", businessType = BusinessType.DELETE)
  @DeleteMapping("/{ids}")
  @ApiOperation("删除护理计划和项目关联")
  public AjaxResult remove(@PathVariable Integer[] ids) {
    return toAjax(nursingItemPlanService.deleteNursingItemPlanByIds(ids));
  }
}
