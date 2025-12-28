package codehelper.service;

import codehelper.model.vo.CodeResponseVO;
import codehelper.model.vo.ImageAnalysisVO;

public interface CodeAssistantService {
    /**
     * 生成指定语言的代码
     * @param userQuestion 用户的问题描述
     * @param language 目标编程语言（如java、python、javascript等）
     * @return 包含生成代码和解释的响应对象
     */
    CodeResponseVO generateCode(String userQuestion, String language);

    /**
     * 保存用户的代码记录
     * @param userId 用户ID
     * @param codeContent 代码内容
     * @param language 编程语言
     */
    void saveCodeRecord(Long userId, String codeContent, String language);

    /**
     * 分析图片并回答相关问题（多模态交互）
     * @param imageUrl 图片的URL地址
     * @param question 与图片相关的问题
     * @return 包含图片分析结果和回答的响应对象
     */
    ImageAnalysisVO analyzeImageWithQuestion(String imageUrl, String question);
    // 新增纯文本提问方法
    String askWithText(String question);
}
