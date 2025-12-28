package codehelper.model.vo;

import lombok.Data;

@Data
public class CodeResponseVO {
    private String code;
    private String explanation;
    private String status;

    // 新增：接收3个参数的构造函数
    public CodeResponseVO(String code, String explanation, String status) {
        this.code = code;
        this.explanation = explanation;
        this.status = status;
    }
}