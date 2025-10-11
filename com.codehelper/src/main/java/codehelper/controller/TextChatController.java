package codehelper.controller;

import codehelper.model.vo.ImageAnalysisVO;
import codehelper.service.CodeAssistantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
public class TextChatController {

    @Autowired
    private CodeAssistantService codeAssistantService;

    // 纯文本提问接口
    @PostMapping("/api/ask/text")
    public String askText(@RequestBody Map<String, String> request) {
        String question = request.get("question");
        return codeAssistantService.askWithText(question);
    }
}