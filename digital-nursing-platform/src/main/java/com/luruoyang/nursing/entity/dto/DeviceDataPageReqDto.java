package com.luruoyang.nursing.entity.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author luruoyang
 */
@Data
@ApiModel("设备数据分页查询参数")
public class DeviceDataPageReqDto {
  private int pageSize;
  private int pageNum;
  private LocalDateTime startTime;
  private LocalDateTime endTime;
  private String deviceId;
  private String deviceName;
  private String functionId;
  private int total;
}
