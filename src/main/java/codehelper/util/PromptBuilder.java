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

    // 1. 生成题目的提示词
    public static String buildExercisePrompt(String type, String difficulty) {
        return "请你扮演一个编程面试官。请生成一道关于 " + type + " 的编程练习题，难度为 " + difficulty + "。" +
                "请直接给出题目描述和要求，不要提供答案，不要有多余的废话。";
    }

    // 2. 批改题目的提示词
    public static String buildCheckExercisePrompt(String question, String userAnswer) {
        return "我是一名学生。题目是：\n" + question + "\n\n" +
                "我的回答是：\n" + userAnswer + "\n\n" +
                "请判断我的回答是否正确，并给出详细的解析和正确的参考代码。";
    }

}