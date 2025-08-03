package com.luruoyang.nursing.controller;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

import com.luruoyang.common.core.domain.R;
import com.luruoyang.nursing.entity.dto.DeviceDto;
import com.luruoyang.nursing.entity.vo.DeviceDetailVo;
import com.luruoyang.nursing.entity.vo.ProductVo;
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
import com.luruoyang.nursing.entity.domain.Device;
import com.luruoyang.nursing.service.IDeviceService;
import com.luruoyang.common.utils.poi.ExcelUtil;
import com.luruoyang.common.core.page.TableDataInfo;

/**
 * 设备Controller
 *
 * @author luruoyang
 * @date 2025-08-01
 */
@RestController
@RequestMapping("/nursing/device")
@Api(tags = "设备相关接口")
public class DeviceController extends BaseController {
  @Autowired
  private IDeviceService deviceService;

  /**
   * 查询设备列表
   */
  @PreAuthorize("@ss.hasPermi('nursing:device:list')")
  @GetMapping("/list")
  @ApiOperation("查询设备列表")
  public TableDataInfo<Device> list(Device device) {
    startPage();
    List<Device> list = deviceService.selectDeviceList(device);
    return getDataTable(list);
  }

  /**
   * 同步设备列表
   */
  @PostMapping("/syncProductList")
  @ApiOperation("同步设备列表")
  public AjaxResult syncProductList() {
    deviceService.syncProductList();
    return success();
  }

  /**
   * 查询产品列表
   */
  @GetMapping("/allProduct")
  @ApiOperation("查询产品列表")
  public AjaxResult allProduct() {
    List<ProductVo> vos = deviceService.allProduct();
    return success(vos);
  }

  /**
   * 注册设备
   */
  @PostMapping("/register")
  @ApiOperation("注册设备")
  public AjaxResult register(@RequestBody DeviceDto dto) {
    return success(deviceService.register(dto));
  }

  /**
   * 查询设备详情
   */
  @GetMapping("/{iotId}")
  @ApiOperation("查询设备详情")
  public AjaxResult queryDeviceDetail(@PathVariable("iotId") String iotId) {
    DeviceDetailVo vo = deviceService.queryDeviceDetail(iotId);
    return success(vo);
  }

  /**
   * 查询设备影子数据
   */
  @GetMapping("/queryServiceProperties/{iotId}")
  @ApiOperation("查询设备详情")
  public AjaxResult queryServiceProperties(@PathVariable("iotId") String iotId) {
    List<Map<String, Object>> list = deviceService.queryServiceProperties(iotId);
    return success(list);
  }

  /**
   * 修改设备
   */
  @PutMapping
  @ApiOperation("修改设备")
  public R updateIotDevice(@RequestBody DeviceDto dto) {
    if (deviceService.updateIotDevice(dto)) {
      return R.ok();
    } else {
      return R.fail();
    }
  }

  /**
   * 删除设备
   */
  @DeleteMapping("/{iotId}")
  @ApiOperation("删除设备")
  public R deleteDevice(@PathVariable String iotId) {
    if (deviceService.deleteDevice(iotId)) {
      return R.ok();
    } else {
      return R.fail();
    }
  }
}
