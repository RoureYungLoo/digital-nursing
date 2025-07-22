package com.luruoyang.nursing.dto;

import com.luruoyang.nursing.domain.NursingItemPlan;
import com.luruoyang.nursing.domain.NursingPlan;
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
  List<NursingItemPlan> itemPlans;
}
