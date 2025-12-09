// 包路径：com.example.codeassistant.model
package com.example.codeassistant.model;

// 代码生成响应模型（对应后端CodeResponseVO）
public class CodeResponseVO {
    private String code;
    private String explanation;
    private String status;

    // Getter和Setter
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getExplanation() { return explanation; }
    public void setExplanation(String explanation) { this.explanation = explanation; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}

// 图片分析响应模型（对应后端ImageAnalysisVO）
class ImageAnalysisVO {
    private String answer;

    public String getAnswer() { return answer; }
    public void setAnswer(String answer) { this.answer = answer; }
}