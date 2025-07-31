package com.luruoyang.nursing;

import ch.qos.logback.core.util.LocationUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.net.url.UrlBuilder;
import cn.hutool.core.util.IdUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.http.Method;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.luruoyang.common.constant.StatusConstants;
import com.luruoyang.common.utils.http.HttpUtils;
import com.luruoyang.nursing.entity.domain.NursingItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class TestHttpClient {

  @Test
  public void testHttpGet() {
    String s = HttpUtils.sendGet("http://www.baidu.com");

    try {
      FileWriter fw = new FileWriter("baidu.html");
      fw.write(s);
    } catch (IOException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }

  }

  @Test
  public void testHttpGet2() {
    HashMap<String, Object> params = new HashMap<>();
    params.put("pageNum", 1);
    params.put("pageSize", 5);

    HttpRequest req = HttpUtil.createRequest(Method.GET, "http://localhost:8080/serve/project/list");
    Map<String, String> headers = new HashMap<>();
    headers.put("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImxvZ2luX3VzZXJfa2V5IjoiYTVjMmIyZmUtZjgzYS00N2I4LWFhYTYtY2Y0ODU1M2U2ZDM1In0.cf3RcDeDPdfLWGLXyMPOcsh4im-LnoMBCOiamRnGX67y0-zbrDSxT3LteeaMk1GOkiNUo02r8hnBm6hSZq4XeA");
    req.addHeaders(headers);
    req.form(params);

    try (FileWriter w = new FileWriter("httpGet2.json");
         HttpResponse resp = req.execute();) {
      if (resp.isOk()) {
        w.write(resp.body());
      } else {
        System.out.println("resp no ok");
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  public void testHttpPost() {
    String url = "http://localhost:8080/serve/project";
    NursingItem body = NursingItem.builder()
        .id(IdUtil.getSnowflakeNextId())
        .image("image")
        .name("护理项目A")
        .nursingRequirement("护理要求")
        .orderNo(12)
        .price(new BigDecimal("123.45"))
        .status(StatusConstants.ENABLE)
        .unit("次")
        .build();
    int timeout = 500;
    HttpRequest req = HttpUtil.createRequest(Method.POST, url);
    req.header("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImxvZ2luX3VzZXJfa2V5IjoiYTVjMmIyZmUtZjgzYS00N2I4LWFhYTYtY2Y0ODU1M2U2ZDM1In0.cf3RcDeDPdfLWGLXyMPOcsh4im-LnoMBCOiamRnGX67y0-zbrDSxT3LteeaMk1GOkiNUo02r8hnBm6hSZq4XeA");
    req.body(JSONUtil.toJsonStr(body));
    req.timeout(timeout);
    HttpResponse resp = req.execute();
    if (resp.isOk()) {
      File file = FileUtil.newFile("添加护理项目.json");
      FileUtil.appendUtf8String(resp.body(), file);
    }
  }

  @Test
  public void testDelete() {
    HttpRequest req = HttpUtil.createRequest(Method.DELETE, "http://localhost:8080/serve/project/1950750705077788672");
    req.header("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImxvZ2luX3VzZXJfa2V5IjoiYTVjMmIyZmUtZjgzYS00N2I4LWFhYTYtY2Y0ODU1M2U2ZDM1In0.cf3RcDeDPdfLWGLXyMPOcsh4im-LnoMBCOiamRnGX67y0-zbrDSxT3LteeaMk1GOkiNUo02r8hnBm6hSZq4XeA");
    HttpResponse resp = req.execute();
    if (resp.isOk()) {
      File file = FileUtil.newFile("删除护理项目.json");
      FileUtil.clean(file);
      FileUtil.writeUtf8String(JSONUtil.toJsonStr(resp.body()), file);
    }
  }

  @Test
  @DisplayName("测试墨迹天气")
  public void testMojiWeather() {
    String url = "https://aliv1.data.moji.com/whapi/json/aliweather/briefforecast6days";
    HttpRequest req = HttpUtil.createRequest(Method.POST, url);

    String mojiAppcode = System.getenv("MOJI_APPCODE");
    System.out.println(mojiAppcode);
    req.header("Authorization", "APPCODE " + mojiAppcode);
    req.header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
    Map<String, Object> map = new HashMap<>();
    map.put("lat", "39.91488908");
    map.put("lon", "116.40387397");
    map.put("AppCode", mojiAppcode);
    req.form(map);

    HttpResponse resp = req.execute();
    if (resp.isOk()) {
      String body = resp.body();
      System.out.println(body);
      File file = FileUtil.newFile("查询天气.json");
      FileUtil.writeUtf8String(body, file);
    }
  }
}
