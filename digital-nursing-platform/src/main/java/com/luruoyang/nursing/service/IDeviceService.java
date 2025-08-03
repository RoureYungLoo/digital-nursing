package com.luruoyang.nursing.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;
import com.luruoyang.nursing.entity.domain.Device;
import com.luruoyang.nursing.entity.dto.DeviceDto;
import com.luruoyang.nursing.entity.vo.DeviceDetailVo;
import com.luruoyang.nursing.entity.vo.ProductVo;

/**
 * 设备Service接口
 *
 * @author luruoyang
 * @date 2025-08-01
 */
public interface IDeviceService extends IService<Device> {
  /**
   * 查询设备
   *
   * @param id 设备主键
   * @return 设备
   */
  public Device selectDeviceById(Long id);

  /**
   * 查询设备列表
   *
   * @param device 设备
   * @return 设备集合
   */
  public List<Device> selectDeviceList(Device device);

  /**
   * 新增设备
   *
   * @param device 设备
   * @return 结果
   */
  public int insertDevice(Device device);

  /**
   * 修改设备
   *
   * @param device 设备
   * @return 结果
   */
  public int updateDevice(Device device);

  /**
   * 批量删除设备
   *
   * @param ids 需要删除的设备主键集合
   * @return 结果
   */
  public int deleteDeviceByIds(Long[] ids);

  /**
   * 删除设备信息
   *
   * @param id 设备主键
   * @return 结果
   */
  public int deleteDeviceById(Long id);

  /**
   * 同步设备列表
   */
  void syncProductList();

  /**
   * 查询产品列表
   *
   * @return 返回值
   */
  List<ProductVo> allProduct();

  /**
   * 注册设备
   *
   * @param dto dto
   * @return 返回值
   */
  int register(DeviceDto dto);

  /**
   * 查询设备详情
   *
   * @param iotId iotId
   * @return 设备详情
   */
  DeviceDetailVo queryDeviceDetail(String iotId);


  /**
   * 查询设备影子数据
   *
   * @param iotId iotId
   * @return 影子数据 map
   */
  List<Map<String, Object>> queryServiceProperties(String iotId);

  /**
   * 修改设备
   * @param dto dto
   * @return 返回值
   */
  boolean updateIotDevice(DeviceDto dto);

  boolean deleteDevice(String iotId);
}
