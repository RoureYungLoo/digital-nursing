package com.luruoyang;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.ResponseFormatJsonObject;
import com.openai.models.chat.completions.ChatCompletion;
import com.openai.models.chat.completions.ChatCompletionCreateParams;

/**
 * @author luruoyang
 */
public class AIUtil {
  public static String invokeQianFan(BaiduProperties properties, String prompt) {
    OpenAIClient client = OpenAIOkHttpClient.builder()
        .apiKey(properties.getApiKey())
        .baseUrl(properties.getBaseUrl())
        .build();

    ChatCompletionCreateParams params = ChatCompletionCreateParams.builder()
        .addUserMessage(prompt)
        .model(properties.getModel())
        .responseFormat(ChatCompletionCreateParams.ResponseFormat.ofJsonObject(ResponseFormatJsonObject.builder().build()))
        .build();

    ChatCompletion chatCompletion = client.chat().completions().create(params);
    return chatCompletion.choices().get(0).message().content().orElseGet(() -> "");
  }
}
