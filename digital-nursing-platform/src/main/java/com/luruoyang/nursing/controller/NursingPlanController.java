package com.luruoyang.nursing.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.luruoyang.common.core.domain.R;
import com.luruoyang.nursing.entity.dto.NursingPlanDto;
import com.luruoyang.nursing.entity.vo.NursingItemVo;
import com.luruoyang.nursing.entity.vo.NursingPlanVo;
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
import com.luruoyang.nursing.entity.domain.NursingPlan;
import com.luruoyang.nursing.service.INursingPlanService;
import com.luruoyang.common.utils.poi.ExcelUtil;
import com.luruoyang.common.core.page.TableDataInfo;

/**
 * 护理计划Controller
 *
 * @author luruoyang
 * @date 2025-07-20
 */
@RestController
@RequestMapping("/serve/plan")
public class NursingPlanController extends BaseController {
  @Autowired
  private INursingPlanService nursingPlanService;

  /**
   * 查询护理计划列表
   */
  @PreAuthorize("@ss.hasPermi('serve:plan:list')")
  @GetMapping("/all")
  public R<List<NursingPlanVo>> list() {
    List<NursingPlanVo> list = nursingPlanService.findAll();
    return R.ok(list);
  }

  /**
   * 查询护理计划列表
   */
  @PreAuthorize("@ss.hasPermi('serve:plan:list')")
  @GetMapping("/list")
  public TableDataInfo list(NursingPlan nursingPlan) {
    startPage();
    List<NursingPlan> list = nursingPlanService.selectNursingPlanList(nursingPlan);
    return getDataTable(list);
  }

  /**
   * 导出护理计划列表
   */
  @PreAuthorize("@ss.hasPermi('serve:plan:export')")
  @Log(title = "护理计划", businessType = BusinessType.EXPORT)
  @PostMapping("/export")
  public void export(HttpServletResponse response, NursingPlan nursingPlan) {
    List<NursingPlan> list = nursingPlanService.selectNursingPlanList(nursingPlan);
    ExcelUtil<NursingPlan> util = new ExcelUtil<NursingPlan>(NursingPlan.class);
    util.exportExcel(response, list, "护理计划数据");
  }

  /**
   * 获取护理计划详细信息
   */
  @PreAuthorize("@ss.hasPermi('serve:plan:query')")
  @GetMapping(value = "/{id:\\d+}")
  public AjaxResult getInfo(@PathVariable("id") Integer id) {
    return success(nursingPlanService.selectNursingPlanById(id));
  }

  /**
   * 新增护理计划
   */
  @PreAuthorize("@ss.hasPermi('serve:plan:add')")
  @Log(title = "护理计划", businessType = BusinessType.INSERT)
  @PostMapping
  public AjaxResult add(@RequestBody NursingPlanDto nursingPlanDto) {
    return toAjax(nursingPlanService.insertNursingPlan(nursingPlanDto));
  }

  /**
   * 修改护理计划
   */
  @PreAuthorize("@ss.hasPermi('serve:plan:edit')")
  @Log(title = "护理计划", businessType = BusinessType.UPDATE)
  @PutMapping
  public AjaxResult edit(@RequestBody NursingPlanVo vo) {
    return toAjax(nursingPlanService.updateNursingPlan(vo));
  }

  /**
   * 删除护理计划
   */
  @PreAuthorize("@ss.hasPermi('serve:plan:remove')")
  @Log(title = "护理计划", businessType = BusinessType.DELETE)
  @DeleteMapping("/{ids}")
  public AjaxResult remove(@PathVariable Integer[] ids) {
    return toAjax(nursingPlanService.deleteNursingPlanByIds(ids));
  }
}
