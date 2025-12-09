package codehelper.service.impl;

import codehelper.dao.UserMapper;
import codehelper.dao.UserRoleMapper; // 新增注入
import codehelper.model.dto.UserLoginDTO;
import codehelper.model.dto.UserRegisterDTO;
import codehelper.model.entity.User;
import codehelper.service.UserService;
import codehelper.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired // 新增：注入角色Mapper
    private UserRoleMapper userRoleMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public void register(UserRegisterDTO dto) {
        // 原有逻辑不变...
    }

    @Override
    public String login(UserLoginDTO dto) {
        User user = userMapper.selectByUsername(dto.getUsername());
        if (user == null || !passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new RuntimeException("用户名或密码错误");
        }

        // 新增：查询当前用户的所有角色（含管理员角色）
        List<String> roles = userRoleMapper.selectRolesByUserId(user.getId());

        // 修复：生成包含角色的JWT
        return jwtUtil.generateToken(user.getId(), roles);
    }
}