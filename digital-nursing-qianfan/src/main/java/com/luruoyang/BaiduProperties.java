package com.luruoyang;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author luruoyang
 */
@Data
@Component
@ConfigurationProperties(prefix = "baidu.qianfan")
public class BaiduProperties {
  /* apiKey */
  private String apiKey;
  /* baseUrl */
  private String baseUrl;
  /* model */
  private String model;
}
