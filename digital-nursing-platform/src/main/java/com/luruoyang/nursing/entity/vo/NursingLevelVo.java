package com.luruoyang.nursing.entity.vo;

import com.luruoyang.nursing.entity.domain.NursingLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author luruoyang
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class NursingLevelVo extends NursingLevel {

  /**
   * 护理计划名称
   */
  private String planName;
}
