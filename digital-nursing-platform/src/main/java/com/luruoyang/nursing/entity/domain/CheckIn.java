package com.luruoyang.nursing.entity.domain;

//import java.io.Serial;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.luruoyang.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import com.luruoyang.common.core.domain.BaseEntity;

/**
 * 入住对象 check_in
 *
 * @author luruoyang
 * @date 2025-07-23
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("入住实体")
@Builder
public class CheckIn extends BaseEntity {
//  @Serial
  private static final long serialVersionUID = 1L;

  @ApiModelProperty(value = "主键ID")
  private Long id;

  @Excel(name = "老人姓名")
  @ApiModelProperty(value = "老人姓名")
  private String elderName;

  @Excel(name = "老人ID")
  @ApiModelProperty(value = "老人ID")
  private Long elderId;

  @Excel(name = "身份证号")
  @ApiModelProperty(value = "身份证号")
  private String idCardNo;

  @JsonFormat(pattern = "yyyy-MM-dd")
  @Excel(name = "入住开始时间", width = 30, dateFormat = "yyyy-MM-dd")
  @ApiModelProperty(value = "入住开始时间")
  private LocalDate startDate;

  @JsonFormat(pattern = "yyyy-MM-dd")
  @Excel(name = "入住结束时间", width = 30, dateFormat = "yyyy-MM-dd")
  @ApiModelProperty(value = "入住结束时间")
  private LocalDate endDate;

  @Excel(name = "护理等级名称")
  @ApiModelProperty(value = "护理等级名称")
  private String nursingLevelName;

  @Excel(name = "入住床位")
  @ApiModelProperty(value = "入住床位")
  private String bedNumber;

  @Excel(name = "状态 (0: 已入住, 1: 已退住)")
  @ApiModelProperty(value = "状态 (0: 已入住, 1: 已退住)")
  private Integer status;

  @Excel(name = "排序编号")
  @ApiModelProperty(value = "排序编号")
  private Integer sortOrder;
}
