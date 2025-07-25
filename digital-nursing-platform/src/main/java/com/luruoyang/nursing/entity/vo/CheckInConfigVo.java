package com.luruoyang.nursing.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.luruoyang.nursing.entity.domain.CheckInConfig;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author luruoyang
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CheckInConfigVo extends CheckInConfig {
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime startDate;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime endDate;
  private Long bedId;
  private String code;
  private Long floorId;
  private String floorName;
  private Long roomId;
  private BigDecimal deposit;
}
