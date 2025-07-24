package com.luruoyang.nursing.entity.dto;

import com.luruoyang.nursing.entity.domain.CheckInConfig;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author luruoyang
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CheckInConfigDto extends CheckInConfig {
  private LocalDate startDate;
  private LocalDate endDate;
  private Long bedId;
  private String code;
  private Long floorId;
  private String floorName;
  private Long roomId;
  private BigDecimal deposit;
}
