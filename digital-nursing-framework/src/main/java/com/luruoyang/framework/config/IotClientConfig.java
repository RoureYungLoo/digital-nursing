package com.luruoyang.framework.config;


import com.huaweicloud.sdk.core.auth.BasicCredentials;
import com.huaweicloud.sdk.core.auth.ICredential;
import com.huaweicloud.sdk.core.region.Region;
import com.huaweicloud.sdk.iotda.v5.IoTDAClient;
import com.luruoyang.framework.config.properties.HuaWeiIotConfigProperties;
import com.luruoyang.framework.config.properties.HuaweiProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author luruoyang
 */
@Configuration
public class IotClientConfig {

  @Autowired
  private HuaWeiIotConfigProperties huaWeiIotConfigProperties;

  @Autowired
  private HuaweiProperties huaweiProperties;

  /* IoTClient */
  @Bean
  public IoTDAClient client() {
    HuaweiProperties.Account account = huaweiProperties.getAccount();
    HuaweiProperties.Iotda.Access.Application app = huaweiProperties.getIotda().getAccess().getApplication();
    ICredential auth = new BasicCredentials()
        .withAk(account.getAccessKeyId())
        .withSk(account.getSecretAccessKey())
        // 标准版/企业版需要使用衍生算法，基础版请删除配置"withDerivedPredicate"
        .withDerivedPredicate(BasicCredentials.DEFAULT_DERIVED_PREDICATE)
        .withProjectId(account.getProjectId());

    return IoTDAClient.newBuilder()
        .withCredential(auth)
        // 标准版/企业版：需自行创建Region对象，基础版：请使用IoTDARegion的region对象，如"withRegion(IoTDARegion.CN_NORTH_4)"
        .withRegion(new Region(account.getRegionId(), app.getEndpoint()))
        // .withRegion(IoTDARegion.CN_NORTH_4)
        .build();
  }

//  @Bean
//  public IoTDAClient huaWeiIotInstance() {
//    ICredential auth = new BasicCredentials()
//        .withAk(huaWeiIotConfigProperties.getAk())
//        .withSk(huaWeiIotConfigProperties.getSk())
//        // 标准版/企业版需要使用衍生算法，基础版请删除配置"withDerivedPredicate"
//        .withDerivedPredicate(BasicCredentials.DEFAULT_DERIVED_PREDICATE)
//        .withProjectId(huaWeiIotConfigProperties.getProjectId());
//
//    return IoTDAClient.newBuilder()
//        .withCredential(auth)
//        // 标准版/企业版：需自行创建Region对象，基础版：请使用IoTDARegion的region对象，如"withRegion(IoTDARegion.CN_NORTH_4)"
//        .withRegion(new Region(huaWeiIotConfigProperties.getRegionId(), huaWeiIotConfigProperties.getEndpoint()))
//        // .withRegion(IoTDARegion.CN_NORTH_4)
//        .build();
//  }

}