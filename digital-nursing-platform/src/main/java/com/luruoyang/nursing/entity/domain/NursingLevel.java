package com.luruoyang.nursing.entity.domain;

//import java.io.Serial;
import java.math.BigDecimal;

import com.luruoyang.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import com.luruoyang.common.core.domain.BaseEntity;

/**
 * 护理等级对象 nursing_level
 *
 * @author luruoyang
 * @date 2025-07-22
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("护理等级实体")
public class NursingLevel extends BaseEntity {
//  @Serial
  private static final long serialVersionUID = 1L;
  /**
   * 主键ID
   */
  @ApiModelProperty(value = "主键ID")
  protected Integer id;
  /**
   * 等级名称
   */
  @Excel(name = "等级名称")
  @ApiModelProperty(value = "等级名称")
  protected String name;
  /**
   * 护理计划ID
   */
  @Excel(name = "护理计划ID")
  @ApiModelProperty(value = "护理计划ID")
  protected Integer planId;
  /**
   * 护理费用
   */
  @Excel(name = "护理费用")
  @ApiModelProperty(value = "护理费用")
  protected BigDecimal fee;
  /**
   * 状态（0：禁用，1：启用）
   */
  @Excel(name = "状态", readConverterExp = "0=：禁用，1：启用")
  @ApiModelProperty(value = "状态")
  protected Integer status;
  /**
   * 等级说明
   */
  @Excel(name = "等级说明")
  @ApiModelProperty(value = "等级说明")
  protected String description;
}
