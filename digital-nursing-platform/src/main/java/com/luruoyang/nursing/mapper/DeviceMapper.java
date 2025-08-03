package com.luruoyang.nursing.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.luruoyang.nursing.entity.domain.Device;


/**
 * 设备Mapper接口
 *
 * @author luruoyang
 * @date 2025-08-01
 */
@Mapper
public interface DeviceMapper extends BaseMapper<Device> {
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
   * 删除设备
   *
   * @param id 设备主键
   * @return 结果
   */
  public int deleteDeviceById(Long id);

  /**
   * 批量删除设备
   *
   * @param ids 需要删除的数据主键集合
   * @return 结果
   */
  public int deleteDeviceByIds(Long[] ids);
}
