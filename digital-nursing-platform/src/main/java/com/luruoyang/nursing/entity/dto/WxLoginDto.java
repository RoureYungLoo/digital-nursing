package com.luruoyang.nursing.entity.dto;

import lombok.Data;

/**
 * @author luruoyang
 */
@Data
public class WxLoginDto {
  private String nickName;
  private String code;
  private String phoneCode;
}
