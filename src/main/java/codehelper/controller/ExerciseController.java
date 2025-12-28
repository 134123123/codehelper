package codehelper.controller;

import codehelper.service.ArkAIService;
import codehelper.util.PromptBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/exercise")
public class ExerciseController {

    @Autowired
    private ArkAIService arkAIService;

    /**
     * AI 出题接口
     */
    @PostMapping("/generate")
    public String generate(@RequestBody Map<String, String> params) {
        String type = params.getOrDefault("type", "Java基础");
        String difficulty = params.getOrDefault("difficulty", "初级");
        // 构造出题 Prompt
        String prompt = "请你扮演一位严厉的面试官。请出一道关于 [" + type + "] 的编程面试题，难度为 [" + difficulty + "]。" +
                "要求：1. 题目描述清晰 2. 不要直接给出答案 3. 格式清晰。";
        return arkAIService.chatWithText(prompt);
    }

    /**
     * AI 判卷接口
     */
    @PostMapping("/check")
    public String check(@RequestBody Map<String, String> params) {
        String question = params.get("question");
        String answer = params.get("answer");
        // 构造判卷 Prompt
        String prompt = "题目是：\n" + question + "\n\n" +
                "学生的回答是：\n" + answer + "\n\n" +
                "请你作为老师进行批改。如果不正确，请指出错误并给出正确代码；如果正确，请给予肯定并补充相关知识点。";
        return arkAIService.chatWithText(prompt);
    }
}