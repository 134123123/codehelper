package codehelper.service.impl;

import codehelper.service.ArkAIService;
import com.volcengine.ark.runtime.model.completion.chat.*;
import com.volcengine.ark.runtime.service.ArkService;
import okhttp3.ConnectionPool;
import okhttp3.Dispatcher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class ArkAIServiceImpl implements ArkAIService {

    @Value("${ark.api.key}")
    private String apiKey;

    @Value("${ark.base.url:https://ark.cn-beijing.volces.com/api/v3}")
    private String baseUrl;

    private ArkService arkService;

    @PostConstruct
    public void init() {
        if (apiKey == null || apiKey.trim().isEmpty()) {
            throw new IllegalArgumentException("ark.api.key 配置为空，请在 application.yml 中配置");
        }
        ConnectionPool connectionPool = new ConnectionPool(5, 1, TimeUnit.SECONDS);
        Dispatcher dispatcher = new Dispatcher();
        arkService = ArkService.builder()
                .dispatcher(dispatcher)
                .connectionPool(connectionPool)
                .baseUrl(baseUrl)
                .apiKey(apiKey)
                .build();
    }

    @Override
    public String chatWithText(String question) {
        List<ChatCompletionContentPart> parts = new ArrayList<>();
        // 仅添加文本问题
        parts.add(ChatCompletionContentPart.builder()
                .type("text")
                .text(question)
                .build());

        ChatMessage userMessage = ChatMessage.builder()
                .role(ChatMessageRole.USER)
                .multiContent(parts)
                .build();
        List<ChatMessage> messages = new ArrayList<>();
        messages.add(userMessage);

        ChatCompletionRequest request = ChatCompletionRequest.builder()
                .model("doubao-seed-1-6-250615") // 豆包文本模型 ID
                .messages(messages)
                .build();

        // 调用 Ark 服务并返回文本结果
        return (String) arkService.createChatCompletion(request)
                .getChoices()
                .stream()
                .findFirst()
                .map(choice -> choice.getMessage().getContent())
                .orElse("模型未返回有效回答");
    }

    @Override
    public String chatWithImage(String imageUrl, String question) {
        // 图片+文本交互逻辑
        List<ChatCompletionContentPart> parts = new ArrayList<>();
        // 添加图片部分
        parts.add(ChatCompletionContentPart.builder()
                .type("image_url")
                .imageUrl(new ChatCompletionContentPart.ChatCompletionContentPartImageURL(imageUrl))
                .build());
        // 添加文本问题部分
        parts.add(ChatCompletionContentPart.builder()
                .type("text")
                .text(question)
                .build());

        ChatMessage userMessage = ChatMessage.builder()
                .role(ChatMessageRole.USER)
                .multiContent(parts)
                .build();
        List<ChatMessage> messages = new ArrayList<>();
        messages.add(userMessage);

        ChatCompletionRequest request = ChatCompletionRequest.builder()
                .model("doubao-seed-1-6-250615") // 豆包多模态模型 ID
                .messages(messages)
                .build();

        return (String) arkService.createChatCompletion(request)
                .getChoices()
                .stream()
                .findFirst()
                .map(choice -> choice.getMessage().getContent())
                .orElse("模型未返回有效回答");
    }
}