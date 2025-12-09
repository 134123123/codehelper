package codehelper.service;

import codehelper.model.dto.UserLoginDTO;
import codehelper.model.dto.UserRegisterDTO;

public interface UserService {
    /**
     * 用户注册
     * @param dto 注册信息DTO
     */
    void register(UserRegisterDTO dto);

    /**
     * 用户登录（返回JWT令牌）
     * @param dto 登录信息DTO
     * @return 包含用户ID和角色的JWT令牌
     */
    String login(UserLoginDTO dto);
}