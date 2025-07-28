package com.luruoyang;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.chat.completions.ChatCompletion;
import com.openai.models.chat.completions.ChatCompletionCreateParams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author luruoyang
 */
@Service
@Slf4j
public class IBaiduServiceImpl implements IBaiduService {

  @Autowired
  private BaiduProperties baiduProperties;

  @Override
  public String callQianFanAI(String prompt) {
    return AIUtil.invokeQianFan(baiduProperties, prompt);
  }
}
