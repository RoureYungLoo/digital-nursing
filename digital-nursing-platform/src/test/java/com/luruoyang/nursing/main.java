//package com.luruoyang.nursing;
//
//import com.openai.client.OpenAIClient;
//import com.openai.client.okhttp.OpenAIOkHttpClient;
//import com.openai.models.ChatCompletion;
//import com.openai.models.ChatCompletionCreateParams;
//import com.openai.models.ChatCompletionUserMessageParam;
//
//
//public class main {
//    public static void main(String[] args) {
//        OpenAIClient client = OpenAIOkHttpClient.builder()
//                .apiKey("bce-v3/ALTAK-c97ghgbf5EVpi3Zel75yU/f7c7fd0ebfa7118858df4625934b96e5efd2468e") //将your_APIKey替换为真实值，如何获取API Key请查看https://cloud.baidu.com/doc/WENXINWORKSHOP/s/Um2wxbaps#步骤二-获取api-key
//                .baseUrl("https://qianfan.baidubce.com/v2/") //千帆ModelBuilder平台地址
//                .build();
//
//        ChatCompletionCreateParams params = ChatCompletionCreateParams.builder()
//                .addUserMessage("你好") // 对话messages信息
//                .model("ernie-4.0-8k") // 模型对应的model值，请查看支持的模型列表：https://cloud.baidu.com/doc/WENXINWORKSHOP/s/wm7ltcvgc
//                .build();
//
//        ChatCompletion chatCompletion = client.chat().completions().create(params);
//        System.out.println(chatCompletion.choices().get(0).message().content());
//    }
//}