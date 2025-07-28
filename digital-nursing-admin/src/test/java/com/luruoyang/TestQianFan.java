package com.luruoyang;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.chat.completions.ChatCompletion;
import com.openai.models.chat.completions.ChatCompletionCreateParams;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestQianFan {

  @Autowired
  private BaiduProperties baiduProperties;

  @Test
  public void test1() {
    String prompt = "你好,请简单介绍一下你自己";
    OpenAIClient client = OpenAIOkHttpClient.builder()
        .apiKey(baiduProperties.getApiKey())
        .baseUrl("https://qianfan.baidubce.com/v2/")
        .build();

    // 模型对应的model值，请查看支持的模型列表：https://cloud.baidu.com/doc/WENXINWORKSHOP/s/wm7ltcvgc
    ChatCompletionCreateParams params = ChatCompletionCreateParams.builder()
        .addUserMessage(prompt)
        .model("ernie-4.5-turbo-128k")
        .build();

    ChatCompletion chatCompletion = client.chat().completions().create(params);
    System.out.println(chatCompletion.choices().get(0).message().content());
  }
}
