package com.luruoyang.nursing.wx.properties;

import com.luruoyang.nursing.entity.dto.WxLoginDto;
import lombok.Data;


/**
 * @author luruoyang
 */
public interface WxService {
  String fetchOpenId(WxLoginDto loginDto);

  String fetchPhoneNumber(WxLoginDto loginDto);
}
