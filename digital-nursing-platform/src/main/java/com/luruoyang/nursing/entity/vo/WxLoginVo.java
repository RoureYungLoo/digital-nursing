package com.luruoyang.nursing.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * @author luruoyang
 */
@Data
@Builder
public class WxLoginVo {
  @ApiModelProperty("JWT token")
  private String token;
  @ApiModelProperty(value = "昵称")
  private String nickName;
}
