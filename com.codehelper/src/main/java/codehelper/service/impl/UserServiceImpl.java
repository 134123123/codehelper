package codehelper.service.impl;

import codehelper.dao.UserMapper;
import codehelper.model.dto.UserLoginDTO;
import codehelper.model.dto.UserRegisterDTO;
import codehelper.model.entity.User;
import codehelper.service.UserService;
import codehelper.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public void register(UserRegisterDTO dto) {
        // 检查用户名是否已存在
        if (userMapper.selectByUsername(dto.getUsername()) != null) {
            throw new RuntimeException("用户名已存在");
        }
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword())); // 密码加密
        user.setNickname(dto.getNickname());
        userMapper.insert(user);
    }

    @Override
    public String login(UserLoginDTO dto) {
        User user = userMapper.selectByUsername(dto.getUsername());
        if (user == null || !passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new RuntimeException("用户名或密码错误");
        }
        return jwtUtil.generateToken(user.getId()); // 生成JWT
    }
}