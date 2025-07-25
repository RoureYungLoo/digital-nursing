package com.luruoyang.nursing.entity.domain;

import java.io.Serial;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.luruoyang.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import com.luruoyang.common.core.domain.BaseEntity;

/**
 * 入住配置对象 check_in_config
 *
 * @author luruoyang
 * @date 2025-07-23
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("入住配置实体")
public class CheckInConfig extends BaseEntity {
  @Serial
  private static final long serialVersionUID = 1L;

  @ApiModelProperty(value = "主键ID")
  protected Long id;

  @Excel(name = "入住表ID")
  @ApiModelProperty(value = "入住表ID")
  protected Long checkInId;

  @Excel(name = "护理等级ID")
  @ApiModelProperty(value = "护理等级ID")
  protected Long nursingLevelId;

  @Excel(name = "护理等级名称")
  @ApiModelProperty(value = "护理等级名称")
  protected String nursingLevelName;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @Excel(name = "费用开始时间", width = 30, dateFormat = "yyyy-MM-dd")
  @ApiModelProperty(value = "费用开始时间")
  protected LocalDateTime feeStartDate;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @Excel(name = "费用结束时间", width = 30, dateFormat = "yyyy-MM-dd")
  @ApiModelProperty(value = "费用结束时间")
  protected LocalDateTime feeEndDate;

  @Excel(name = "押金", readConverterExp = "元=")
  @ApiModelProperty(value = "押金")
  protected BigDecimal deposit;

  @Excel(name = "护理费用", readConverterExp = "元=/月")
  @ApiModelProperty(value = "护理费用")
  protected BigDecimal nursingFee;

  @Excel(name = "床位费用", readConverterExp = "元=/月")
  @ApiModelProperty(value = "床位费用")
  protected BigDecimal bedFee;

  @Excel(name = "医保支付", readConverterExp = "元=/月")
  @ApiModelProperty(value = "医保支付")
  protected BigDecimal insurancePayment;

  @Excel(name = "政府补贴", readConverterExp = "元=/月")
  @ApiModelProperty(value = "政府补贴")
  protected BigDecimal governmentSubsidy;

  @Excel(name = "其他费用", readConverterExp = "元=/月")
  @ApiModelProperty(value = "其他费用")
  protected BigDecimal otherFees;

  @Excel(name = "排序编号")
  @ApiModelProperty(value = "排序编号")
  protected Integer sortOrder;
}
