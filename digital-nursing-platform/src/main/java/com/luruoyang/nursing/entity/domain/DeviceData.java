package com.luruoyang.nursing.entity.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.luruoyang.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import com.luruoyang.common.core.domain.BaseEntity;

/**
 * 设备数据对象 device_data
 *
 * @author luruoyang
 * @date 2025-08-03
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("设备数据实体")
public class DeviceData extends BaseEntity {
  private static final long serialVersionUID = 1L;
  /**
   * 告警规则ID，自增主键
   */
  @ApiModelProperty(value = "主键ID")
  private Long id;

  @Excel(name = "设备名称")
  @ApiModelProperty(value = "设备名称")
  private String deviceName;

  @Excel(name = "设备ID")
  @ApiModelProperty(value = "设备ID")
  private String iotId;

  @Excel(name = "所属产品的key")
  @ApiModelProperty(value = "所属产品的key")
  private String productKey;

  @Excel(name = "产品名称")
  @ApiModelProperty(value = "产品名称")
  private String productName;

  @Excel(name = "功能名称")
  @ApiModelProperty(value = "功能名称")
  private String functionId;

  @Excel(name = "接入位置")
  @ApiModelProperty(value = "接入位置")
  private String accessLocation;

  @Excel(name = "位置类型 0：随身设备 1：固定设备")
  @ApiModelProperty(value = "位置类型 0：随身设备 1：固定设备")
  private Integer locationType;

  @Excel(name = "物理位置类型 0楼层 1房间 2床位")
  @ApiModelProperty(value = "物理位置类型 0楼层 1房间 2床位")
  private Integer physicalLocationType;

  @Excel(name = "位置备注")
  @ApiModelProperty(value = "位置备注")
  private String deviceDescription;

  @Excel(name = "数据值")
  @ApiModelProperty(value = "数据值")
  private String dataValue;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @Excel(name = "数据上报时间", width = 30, dateFormat = "yyyy-MM-dd")
  @ApiModelProperty(value = "数据上报时间")
  private LocalDateTime alarmTime;

}
