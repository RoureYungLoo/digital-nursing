package com.luruoyang.nursing.entity.domain;

import com.luruoyang.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import com.luruoyang.common.core.domain.BaseEntity;

//import java.io.Serial;

/**
 * 老人对象 elder
 *
 * @author luruoyang
 * @date 2025-07-23
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("老人实体")
public class Elder extends BaseEntity {
//  @Serial
  private static final long serialVersionUID = 1L;

  @ApiModelProperty(value = "主键ID")
  protected Long id;

  @Excel(name = "名称")
  @ApiModelProperty(value = "名称")
  protected String name;

  @Excel(name = "图片")
  @ApiModelProperty(value = "图片")
  protected String image;

  @Excel(name = "身份证号")
  @ApiModelProperty(value = "身份证号")
  protected String idCardNo;

  @Excel(name = "性别", readConverterExp = "0=:女,1=:男")
  @ApiModelProperty(value = "性别")
  protected Integer sex;

  @Excel(name = "状态", readConverterExp = "0=：禁用，1:启用,2=:请假,3=:退住中,4=入住中,5=已退住")
  @ApiModelProperty(value = "状态")
  protected Integer status;

  @Excel(name = "手机号")
  @ApiModelProperty(value = "手机号")
  protected String phone;

  @Excel(name = "出生日期")
  @ApiModelProperty(value = "出生日期")
  protected String birthday;

  @Excel(name = "家庭住址")
  @ApiModelProperty(value = "家庭住址")
  protected String address;

  @Excel(name = "身份证国徽面")
  @ApiModelProperty(value = "身份证国徽面")
  protected String idCardNationalEmblemImg;

  @Excel(name = "身份证人像面")
  @ApiModelProperty(value = "身份证人像面")
  protected String idCardPortraitImg;

  @Excel(name = "床位编号")
  @ApiModelProperty(value = "床位编号")
  protected String bedNumber;

  @Excel(name = "床位id")
  @ApiModelProperty(value = "床位id")
  protected Long bedId;
}
