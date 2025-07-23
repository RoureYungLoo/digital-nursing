package com.luruoyang.nursing.entity.vo;

import com.luruoyang.nursing.entity.domain.NursingItemPlan;
import com.luruoyang.nursing.entity.domain.NursingPlan;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author luruoyang
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class NursingPlanVo extends NursingPlan {

  List<NursingItemPlanVo> nursingPlanList;
}
