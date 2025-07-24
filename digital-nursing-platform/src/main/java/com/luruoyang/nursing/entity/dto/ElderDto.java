package com.luruoyang.nursing.entity.dto;

import com.luruoyang.nursing.entity.domain.Elder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author luruoyang
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ElderDto extends Elder {
  private String age;
}
