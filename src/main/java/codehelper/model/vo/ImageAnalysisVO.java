package codehelper.model.vo;
/**
 * 图片分析与问答的响应对象
 */
public class ImageAnalysisVO {
    private String analysisResult; // 图片分析内容
    private String answer; // 针对问题的回答

    // 构造方法、getter、setter（根据需要添加）
    public String getAnalysisResult() {
        return analysisResult;
    }

    public void setAnalysisResult(String analysisResult) {
        this.analysisResult = analysisResult;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}