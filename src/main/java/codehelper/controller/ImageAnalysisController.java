package codehelper.controller;

import codehelper.model.vo.ImageAnalysisVO;
import codehelper.service.CodeAssistantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
public class ImageAnalysisController {

    @Autowired
    private CodeAssistantService codeAssistantService;

    @PostMapping("/api/analyze/image")
    public ImageAnalysisVO analyzeImage(@RequestBody Map<String, String> request) {
        String imageUrl = request.get("imageUrl");
        String question = request.get("question");
        return codeAssistantService.analyzeImageWithQuestion(imageUrl, question);
    }
}