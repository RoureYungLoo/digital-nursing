package com.luruoyang.nursing.entity.dto;

import com.luruoyang.nursing.entity.domain.NursingProjectPlan;
import com.luruoyang.nursing.entity.domain.NursingPlan;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author luruoyang
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class NursingPlanDto extends NursingPlan {

  /**
   * 护理项目关联护理计划
   */
  List<NursingProjectPlan> nursingPlanList;
}
