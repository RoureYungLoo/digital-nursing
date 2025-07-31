package com.luruoyang.nursing.entity.dto;

import com.luruoyang.nursing.entity.domain.Reservation;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @author luruoyang
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ReservationDto extends Reservation {
  private Integer pageNum;
  private Integer pageSize;
  private Integer status;
}
