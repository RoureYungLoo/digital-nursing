package com.luruoyang.nursing.wx.properties;


import com.luruoyang.nursing.entity.dto.WxLoginDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author luruoyang
 */
@Service
public class WxServiceImpl implements WxService {
  @Autowired
  private WxUtil wxUtil;

  @Override
  public String fetchOpenId(WxLoginDto loginDto) {
    String openid = wxUtil.getOpenId(loginDto);
    return openid;
  }

  @Override
  public String fetchPhoneNumber(WxLoginDto loginDto) {
    String phone = wxUtil.getPhoneNumber(loginDto);
    return phone;
  }
}
