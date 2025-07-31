package com.luruoyang.nursing.entity.vo;

import lombok.Builder;
import lombok.Data;

/**
 * @author luruoyang
 */
@Data
@Builder
public class WxLoginVo {
  private String token;
  private String nickName;
}
