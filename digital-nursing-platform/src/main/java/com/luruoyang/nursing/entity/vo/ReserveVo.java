package com.luruoyang.nursing.entity.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class ReserveVo implements Serializable {
  private LocalDateTime time;
  private Integer count;
}
