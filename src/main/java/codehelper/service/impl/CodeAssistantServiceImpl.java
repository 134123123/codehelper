
// CodeAssistantServiceImpl.java 实现
package codehelper.service.impl;

        import codehelper.model.vo.ImageAnalysisVO;
        import codehelper.model.vo.CodeResponseVO;
        import codehelper.service.ArkAIService;
        import codehelper.service.CodeAssistantService;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Service;

@Service
public class CodeAssistantServiceImpl implements CodeAssistantService {

    @Autowired
    private ArkAIService arkAIService;

    @Override
    public CodeResponseVO generateCode(String userQuestion, String language) {
        return null;
    }

    @Override
    public void saveCodeRecord(Long userId, String codeContent, String language) {

    }

    @Override
    public ImageAnalysisVO analyzeImageWithQuestion(String imageUrl, String question) {
        // 原图片+文本逻辑（若需保留）
        ImageAnalysisVO vo = new ImageAnalysisVO();
        vo.setAnswer(arkAIService.chatWithImage(imageUrl, question));
        return vo;
    }

    @Override
    public String askWithText(String question) {
        // 调用纯文本交互方法
        return arkAIService.chatWithText(question);
    }
}