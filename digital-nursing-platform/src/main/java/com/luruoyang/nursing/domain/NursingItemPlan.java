package com.luruoyang.nursing.domain;

import com.luruoyang.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.luruoyang.common.core.domain.BaseEntity;

/**
 * 护理计划和项目关联对象 nursing_item_plan
 *
 * @author luruoyang
 * @date 2025-07-22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("护理计划和项目关联实体")
public class NursingItemPlan extends BaseEntity{
private static final long serialVersionUID = 1L;
    /**  */
      @ApiModelProperty(value = "主键ID")
    private Integer id;
    /** 计划id */
        @Excel(name = "计划id")
      @ApiModelProperty(value = "计划id")
    private Integer planId;
    /** 项目id */
        @Excel(name = "项目id")
      @ApiModelProperty(value = "项目id")
    private Integer itemId;
    /** 计划执行时间 */
        @Excel(name = "计划执行时间")
      @ApiModelProperty(value = "计划执行时间")
    private String executeTime;
    /** 执行周期 0 天 1 周 2月 */
        @Excel(name = "执行周期 0 天 1 周 2月")
      @ApiModelProperty(value = "执行周期 0 天 1 周 2月")
    private Integer executeCycle;
    /** 执行频次 */
        @Excel(name = "执行频次")
      @ApiModelProperty(value = "执行频次")
    private Integer executeFrequency;
}
