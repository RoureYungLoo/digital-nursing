package com.luruoyang.nursing.service.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.huaweicloud.sdk.iotda.v5.IoTDAClient;
import com.huaweicloud.sdk.iotda.v5.model.*;
import com.luruoyang.common.constant.HttpStatus;
import com.luruoyang.common.constant.StatusConstants;
import com.luruoyang.common.exception.base.BaseException;
import com.luruoyang.common.utils.DateTimeZoneConverter;
import com.luruoyang.common.utils.StringUtils;
import com.luruoyang.common.utils.uuid.IdUtils;
import com.luruoyang.nursing.constants.RedisKey;
import com.luruoyang.nursing.entity.dto.DeviceDto;
import com.luruoyang.nursing.entity.vo.DeviceDetailVo;
import com.luruoyang.nursing.entity.vo.ProductVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luruoyang.nursing.mapper.DeviceMapper;
import com.luruoyang.nursing.entity.domain.Device;
import com.luruoyang.nursing.service.IDeviceService;

/**
 * 设备Service业务层处理
 *
 * @author luruoyang
 * @date 2025-08-01
 */
@Service
@Slf4j
public class DeviceServiceImpl extends ServiceImpl<DeviceMapper, Device> implements IDeviceService {
  @Autowired
  private DeviceMapper deviceMapper;

  @Autowired
  private IoTDAClient client;

  @Autowired
  private RedisTemplate<Object, Object> redisTemplate;

  /**
   * 查询设备
   *
   * @param id 设备主键
   * @return 设备
   */
  @Override
  public Device selectDeviceById(Long id) {
    return this.getById(id);
  }

  /**
   * 查询设备列表
   *
   * @param device 设备
   * @return 设备
   */
  @Override
  public List<Device> selectDeviceList(Device device) {

    return deviceMapper.selectDeviceList(device);
  }

  /**
   * 新增设备
   *
   * @param device 设备
   * @return 结果
   */
  @Override
  public int insertDevice(Device device) {
    return this.save(device) ? 1 : 0;
  }

  /**
   * 修改设备
   *
   * @param device 设备
   * @return 结果
   */
  @Override
  public int updateDevice(Device device) {
    return this.updateById(device) ? 1 : 0;
  }

  /**
   * 批量删除设备
   *
   * @param ids 需要删除的设备主键
   * @return 结果
   */
  @Override
  public int deleteDeviceByIds(Long[] ids) {
    return this.removeByIds(Arrays.asList(ids)) ? 1 : 0;

  }

  /**
   * 删除设备信息
   *
   * @param id 设备主键
   * @return 结果
   */
  @Override
  public int deleteDeviceById(Long id) {
    return this.removeById(id) ? 1 : 0;
  }


  /**
   * 同步设备列表
   */
  @Override
  public void syncProductList() {
    ListProductsRequest req = new ListProductsRequest();
    req.setLimit(50);
    ListProductsResponse resp = client.listProducts(req);
    int code = resp.getHttpStatusCode();
    if (code != 200) {
      throw new BaseException("物联网接口 - 查询产品，同步失败");
    } else {
      List<ProductSummary> summaryList = resp.getProducts();
      redisTemplate.opsForValue().set(
          RedisKey.IOT_ALL_PRODUCT_LIST,
          JSONUtil.toJsonStr(summaryList)
      );
    }
  }

  /**
   * 查询产品列表
   *
   * @return List<ProductVo>
   */
  @Override
  public List<ProductVo> allProduct() {
//    List<String> productList = (List<String>) redisTemplate.opsForValue().get(RedisKey.IOT_ALL_PRODUCT_LIST);
    String productListStr = (String) redisTemplate.opsForValue().get(RedisKey.IOT_ALL_PRODUCT_LIST);
    if (StringUtils.isEmpty(productListStr)) {
      throw new BaseException("no product ");
    }

    List<ProductVo> vos = null;
//    vos = productList.stream().map(new Function<String, ProductVo>() {
//      @Override
//      public ProductVo apply(String s) {
//        JSONObject obj = JSONUtil.parseObj(s);
//        return ProductVo.builder()
//            .name(obj.getStr("name"))
//            .productId(obj.getStr("product_id")).build();
//      }
//    }).collect(Collectors.toList());

    vos = JSONUtil.toList(productListStr, ProductVo.class);
    return vos;
  }

  /**
   * @param dto dto
   * @return
   */
  @Override
  public int register(DeviceDto dto) {
    // 设备名称判重
    // this.getOne(Wrappers.lambdaQuery().eq(Device::getProductName))
    long count = this.count(Wrappers.<Device>lambdaQuery().eq(Device::getDeviceName, dto.getDeviceName()));
    if (count > 0) {
      throw new BaseException("设备名称 - 重复");
    }

    // 设备标识判重
    count = this.count(Wrappers.<Device>lambdaQuery().eq(Device::getNodeId, dto.getNodeId()));
    if (count > 0) {
      throw new BaseException("设备标识 - 重复");
    }

    // 该位置是否已绑定设备
    Integer physicalLocationType = dto.getPhysicalLocationType();
    count = this.count(Wrappers.<Device>lambdaQuery()
        .eq(Device::getProductKey, dto.getProductKey())
        .eq(Device::getLocationType, dto.getLocationType())
        .eq(physicalLocationType != null, Device::getPhysicalLocationType, physicalLocationType)
        .eq(Device::getBindingLocation, dto.getBindingLocation())
    );
    if (count > 0) {
      throw new BaseException("设备位置 - 重复");
    }

    // 注册IoT设备
    AddDeviceRequest req = new AddDeviceRequest();
    AddDevice body = new AddDevice();
    AuthInfo authInfo = new AuthInfo();

    body.setNodeId(dto.getNodeId());
    body.setProductId(dto.getProductKey());
    body.setDeviceName(dto.getDeviceName());
    authInfo.setSecret(IdUtils.simpleUUID());
    body.setAuthInfo(authInfo);
    req.setBody(body);

    AddDeviceResponse resp = client.addDevice(req);
    if (resp.getHttpStatusCode() != HttpStatus.CREATED) {
      throw new BaseException("注册设备 - 失败");
    }

    // 保存到数据库
    Device device = BeanUtil.toBean(dto, Device.class);
    device.setIotId(resp.getDeviceId());
    device.setSecret(authInfo.getSecret());
    // 补全参数
    if (this.save(device)) {
      return 1;
    } else {
      return 0;
    }
  }

  /**
   * 查询设备详情
   *
   * @param iotId iotId
   * @return vo
   */
  @Override
  public DeviceDetailVo queryDeviceDetail(String iotId) {

    // 查询本地数据库
    Device device = this.getOne(Wrappers.<Device>lambdaQuery().eq(Device::getIotId, iotId));
    if (ObjectUtil.isEmpty(device)) {
      throw new BaseException("本地设备不存在");
    }

    // 查询物联网平台
    ShowDeviceRequest req = new ShowDeviceRequest();
    req.setDeviceId(iotId);
    ShowDeviceResponse resp = client.showDevice(req);
    if (resp.getHttpStatusCode() != HttpStatus.SUCCESS) {
      throw new BaseException("IoT 平台未查询到该设备");
    }

    // 封装数据
    DeviceDetailVo vo = BeanUtil.toBean(device, DeviceDetailVo.class);
    vo.setDeviceStatus(resp.getStatus());
    String activeTime = resp.getActiveTime();
    if (StringUtils.isNotEmpty(activeTime)) {
      LocalDateTime tmp = LocalDateTime.parse(activeTime, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
      vo.setActiveTime(DateTimeZoneConverter.utcToShanghai(tmp));
    }
    return vo;
  }

  /**
   * 查询设备影子数据
   *
   * @param iotId iotId
   * @return 影子数据 map
   */
  @Override
  public List<Map<String, Object>> queryServiceProperties(String iotId) {
    ShowDeviceShadowRequest req = new ShowDeviceShadowRequest();
    req.setDeviceId(iotId);
    ShowDeviceShadowResponse resp = null;

    try {
      resp = client.showDeviceShadow(req);
    } catch (Exception e) {
      throw new RuntimeException(e.getMessage());
    }

    if (resp.getHttpStatusCode() != HttpStatus.SUCCESS) {
      throw new BaseException("影子数据获取 - 失败");
    }

    // File file = FileUtil.newFile("digital-nursing-platform/json/showDeviceShadow.json");
    // FileUtil.writeUtf8String(JSONUtil.toJsonStr(resp), file);

    List<DeviceShadowData> shadow = resp.getShadow();
    if (shadow.isEmpty()) {
      return null;
    }
    DeviceShadowProperties reported = shadow.get(0).getReported();
    String eventTimeStr = reported.getEventTime();
    Object properties = reported.getProperties();
    JSONObject jsonObject = JSONUtil.parseObj(properties);

    LocalDateTime time = LocalDateTimeUtil.parse(eventTimeStr, "yyyyMMdd'T'HHmmss'Z'");
    LocalDateTime eventTime = DateTimeZoneConverter.utcToShanghai(time);

    List<Map<String, Object>> list = new ArrayList<>();
    jsonObject.forEach((k, v
    ) -> {
      Map<String, Object> map = new HashMap<>();
      map.put("functionId", k);
      map.put("eventTime", eventTime);
      map.put("value", v);
      list.add(map);
    });

    return list;
  }

  /**
   * 修改设备
   *
   * @param dto dto
   * @return 返回值
   */
  @Override
  public boolean updateIotDevice(DeviceDto dto) {

    Device deviceDb = this.getById(dto.getId());

    // 设备类型判断
    if (!dto.getLocationType().equals(deviceDb.getLocationType())) {
      throw new BaseException("设备类型 - 不匹配");
    }

    // 更新数据库
    // 该位置是否已绑定设备
    String bindingLocation = dto.getBindingLocation();
    Integer physicalLocationType = dto.getPhysicalLocationType();

    if (Objects.nonNull(bindingLocation) && bindingLocation.equals(deviceDb.getBindingLocation())
        || Objects.nonNull(physicalLocationType) && physicalLocationType.equals(deviceDb.getPhysicalLocationType())
    ) {
      log.info("未修改接入位置");
    } else {
      long count = this.count(Wrappers.<Device>lambdaQuery()
          .eq(Device::getProductKey, dto.getProductKey())
          .eq(Device::getLocationType, dto.getLocationType())
          .eq(physicalLocationType != null, Device::getPhysicalLocationType, physicalLocationType)
          .eq(Device::getBindingLocation, dto.getBindingLocation())
      );
      if (count > 0) {
        throw new BaseException("接入位置 - 重复");
      }
    }

    // 更新iot平台设备
    UpdateDeviceRequest req = new UpdateDeviceRequest();
    req.setDeviceId(dto.getIotId());
    UpdateDevice body = new UpdateDevice();

    AuthInfoWithoutSecret authInfo = new AuthInfoWithoutSecret();
    body.setAuthInfo(authInfo);
    body.setDeviceName(dto.getDeviceName());
    req.setBody(body);
    UpdateDeviceResponse resp;
    try {
      resp = client.updateDevice(req);
    } catch (Exception e) {
      throw new RuntimeException(e.getMessage());
    }

    if (resp.getHttpStatusCode() != HttpStatus.SUCCESS) {
      throw new BaseException("更新设备 - 失败");
    }

    BeanUtils.copyProperties(dto, deviceDb);
    this.updateById(deviceDb);
    return true;
  }

  /**
   * 删除设备
   *
   * @param iotId iotId
   * @return 返回值
   */
  @Override
  public boolean deleteDevice(String iotId) {
    // 删除iot平台的设备
    DeleteDeviceRequest req = new DeleteDeviceRequest();
    req.setDeviceId(iotId);
    DeleteDeviceResponse resp = client.deleteDevice(req);
    if (resp.getHttpStatusCode() != HttpStatus.NO_CONTENT) {
      throw new BaseException("删除设备 - 失败");
    }

    // 删除数据库中的设备
    if (this.remove(Wrappers.<Device>lambdaQuery().eq(Device::getIotId, iotId))) {
      return true;
    } else {
      return false;
    }
  }
}
