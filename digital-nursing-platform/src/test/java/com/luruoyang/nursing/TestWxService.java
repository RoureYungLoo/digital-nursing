package com.luruoyang.nursing;

import cn.hutool.core.io.FileUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.luruoyang.nursing.entity.vo.ElderVO;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class TestWxService {
  @Test
  public void testCode2Session() {
    String code2SessionURL = "https://api.weixin.qq.com/sns/jscode2session";
    Map<String, Object> params = new HashMap<>();
    params.put("appid", "wx718e2a5c0a79ad8a");
    params.put("secret", "9e62707b606171dde26079f7444ce4d8");
    params.put("js_code", "0b3YMc000pVzHU1c0j000fE0ao3YMc0v");
    params.put("grant_type", "authorization_code");

    String res = HttpUtil.get(code2SessionURL, params, 500);
    File file = FileUtil.newFile("json/Code2Session.json");
    FileUtil.writeUtf8String(res, file);
  }

  @Test
  public void testGetAccessToken() {
    String accessTokenURL = "https://api.weixin.qq.com/cgi-bin/token";
    Map<String, Object> params = new HashMap<>();
    params.put("appid", "wx718e2a5c0a79ad8a");
    params.put("secret", "9e62707b606171dde26079f7444ce4d8");
    params.put("grant_type", "client_credential");
    String res = HttpUtil.get(accessTokenURL, params, 500);
    File file = FileUtil.newFile("json/accessToken.json");
    FileUtil.writeUtf8String(res, file);
  }

  @Test
  public void testPhoneNumber() {
    String phoneNumberURL = "https://api.weixin.qq.com/wxa/business/getuserphonenumber?access_token=%s";

    phoneNumberURL = String.format(phoneNumberURL, "94_qAcJHCQRXrjilWdpXwD9MFTfuXYN8Kzxxk6Y2Km293u9BeQ-UpIPVcFSJqpH0-Dmy3aFeKvnXV1kyCx8_gNv8XEf3S-7JUP9rhjcLgm0Tgtg0m0jI8V_f9-rpUIQVAjAHACHM");
    System.out.println(phoneNumberURL);

    Map<String, Object> params = new HashMap<>();
    params.put("code", "ed2a1c851a5e15171fcc94be86c6c6fe6cd1ddb59fe1a846d8724f0a496d6c02");
    String jsonStr = JSONUtil.toJsonStr(params);
    System.out.println(jsonStr);
    String res = HttpUtil.post(phoneNumberURL, jsonStr, 500);
    File file = FileUtil.newFile("json/phoneNumber.json");
    FileUtil.writeUtf8String(res, file);

  }
}
