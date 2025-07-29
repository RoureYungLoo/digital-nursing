package com.luruoyang.nursing.entity.domain;

import com.luruoyang.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import com.luruoyang.common.core.domain.BaseEntity;

//import java.io.Serial;

/**
 * 护理计划和项目关联对象 nursing_item_plan
 *
 * @author luruoyang
 * @date 2025-07-22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("护理计划和项目关联实体")
public class NursingItemPlan extends BaseEntity {
//  @Serial
  private static final long serialVersionUID = 1L;
  @ApiModelProperty(value = "主键ID")
  protected Integer id;

  @Excel(name = "计划id")
  @ApiModelProperty(value = "计划id")
  protected Integer planId;

  @Excel(name = "项目id")
  @ApiModelProperty(value = "项目id")
  protected Integer projectId;

  @Excel(name = "计划执行时间")
  @ApiModelProperty(value = "计划执行时间")
  protected String executeTime;

  @Excel(name = "执行周期 0 天 1 周 2月")
  @ApiModelProperty(value = "执行周期 0 天 1 周 2月")
  protected Integer executeCycle;

  @Excel(name = "执行频次")
  @ApiModelProperty(value = "执行频次")
  protected Integer executeFrequency;
}
