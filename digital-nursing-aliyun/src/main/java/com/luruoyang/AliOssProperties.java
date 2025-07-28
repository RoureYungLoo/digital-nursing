package com.luruoyang;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author luruoyang
 */
@Data
@Component
@ConfigurationProperties(prefix = "aliyun.oss")
public class AliOssProperties {
  private String endpoint;
  private String bucketName;
  private String region;
  private String accessKeyId;
  private String accessKeySecret;
}
