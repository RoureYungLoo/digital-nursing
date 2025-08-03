package com.luruoyang.nursing.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.luruoyang.nursing.entity.domain.Device;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/**
 * @author luruoyang
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DeviceDetailVo extends Device {
  /**
   * 接入位置
   */
  @ApiModelProperty(value = "接入位置")
  private String remark;

  /**
   * 设备状态，ONLINE：设备在线，OFFLINE：设备离线，ABNORMAL：设备异常，INACTIVE：设备未激活，FROZEN：设备冻结
   */
  @ApiModelProperty(value = "设备状态，ONLINE：设备在线，OFFLINE：设备离线，ABNORMAL：设备异常，INACTIVE：设备未激活，FROZEN：设备冻结")
  private String deviceStatus;

  /**
   * 激活时间
   */
  @ApiModelProperty(value = "激活时间,格式：yyyy-MM-dd HH:mm:ss")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private LocalDateTime activeTime;

  /**
   * 创建人名称
   */
  @ApiModelProperty(value = "创建人名称")
  private String creator;

}
