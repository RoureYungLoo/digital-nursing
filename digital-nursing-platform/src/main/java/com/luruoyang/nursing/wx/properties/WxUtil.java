package com.luruoyang.nursing.wx.properties;

import cn.hutool.core.io.FileUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson2.JSONPObject;
import com.luruoyang.nursing.entity.dto.WxLoginDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author luruoyang
 */
@Slf4j
@Component
public class WxUtil {
  @Autowired
  private WxProperties properties;

  /**
   * 获取手机号
   * @param loginDto dto
   * @return 手机号
   */
  public String getPhoneNumber(WxLoginDto loginDto) {
    String phoneNumberUrl = String.format(properties.getPhoneNumberUrl(), getAccessToken());
    Map<String, Object> params = new HashMap<>();
    params.put("code", loginDto.getPhoneCode());
    String jsonStr = JSONUtil.toJsonStr(params);
    String res = HttpUtil.post(phoneNumberUrl, jsonStr);
    JSONObject obj = JSONUtil.parseObj(res);
    if (obj.getInt("errcode") == 0) {
      JSONObject phoneInfo = obj.getJSONObject("phone_info");
      return phoneInfo.getStr("phoneNumber");
    }
    log.error("getPhoneNumber error");
    return null;
  }


  /**
   * 获取 openId
   * @param loginDto dto
   * @return openId
   */
  public String getOpenId(WxLoginDto loginDto) {
    Map<String, Object> params = new HashMap<>();
    params.put("appid", properties.getAppId());
    params.put("secret", properties.getAppSecret());
    params.put("js_code", loginDto.getCode());
    params.put("grant_type", "authorization_code");
    String res = HttpUtil.get(properties.getCode2SessionUrl(), params);
    JSONObject obj = JSONUtil.parseObj(res);
    return obj.getStr("openid");
  }

  /**
   * 获取微信 AccessToken
   * @return AccessToken
   */
  public String getAccessToken() {
    Map<String, Object> params = new HashMap<>();
    params.put("appid", properties.getAppId());
    params.put("secret", properties.getAppSecret());
    params.put("grant_type", "client_credential");
    String res = HttpUtil.get(properties.getAccessTokenUrl(), params);
    JSONObject obj = JSONUtil.parseObj(res);
    String accessToken = obj.getStr("access_token");
    return accessToken;
  }
}
