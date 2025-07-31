package com.luruoyang.nursing.wx.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author luruoyang
 */
@Data
@Component
@ConfigurationProperties(prefix = "tencent.wx.mini-program")
public class WxProperties {
  private String appId;
  private String appSecret;
  private String code2SessionUrl;
  private String phoneNumberUrl;
  private String accessTokenUrl;
}
