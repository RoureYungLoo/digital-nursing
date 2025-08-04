package com.luruoyang.nursing.entity.domain;

import com.luruoyang.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.luruoyang.common.core.domain.BaseEntity;

/**
 * 报警规则对象 alert_rule
 *
 * @author luruoyang
 * @date 2025-08-04
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("报警规则实体")
public class AlertRule extends BaseEntity{
private static final long serialVersionUID = 1L;
    /** $column.columnComment */
      @ApiModelProperty(value = "主键ID")
    private Long id;
    /** 所属产品的key */
        @Excel(name = "所属产品的key")
      @ApiModelProperty(value = "所属产品的key")
    private String productKey;
    /** 产品名称 */
        @Excel(name = "产品名称")
      @ApiModelProperty(value = "产品名称")
    private String productName;
    /** 模块的key */
        @Excel(name = "模块的key")
      @ApiModelProperty(value = "模块的key")
    private String moduleId;
    /** 模块名称 */
        @Excel(name = "模块名称")
      @ApiModelProperty(value = "模块名称")
    private String moduleName;
    /** 功能名称 */
        @Excel(name = "功能名称")
      @ApiModelProperty(value = "功能名称")
    private String functionName;
    /** 功能标识 */
        @Excel(name = "功能标识")
      @ApiModelProperty(value = "功能标识")
    private String functionId;
    /** 物联网设备id */
        @Excel(name = "物联网设备id")
      @ApiModelProperty(value = "物联网设备id")
    private String iotId;
    /** 设备名称 */
        @Excel(name = "设备名称")
      @ApiModelProperty(value = "设备名称")
    private String deviceName;
    /** 报警数据类型，0：老人异常数据，1：设备异常数据 */
        @Excel(name = "报警数据类型，0：老人异常数据，1：设备异常数据")
      @ApiModelProperty(value = "报警数据类型，0：老人异常数据，1：设备异常数据")
    private Integer alertDataType;
    /** 告警规则名称 */
        @Excel(name = "告警规则名称")
      @ApiModelProperty(value = "告警规则名称")
    private String alertRuleName;
    /** 运算符 */
        @Excel(name = "运算符")
      @ApiModelProperty(value = "运算符")
    private String operator;
    /** 阈值 */
        @Excel(name = "阈值")
      @ApiModelProperty(value = "阈值")
    private Double value;
    /** 持续周期 */
        @Excel(name = "持续周期")
      @ApiModelProperty(value = "持续周期")
    private Long duration;
    /** 报警生效时段 */
        @Excel(name = "报警生效时段")
      @ApiModelProperty(value = "报警生效时段")
    private String alertEffectivePeriod;
    /** 报警沉默周期 */
        @Excel(name = "报警沉默周期")
      @ApiModelProperty(value = "报警沉默周期")
    private Integer alertSilentPeriod;
    /** 0 禁用 1启用 */
        @Excel(name = "0 禁用 1启用")
      @ApiModelProperty(value = "0 禁用 1启用")
    private Integer status;
}
