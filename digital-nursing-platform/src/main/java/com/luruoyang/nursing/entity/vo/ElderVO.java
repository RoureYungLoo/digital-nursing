package com.luruoyang.nursing.entity.vo;

import com.luruoyang.nursing.entity.domain.Elder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author luruoyang
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ElderVO extends Elder {
  private String age;
}
