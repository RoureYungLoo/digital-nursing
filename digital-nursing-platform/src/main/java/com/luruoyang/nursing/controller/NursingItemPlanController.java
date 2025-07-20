package com.luruoyang.nursing.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

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
import com.luruoyang.nursing.domain.NursingItemPlan;
import com.luruoyang.nursing.service.INursingItemPlanService;
import com.luruoyang.common.utils.poi.ExcelUtil;
import com.luruoyang.common.core.page.TableDataInfo;

/**
 * 护理计划和项目关联Controller
 *
 * @author luruoyang
 * @date 2025-07-19
 */
@RestController
@RequestMapping("/nursing/itemplan")
public class NursingItemPlanController extends BaseController {
  @Autowired
  private INursingItemPlanService nursingItemPlanService;

  /**
   * 查询护理计划和项目关联列表
   */
  @PreAuthorize("@ss.hasPermi('nursing:itemplan:list')")
  @GetMapping("/list")
  public TableDataInfo list(NursingItemPlan nursingItemPlan) {
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
  public AjaxResult getInfo(@PathVariable("id") Long id) {
    return success(nursingItemPlanService.selectNursingItemPlanById(id));
  }

  /**
   * 新增护理计划和项目关联
   */
  @PreAuthorize("@ss.hasPermi('nursing:itemplan:add')")
  @Log(title = "护理计划和项目关联", businessType = BusinessType.INSERT)
  @PostMapping
  public AjaxResult add(@RequestBody NursingItemPlan nursingItemPlan) {
    return toAjax(nursingItemPlanService.insertNursingItemPlan(nursingItemPlan));
  }

  /**
   * 修改护理计划和项目关联
   */
  @PreAuthorize("@ss.hasPermi('nursing:itemplan:edit')")
  @Log(title = "护理计划和项目关联", businessType = BusinessType.UPDATE)
  @PutMapping
  public AjaxResult edit(@RequestBody NursingItemPlan nursingItemPlan) {
    return toAjax(nursingItemPlanService.updateNursingItemPlan(nursingItemPlan));
  }

  /**
   * 删除护理计划和项目关联
   */
  @PreAuthorize("@ss.hasPermi('nursing:itemplan:remove')")
  @Log(title = "护理计划和项目关联", businessType = BusinessType.DELETE)
  @DeleteMapping("/{ids}")
  public AjaxResult remove(@PathVariable Long[] ids) {
    return toAjax(nursingItemPlanService.deleteNursingItemPlanByIds(ids));
  }
}
