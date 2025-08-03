package com.luruoyang.framework.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 华为云IoTDA配置属性类
 *
 * @author luruoyang
 */
@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "huawei")
public class HuaweiProperties {

  private Account account = new Account();
  private Iotda iotda = new Iotda();
  private int connectionCount = 4;
  /**
   * 开门命令所属服务id
   */
  private String smartDoorServiceId;

  /**
   * 开门记录属性
   */
  private String doorOpenPropertyName;

  /**
   * 开门命令
   */
  private String doorOpenCommandName;

  /**
   * 设置临时密码命令
   */
  private String passwordSetCommandName;

  /**
   * 仅支持true
   */
  private boolean useSsl = true;

  /**
   * IoTDA仅支持default
   */
  private String vhost = "default";

  /**
   * IoTDA仅支持PLAIN
   */
  private String saslMechanisms = "PLAIN";

  /**
   * true: SDK自动ACK（默认）
   * false:收到消息后，需要手动调用message.acknowledge()
   */
  private boolean isAutoAcknowledge = true;

  /**
   * 重连时延（ms）
   */
  private long reconnectDelay = 3000L;

  /**
   * 最大重连时延（ms）,随着重连次数增加重连时延逐渐增加
   */
  private long maxReconnectDelay = 30 * 1000L;

  /**
   * 最大重连次数,默认值-1，代表没有限制
   */
  private long maxReconnectAttempts = -1;

  /**
   * 空闲超时，对端在这个时间段内没有发送AMQP帧则会导致连接断开。默认值为30000。单位：毫秒。
   */
  private long idleTimeout = 30 * 1000L;

  /**
   * The values below control how many messages the remote peer can send to the client and be held in a pre-fetch buffer for each consumer instance.
   */
  private int queuePrefetch = 1000;

  /**
   * 扩展参数
   */
  private Map<String, String> extendedOptions;

  @Setter
  @Getter
  public static class Account {
    // Getters and Setters
    private String userName;
    private String accessKeyId;
    private String secretAccessKey;
    private String projectId;
    private String regionId;

  }

  @Setter
  @Getter
  public static class Iotda {
    private Access access = new Access();

    @Setter
    @Getter
    public static class Access {
      private Application application = new Application();
      private Device device = new Device();

      @Setter
      @Getter
      public static class Application {
        private String endpoint;
        private Amqps amqps = new Amqps();

        @Setter
        @Getter
        public static class Amqps {
          private String accessKey;
          private String accessCode;
          private String queueName;
          private int port = 5671;


        }
      }

      @Setter
      @Getter
      public static class Device {
        private String endpoint;

      }
    }
  }
}
