package com.luruoyang.nursing.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * @author luruoyang
 */
@Data
@ApiModel("设备数据分页查询参数")
public class DeviceDataPageReqDto {
  private int pageSize;
  private int pageNum;
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime startTime;
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime endTime;
  private String deviceId;
  private String deviceName;
  private String functionId;
  private int total;
}
