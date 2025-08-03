package com.luruoyang.nursing.entity.domain;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.luruoyang.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import com.luruoyang.common.core.domain.BaseEntity;

/**
 * 预约信息对象 reservation
 *
 * @author luruoyang
 * @date 2025-07-31
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("预约信息实体")
public class Reservation extends BaseEntity {
  private static final long serialVersionUID = 1L;
  /**
   * 主键ID
   */
  @ApiModelProperty(value = "主键ID")
  private Long id;
  /**
   * 预约人姓名
   */
  @Excel(name = "预约人姓名")
  @ApiModelProperty(value = "预约人姓名")
  private String name;
  /**
   * 预约人手机号
   */
  @Excel(name = "预约人手机号")
  @ApiModelProperty(value = "预约人手机号")
  private String mobile;
  /**
   * 预约时间
   */
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @Excel(name = "预约时间", width = 30, dateFormat = "yyyy-MM-dd")
  @ApiModelProperty(value = "预约时间")
  private LocalDateTime time;
  /**
   * 探访人
   */
  @Excel(name = "探访人")
  @ApiModelProperty(value = "探访人")
  private String visitor;
  /**
   * 预约类型，0：参观预约，1：探访预约
   */
  @Excel(name = "预约类型，0：参观预约，1：探访预约")
  @ApiModelProperty(value = "预约类型，0：参观预约，1：探访预约")
  private Integer type;
  /**
   * 预约状态，0：待报道，1：已完成，2：取消，3：过期
   */
  @Excel(name = "预约状态，0：待报道，1：已完成，2：取消，3：过期")
  @ApiModelProperty(value = "预约状态，0：待报道，1：已完成，2：取消，3：过期")
  private Integer status;
}
