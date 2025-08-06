/*
 * Copyright (c) 2020-2023 Huawei Cloud Computing Technology Co., Ltd. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this list of
 *    conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list
 *    of conditions and the following disclaimer in the documentation and/or other materials
 *    provided with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its contributors may be used
 *    to endorse or promote products derived from this software without specific prior written
 *    permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
 * OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.luruoyang.task;


import com.huaweicloud.sdk.iot.device.CommandSample;
import com.huaweicloud.sdk.iot.device.IoTDevice;
import com.huaweicloud.sdk.iot.device.client.DeviceClient;
import com.huaweicloud.sdk.iot.device.client.requests.ServiceProperty;
import com.huaweicloud.sdk.iot.device.transport.ActionListener;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

/**
 * 设备属性的上报
 *
 * @author luruoyang
 */
@Slf4j
public class PropertyReportSmokeAlarm {

  private static final String IOT_ROOT_CA_RES_PATH = "ca.jks";

  private static final String IOT_ROOT_CA_TMP_PATH = "huaweicloud-iotda-tmp-" + IOT_ROOT_CA_RES_PATH;


  public static void main(String[] args) throws InterruptedException, IOException {

    // 加载iot平台的ca证书，进行服务端校验
    File tmpCAFile = new File(IOT_ROOT_CA_TMP_PATH);
    try (InputStream resource = CommandSample.class.getClassLoader().getResourceAsStream(IOT_ROOT_CA_RES_PATH)) {

      if (resource != null) {
        Files.copy(resource, tmpCAFile.toPath(), REPLACE_EXISTING);
      } else {
        log.error("resource is null");
      }
    }


    // 创建设备并初始化. 用户请替换为自己的接入地址。
    IoTDevice device = new IoTDevice(
        "ssl://4bbbeb0e66.st1.iotda-device.cn-east-3.myhuaweicloud.com:8883",
        "688c383394a9a05c33799235_room-smoke-alarmer",
        "981807341210468597778cf6fac19fd5", tmpCAFile);
    if (device.init() != 0) {
      return;
    }


    while (true) {

      Map<String, Object> json = new HashMap<>();
      Random rand = new SecureRandom();

      // 按照物模型设置属性
      // json.put("BodyTemp", rand.nextInt(42 - 33 + 1) + 33);
      // json.put("HeartRate", rand.nextInt(200 - 45 + 1) + 45);
      // json.put("Battery", rand.nextFloat() * 100.0f);
      //json.put("BloodOxygen", 1.0 * (rand.nextInt(100 - 5 + 1) + 5));

      /* 烟雾报警器 */
      int humidity = rand.nextInt(50 - 30 + 1) + 30;
      int temperature = rand.nextInt(100 - 20 + 1) + 20;
      json.put("CurrentHumidity", humidity);
      json.put("IndoorTemperature", temperature);
      json.put("SmokeSensorState", temperature >= 55);

      ServiceProperty serviceProperty = new ServiceProperty();
      serviceProperty.setProperties(json);
      // serviceId要和物模型一致
      serviceProperty.setServiceId("smoke_services");

      DeviceClient client = device.getClient();
      if (client == null) {
        log.error("client is null");
        return;
      }
      client.reportProperties(List.of(serviceProperty), new ActionListener() {
        @Override
        public void onSuccess(Object context) {
          log.info("pubMessage success");
        }

        @Override
        public void onFailure(Object context, Throwable var2) {
          log.error("reportProperties failed" + var2.toString());
        }
      });
      Thread.sleep(30000);
    }
  }
}
