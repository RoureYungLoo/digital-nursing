package com.luruoyang.common.constant;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author luruoyang
 */
@ApiModel("状态常量")
public class StatusConstants {

  /**
   * 启用/禁用
   */
  @ApiModelProperty("已禁用")
  public static final Integer DISABLE = 0;
  @ApiModelProperty("已启用")
  public static final Integer ENABLE = 1;

  /**
   * 床位状态
   */
  @ApiModelProperty("未入住")
  public static final Integer BED_NOT_USE = 0;
  @ApiModelProperty("已入住")
  public static final Integer BED_IN_USE = 1;

  /**
   * 办理入住/退住
   */
  @ApiModelProperty("已入住")
  public static final Integer CHECKED_IN = 0;
  @ApiModelProperty("已退住")
  public static final Integer CHECKED_OUT = 1;

  /**
   * 合同状态
   */
  @ApiModelProperty("未生效")
  public static final Integer PENDING = 0;
  @ApiModelProperty("已生效")
  public static final Integer ACTIVE = 1;
  @ApiModelProperty("已过期")
  public static final Integer EXPIRED = 2;
  @ApiModelProperty("已失效")
  public static final Integer INVALID = 3;
}
