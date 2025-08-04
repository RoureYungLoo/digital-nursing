package com.luruoyang.nursing.entity.vo;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author luruoyang
 */
@Data
public class DeviceVo {
  @ApiModelProperty(value = "主键ID")
  private Long id;

  @ApiModelProperty(value = "物联网设备ID")
  private String iotId;

  @ApiModelProperty(value = "设备名称")
  private String deviceName;

  @ApiModelProperty(value = "产品key")
  private String productKey;

  @ApiModelProperty(value = "产品名称")
  private String productName;

  private List<DeviceDataVo> deviceDataVos;
}
