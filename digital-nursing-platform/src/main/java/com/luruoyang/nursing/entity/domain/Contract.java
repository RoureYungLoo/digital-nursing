package com.luruoyang.nursing.entity.domain;

//import java.io.Serial;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.luruoyang.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import com.luruoyang.common.core.domain.BaseEntity;

/**
 * 合同对象 contract
 *
 * @author luruoyang
 * @date 2025-07-23
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("合同实体")
public class Contract extends BaseEntity {
//  @Serial
  private static final long serialVersionUID = 1L;

  @ApiModelProperty(value = "主键ID")
  private Long id;

  @Excel(name = "老人ID")
  @ApiModelProperty(value = "老人ID")
  private Long elderId;

  @Excel(name = "合同名称")
  @ApiModelProperty(value = "合同名称")
  private String contractName;

  @Excel(name = "合同编号")
  @ApiModelProperty(value = "合同编号")
  private String contractNumber;

  @Excel(name = "协议地址", readConverterExp = "文=件路径或URL")
  @ApiModelProperty(value = "协议地址")
  private String agreementPath;

  @Excel(name = "丙方手机号")
  @ApiModelProperty(value = "丙方手机号")
  private String thirdPartyPhone;

  @Excel(name = "丙方姓名")
  @ApiModelProperty(value = "丙方姓名")
  private String thirdPartyName;

  @Excel(name = "老人姓名")
  @ApiModelProperty(value = "老人姓名")
  private String elderName;

  @JsonFormat(pattern = "yyyy-MM-dd")
  @Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd")
  @ApiModelProperty(value = "开始时间")
  private LocalDate startDate;

  @JsonFormat(pattern = "yyyy-MM-dd")
  @Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd")
  @ApiModelProperty(value = "结束时间")
  private LocalDate endDate;

  @Excel(name = "状态 (0: 未生效, 1: 已生效, 2: 已过期, 3: 已失效)")
  @ApiModelProperty(value = "状态 (0: 未生效, 1: 已生效, 2: 已过期, 3: 已失效)")
  private Integer status;

  @JsonFormat(pattern = "yyyy-MM-dd")
  @Excel(name = "签约日期", width = 30, dateFormat = "yyyy-MM-dd")
  @ApiModelProperty(value = "签约日期")
  private LocalDate signDate;

  @Excel(name = "解除提交人")
  @ApiModelProperty(value = "解除提交人")
  private String terminationSubmitter;

  @JsonFormat(pattern = "yyyy-MM-dd")
  @Excel(name = "解除日期", width = 30, dateFormat = "yyyy-MM-dd")
  @ApiModelProperty(value = "解除日期")
  private LocalDate terminationDate;

  @Excel(name = "解除协议地址", readConverterExp = "文=件路径或URL")
  @ApiModelProperty(value = "解除协议地址")
  private String terminationAgreementPath;

  @Excel(name = "排序编号")
  @ApiModelProperty(value = "排序编号")
  private Long sortOrder;
}
