package com.luruoyang.nursing.service.impl;

import java.time.LocalDateTime;
import java.util.*;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.luruoyang.common.constant.HttpStatus;
import com.luruoyang.common.core.page.TableDataInfo;
import com.luruoyang.common.utils.DateTimeZoneConverter;
import com.luruoyang.common.utils.StringUtils;
import com.luruoyang.common.utils.UserThreadLocal;
import com.luruoyang.nursing.constants.RedisKey;
import com.luruoyang.nursing.entity.domain.Device;
import com.luruoyang.nursing.entity.dto.DeviceDataPageReqDto;
import com.luruoyang.nursing.entity.resp.Body;
import com.luruoyang.nursing.entity.resp.DevicePropertyEvent;
import com.luruoyang.nursing.entity.resp.Header;
import com.luruoyang.nursing.entity.resp.NotifyData;
import com.luruoyang.nursing.service.IDeviceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luruoyang.nursing.mapper.DeviceDataMapper;
import com.luruoyang.nursing.entity.domain.DeviceData;
import com.luruoyang.nursing.service.IDeviceDataService;

/**
 * 设备数据Service业务层处理
 *
 * @author luruoyang
 * @date 2025-08-03
 */
@Service
@Slf4j
public class DeviceDataServiceImpl extends ServiceImpl<DeviceDataMapper, DeviceData> implements IDeviceDataService {
  @Autowired
  private DeviceDataMapper deviceDataMapper;

  @Autowired
  private IDeviceService deviceService;

  @Autowired
  private RedisTemplate<Object, Object> redisTemplate;

  /**
   * 查询设备数据
   *
   * @param id 设备数据主键
   * @return 设备数据
   */
  @Override
  public DeviceData selectDeviceDataById(Long id) {
    return this.getById(id);
  }

  /**
   * 查询设备数据列表
   *
   * @param deviceData 设备数据
   * @return 设备数据
   */
  @Override
  public List<DeviceData> selectDeviceDataList(DeviceData deviceData) {
    return deviceDataMapper.selectDeviceDataList(deviceData);
  }

  /**
   * 新增设备数据
   *
   * @param deviceData 设备数据
   * @return 结果
   */
  @Override
  public int insertDeviceData(DeviceData deviceData) {
    return this.save(deviceData) ? 1 : 0;
  }

  /**
   * 修改设备数据
   *
   * @param deviceData 设备数据
   * @return 结果
   */
  @Override
  public int updateDeviceData(DeviceData deviceData) {
    return this.updateById(deviceData) ? 1 : 0;
  }

  /**
   * 批量删除设备数据
   *
   * @param ids 需要删除的设备数据主键
   * @return 结果
   */
  @Override
  public int deleteDeviceDataByIds(Long[] ids) {
    return this.removeByIds(Arrays.asList(ids)) ? 1 : 0;

  }

  /**
   * 删除设备数据信息
   *
   * @param id 设备数据主键
   * @return 结果
   */
  @Override
  public int deleteDeviceDataById(Long id) {
    return this.removeById(id) ? 1 : 0;
  }

  /**
   * 批量插入设备数据
   *
   * @param contentStr 平台返回的字符串
   */
  @Override
  public void batchInsertDeviceData(String contentStr) {
    DevicePropertyEvent resp = JSONUtil.toBean(contentStr, DevicePropertyEvent.class);

    NotifyData notifyData = resp.getNotifyData();
    if (ObjectUtil.isEmpty(notifyData)) {
      log.error("huawei iot resp no notifyData");
      return;
    }

    Header header = notifyData.getHeader();
    if (ObjectUtil.isEmpty(header)) {
      log.error("huawei iot resp no header");
      return;
    }

    String deviceId = header.getDeviceId();
    Device deviceDb = deviceService.getOne(Wrappers.<Device>lambdaQuery().eq(Device::getIotId, deviceId));
    if (ObjectUtil.isNull(deviceDb)) {
      log.error("no deviceDb");
      return;
    }

    Body body = notifyData.getBody();
    if (ObjectUtil.isNull(body)) {
      log.error("huawei iot resp no header");
      return;
    }

    List<com.luruoyang.nursing.entity.resp.Service> services = body.getServices();
    if (CollectionUtil.isEmpty(services)) {
      log.error("huawei iot resp no header");
      return;
    }

    Long userId = UserThreadLocal.getUserId();
    for (com.luruoyang.nursing.entity.resp.Service service : services) {
      String et = service.getEventTime();
      LocalDateTime time = LocalDateTimeUtil.parse(et, "yyyyMMdd'T'HHmmss'Z'");
      LocalDateTime eventTime = DateTimeZoneConverter.utcToShanghai(time);

      Map<String, Object> properties = service.getProperties();
      if (properties.isEmpty()) {
        log.error("huawei iot resp properties ");
        return;
      }

      List<DeviceData> deviceDataList = new ArrayList<>();
      properties.forEach((functionId, dataValue) -> {
        DeviceData deviceData = BeanUtil.toBean(deviceDb, DeviceData.class);
        deviceData.setId(null);
        deviceData.setAlarmTime(eventTime);
        deviceData.setFunctionId(functionId);
        deviceData.setDataValue(dataValue + "");
        deviceDataList.add(deviceData);
        log.info("==============> deviceDataList: {}", deviceDataList);
      });

      this.saveBatch(deviceDataList);

      // redisTemplate.opsForValue().set(RedisKey.IOT_DEVICE_NEWEST_DATA, JSONUtil.toJsonStr(deviceDataList));
      redisTemplate.opsForHash().put(RedisKey.IOT_DEVICE_NEWEST_DATA, deviceDb.getIotId(), JSONUtil.toJsonStr(deviceDataList));
    }
  }

  /**
   * 分页条件查询设备数据
   *
   * @param dto dto
   * @return 结果
   */
  @Override
  public TableDataInfo<DeviceData> selectDeviceDataPage(DeviceDataPageReqDto dto) {
    Page<DeviceData> page = new Page<>(dto.getPageNum(), dto.getPageSize());

    LambdaQueryWrapper<DeviceData> wrapper = Wrappers.<DeviceData>lambdaQuery();
    String deviceName = dto.getDeviceName();
    String functionId = dto.getFunctionId();
    LocalDateTime startTime = dto.getStartTime();
    LocalDateTime endTime = dto.getEndTime();
    wrapper.like(StringUtils.isNotEmpty(deviceName), DeviceData::getDeviceName, deviceName)
        .eq(StringUtils.isNotEmpty(functionId), DeviceData::getFunctionId, functionId)
        .between(Objects.nonNull(startTime) && Objects.nonNull(endTime), DeviceData::getAlarmTime, startTime, endTime);

    page = this.page(page, wrapper);

    TableDataInfo<DeviceData> rspData = new TableDataInfo<>();
    rspData.setCode(HttpStatus.SUCCESS);
    rspData.setMsg("查询成功");
    rspData.setRows(page.getRecords());
    rspData.setTotal(page.getTotal());
    return rspData;
  }
}
