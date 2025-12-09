package codehelper.service;

import org.springframework.stereotype.Service;

@Service
public interface ArkAIService {
    /**
     * 仅调用豆包文本模型（纯文字提问）
     * @param question 文本问题
     * @return 模型生成的回答内容
     */
    String chatWithText(String question);

    String chatWithImage(String imageUrl, String question);

}