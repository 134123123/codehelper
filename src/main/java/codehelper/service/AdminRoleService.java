package codehelper.service;

import codehelper.dao.UserMapper;
import codehelper.dao.UserRoleMapper;
import codehelper.model.dto.AssignAdminDTO;
import codehelper.model.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 管理员角色分配服务
 */
@Service
public class AdminRoleService {

    @Resource
    private UserRoleMapper userRoleMapper;

    @Resource
    private UserMapper userMapper;

    /**
     * 为用户分配管理员角色（事务控制：确保操作原子性）
     */
    @Transactional(rollbackFor = Exception.class)
    public void assignAdminRole(AssignAdminDTO dto) {
        Long userId = dto.getUserId();

        // 1. 校验用户是否存在且状态正常
        User user = userMapper.selectValidUserById(userId);
        if (user == null) {
            throw new RuntimeException("用户不存在或账号已禁用");
        }

        // 2. 获取管理员角色ID（数据库已初始化，若不存在则抛异常）
        Long adminRoleId = userRoleMapper.getAdminRoleId();
        if (adminRoleId == null) {
            throw new RuntimeException("系统错误：管理员角色未初始化");
        }

        // 3. 校验用户是否已拥有管理员角色
        Integer exists = userRoleMapper.checkAdminRoleExists(userId, adminRoleId);
        if (exists != null && exists > 0) {
            throw new RuntimeException("该用户已为管理员，无需重复分配");
        }

        // 4. 执行角色分配
        userRoleMapper.insertUserAdminRole(userId, adminRoleId);
    }
}