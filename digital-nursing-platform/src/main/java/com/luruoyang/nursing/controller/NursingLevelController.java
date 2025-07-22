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
import com.luruoyang.nursing.domain.NursingLevel;
import com.luruoyang.nursing.service.INursingLevelService;
import com.luruoyang.common.utils.poi.ExcelUtil;
import com.luruoyang.common.core.page.TableDataInfo;

/**
 * 护理等级Controller
 *
 * @author luruoyang
 * @date 2025-07-22
 */
@RestController
@RequestMapping("/nursing/level")
@Api(tags = "护理等级相关接口")
public class NursingLevelController extends BaseController {
  @Autowired
  private INursingLevelService nursingLevelService;

/**
 * 查询护理等级列表
 */
@PreAuthorize("@ss.hasPermi('nursing:level:list')")
@GetMapping("/list")
@ApiOperation("查询护理等级列表")
  public TableDataInfo<List<NursingLevel>> list(NursingLevel nursingLevel) {
    startPage();
    List<NursingLevel> list = nursingLevelService.selectNursingLevelList(nursingLevel);
    return getDataTable(list);
  }

  /**
   * 导出护理等级列表
   */
  @PreAuthorize("@ss.hasPermi('nursing:level:export')")
  @Log(title = "护理等级", businessType = BusinessType.EXPORT)
  @PostMapping("/export")
  @ApiOperation("导出护理等级列表")
  public void export(HttpServletResponse response, NursingLevel nursingLevel) {
    List<NursingLevel> list = nursingLevelService.selectNursingLevelList(nursingLevel);
    ExcelUtil<NursingLevel> util = new ExcelUtil<NursingLevel>(NursingLevel. class);
    util.exportExcel(response, list, "护理等级数据");
  }

  /**
   * 获取护理等级详细信息
   */
  @PreAuthorize("@ss.hasPermi('nursing:level:query')")
  @GetMapping(value = "/{id}")
  @ApiOperation("获取护理等级详细信息")
      public R<NursingLevel> getInfo(@ApiParam(value = "护理等级ID", required = true) @PathVariable("id") Integer id) {
        return R.ok(nursingLevelService.selectNursingLevelById(id));
  }

  /**
   * 新增护理等级
   */
  @PreAuthorize("@ss.hasPermi('nursing:level:add')")
  @Log(title = "护理等级", businessType = BusinessType.INSERT)
  @PostMapping
  @ApiOperation("新增护理等级")
  public AjaxResult add(@ApiParam(value = "护理等级实体", required = true) @RequestBody NursingLevel nursingLevel) {
    return toAjax(nursingLevelService.insertNursingLevel(nursingLevel));
  }

  /**
   * 修改护理等级
   */
  @PreAuthorize("@ss.hasPermi('nursing:level:edit')")
  @Log(title = "护理等级", businessType = BusinessType.UPDATE)
  @PutMapping
  @ApiOperation("修改护理等级")
  public AjaxResult edit(@ApiParam(required = true, value = "护理等级实体") @RequestBody NursingLevel nursingLevel) {
    return toAjax(nursingLevelService.updateNursingLevel(nursingLevel));
  }

  /**
   * 删除护理等级
   */
  @PreAuthorize("@ss.hasPermi('nursing:level:remove')")
  @Log(title = "护理等级", businessType = BusinessType.DELETE)
  @DeleteMapping("/{ids}")
  @ApiOperation("删除护理等级")
  public AjaxResult remove(@PathVariable Integer[] ids) {
    return toAjax(nursingLevelService.deleteNursingLevelByIds(ids));
  }
}
