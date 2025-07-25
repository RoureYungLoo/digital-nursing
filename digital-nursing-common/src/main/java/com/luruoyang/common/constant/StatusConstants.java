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
   * 0 未生效
   * 1 已生效
   * 2 已过期
   * 3 已失效
   */
  @ApiModelProperty("未生效")
  public static final Integer PENDING = 0;
  @ApiModelProperty("已生效")
  public static final Integer ACTIVE = 1;
  @ApiModelProperty("已过期")
  public static final Integer EXPIRED = 2;
  @ApiModelProperty("已失效")
  public static final Integer INVALID = 3;

  /**
   * 老人状态
   * 0 禁用
   * 1 启用
   * 2 请假
   * 3 退住中
   * 4 入住中
   * 5 已退住
   * 6 已入住
   */
  /* 0 禁用 */
  public static final Integer ELDER_DISABLED = 0;
  public static final Integer ELDER_ENABLED = 1;
  public static final Integer ELDER_ON_HOLIDAY = 2;
  public static final Integer ELDER_CHECKING_OUT = 3;
  public static final Integer ELDER_CHECKING_IN = 4;
  public static final Integer ELDER_CHECKED_OUT = 5;
  public static final Integer ELDER_CHECKED_IN = 6;
}
