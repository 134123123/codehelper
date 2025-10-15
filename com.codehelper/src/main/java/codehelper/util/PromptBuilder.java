package codehelper.util;

public class PromptBuilder {
    // 构造“生成代码”的提示词
    public static String buildGeneratePrompt(String userQuestion, String language) {
        return "请用" + language + "语言实现：" + userQuestion;
    }

    // 构造“解释代码”的提示词
    public static String buildExplainPrompt(String code) {
        return "请解释这段代码的逻辑：\n" + code;
    }
}