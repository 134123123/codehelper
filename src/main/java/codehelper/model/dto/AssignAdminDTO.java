package codehelper.model.dto;

import lombok.Data;
import javax.validation.constraints.NotNull;

/**
 * 分配管理员角色的请求参数DTO
 */
@Data
public class AssignAdminDTO {
    /**
     * 目标用户ID（必须存在且有效）
     */
    @NotNull(message = "用户ID不能为空")
    private Long userId;

    // 手动添加getter方法（解决Lombok可能的生效问题）
    public Long getUserId() {
        return userId;
    }
}