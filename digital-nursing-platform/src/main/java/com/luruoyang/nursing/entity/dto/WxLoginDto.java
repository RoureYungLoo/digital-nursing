package com.luruoyang.nursing.entity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author luruoyang
 */
@Data
public class WxLoginDto {
  @ApiModelProperty("昵称")
  private String nickName;
  @ApiModelProperty("登录临时凭证")
  private String code;
  @ApiModelProperty("手机号临时凭证")
  private String phoneCode;
}
