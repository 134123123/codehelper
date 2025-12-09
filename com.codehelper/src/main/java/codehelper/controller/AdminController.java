package codehelper.controller;

import codehelper.model.dto.AssignAdminDTO;
import codehelper.service.AdminRoleService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 管理员权限管理接口
 */
@RestController
@RequestMapping("/api/system/admin")
public class AdminController {

    @Resource
    private AdminRoleService adminRoleService;

    /**
     * 分配管理员角色接口
     * 权限控制：仅当前管理员可调用（依赖Spring Security）
     */
    @PostMapping("/assign-role")
    @PreAuthorize("hasRole('ROLE_ADMIN')") // 角色校验注解
    public String assignAdminRole(@Validated @RequestBody AssignAdminDTO dto) {
        adminRoleService.assignAdminRole(dto);
        return "管理员权限分配成功";
    }
}