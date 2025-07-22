package com.luruoyang;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "aliyun.oss")
public class AliOssProperties {

  //  @Value("${aliyun.oss.endpoint}")
  private String endpoint;

  //  @Value("${aliyun.oss.bucketName}")
  private String bucketName;

  //  @Value("${aliyun.oss.region}")
  private String region;

  //  @Value("${aliyun.oss.accessKeyId}")
  private String accessKeyId;

  //  @Value("${aliyun.oss.accessKeySecret}")
  private String accessKeySecret;
}
