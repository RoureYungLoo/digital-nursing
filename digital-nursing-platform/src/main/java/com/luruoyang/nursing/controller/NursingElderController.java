package com.luruoyang.nursing.controller;

import com.luruoyang.nursing.entity.dto.NursingElderDto;
import com.luruoyang.nursing.service.INursingElderService;
import com.luruoyang.common.annotation.Log;
import com.luruoyang.common.core.controller.BaseController;
import com.luruoyang.common.core.domain.AjaxResult;
import com.luruoyang.common.core.page.TableDataInfo;
import com.luruoyang.common.enums.BusinessType;
import com.luruoyang.common.utils.poi.ExcelUtil;
import com.luruoyang.nursing.entity.domain.NursingElder;
import com.luruoyang.nursing.entity.dto.NursingElderDto;
import com.luruoyang.nursing.service.INursingElderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 护理员老人关联Controller
 *
 * @author ruoyi
 * @date 2024-05-28
 */
@RestController
@RequestMapping("/elder/nursingElder")
@Api(tags = "护理员老人关联相关接口")
public class NursingElderController extends BaseController {
  @Autowired
  private INursingElderService nursingElderService;

  @PostMapping("/setNursing")
  @ApiOperation("设置护理员")
  public AjaxResult setNursingElder(@RequestBody List<NursingElderDto> nursingElderDtos) {
    return AjaxResult.success(nursingElderService.setNursingElder(nursingElderDtos));
  }

  /**
   * 查询护理员老人关联列表
   */
  @PreAuthorize("@ss.hasPermi('elder:elder:list')")
  @GetMapping("/list")
  @ApiOperation("查询护理员老人关联列表")
  public TableDataInfo list(NursingElder nursingElder) {
    startPage();
    List<NursingElder> list = nursingElderService.selectNursingElderList(nursingElder);
    return getDataTable(list);
  }

  /**
   * 导出护理员老人关联列表
   */
  @PreAuthorize("@ss.hasPermi('elder:elder:export')")
  @Log(title = "护理员老人关联", businessType = BusinessType.EXPORT)
  @PostMapping("/export")
  public void export(HttpServletResponse response, NursingElder nursingElder) {
    List<NursingElder> list = nursingElderService.selectNursingElderList(nursingElder);
    ExcelUtil<NursingElder> util = new ExcelUtil<NursingElder>(NursingElder.class);
    util.exportExcel(response, list, "护理员老人关联数据");
  }

  /**
   * 获取护理员老人关联详细信息
   */
  @PreAuthorize("@ss.hasPermi('elder:elder:query')")
  @GetMapping(value = "/{id}")
  @ApiOperation("获取护理员老人关联详细信息")
  public AjaxResult getInfo(@PathVariable("id") Long id) {
    return success(nursingElderService.selectNursingElderById(id));
  }

  /**
   * 新增护理员老人关联
   */
  @PreAuthorize("@ss.hasPermi('elder:elder:add')")
  @Log(title = "护理员老人关联", businessType = BusinessType.INSERT)
  @PostMapping
  @ApiOperation("新增护理员老人关联")
  public AjaxResult add(@RequestBody NursingElder nursingElder) {
    return toAjax(nursingElderService.insertNursingElder(nursingElder));
  }

  /**
   * 修改护理员老人关联
   */
  @ApiOperation("修改护理员老人关联")
  @PreAuthorize("@ss.hasPermi('elder:elder:edit')")
  @Log(title = "护理员老人关联", businessType = BusinessType.UPDATE)
  @PutMapping
  public AjaxResult edit(@RequestBody NursingElder nursingElder) {
    return toAjax(nursingElderService.updateNursingElder(nursingElder));
  }

  /**
   * 删除护理员老人关联
   */
  @ApiOperation("删除护理员老人关联")
  @PreAuthorize("@ss.hasPermi('elder:elder:remove')")
  @Log(title = "护理员老人关联", businessType = BusinessType.DELETE)
  @DeleteMapping("/{ids}")
  public AjaxResult remove(@PathVariable Long[] ids) {
    return toAjax(nursingElderService.deleteNursingElderByIds(ids));
  }
}
