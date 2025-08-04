package com.luruoyang.nursing.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.luruoyang.common.core.domain.R;
import com.luruoyang.nursing.entity.dto.DeviceDataPageReqDto;
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
import com.luruoyang.nursing.entity.domain.DeviceData;
import com.luruoyang.nursing.service.IDeviceDataService;
import com.luruoyang.common.utils.poi.ExcelUtil;
import com.luruoyang.common.core.page.TableDataInfo;

/**
 * 设备数据Controller
 *
 * @author luruoyang
 * @date 2025-08-03
 */
@RestController
@RequestMapping("/nursing/data")
@Api(tags = "设备数据相关接口")
public class DeviceDataController extends BaseController {
  @Autowired
  private IDeviceDataService deviceDataService;

  /**
   * 查询设备数据列表
   */
//  @PreAuthorize("@ss.hasPermi('nursing:data:list')")
//  @GetMapping("/list")
//  @ApiOperation("查询设备数据列表")
//  public TableDataInfo<DeviceData> list(DeviceData deviceData) {
//    startPage();
//    List<DeviceData> list = deviceDataService.selectDeviceDataList(deviceData);
//    return getDataTable(list);
//  }

  /**
   * 查询设备数据列表
   */
  @PreAuthorize("@ss.hasPermi('nursing:data:list')")
  @GetMapping("/list")
  @ApiOperation("查询设备数据列表")
  public TableDataInfo<DeviceData> page(DeviceDataPageReqDto dto) {
    return deviceDataService.selectDeviceDataPage(dto);
  }

  /**
   * 导出设备数据列表
   */
  @PreAuthorize("@ss.hasPermi('nursing:data:export')")
  @Log(title = "设备数据", businessType = BusinessType.EXPORT)
  @PostMapping("/export")
  @ApiOperation("导出设备数据列表")
  public void export(HttpServletResponse response, DeviceData deviceData) {
    List<DeviceData> list = deviceDataService.selectDeviceDataList(deviceData);
    ExcelUtil<DeviceData> util = new ExcelUtil<DeviceData>(DeviceData.class);
    util.exportExcel(response, list, "设备数据数据");
  }

  /**
   * 获取设备数据详细信息
   */
  @PreAuthorize("@ss.hasPermi('nursing:data:query')")
  @GetMapping(value = "/{id}")
  @ApiOperation("获取设备数据详细信息")
  public R<DeviceData> getInfo(@ApiParam(value = "设备数据ID", required = true) @PathVariable("id") Long id) {
    return R.ok(deviceDataService.selectDeviceDataById(id));
  }

  /**
   * 新增设备数据
   */
  @PreAuthorize("@ss.hasPermi('nursing:data:add')")
  @Log(title = "设备数据", businessType = BusinessType.INSERT)
  @PostMapping
  @ApiOperation("新增设备数据")
  public AjaxResult add(@ApiParam(value = "设备数据实体", required = true) @RequestBody DeviceData deviceData) {
    return toAjax(deviceDataService.insertDeviceData(deviceData));
  }

  /**
   * 修改设备数据
   */
  @PreAuthorize("@ss.hasPermi('nursing:data:edit')")
  @Log(title = "设备数据", businessType = BusinessType.UPDATE)
  @PutMapping
  @ApiOperation("修改设备数据")
  public AjaxResult edit(@ApiParam(required = true, value = "设备数据实体") @RequestBody DeviceData deviceData) {
    return toAjax(deviceDataService.updateDeviceData(deviceData));
  }

  /**
   * 删除设备数据
   */
  @PreAuthorize("@ss.hasPermi('nursing:data:remove')")
  @Log(title = "设备数据", businessType = BusinessType.DELETE)
  @DeleteMapping("/{ids}")
  @ApiOperation("删除设备数据")
  public AjaxResult remove(@PathVariable Long[] ids) {
    return toAjax(deviceDataService.deleteDeviceDataByIds(ids));
  }
}
