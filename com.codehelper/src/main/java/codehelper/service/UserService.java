package codehelper.service;

import codehelper.model.dto.UserLoginDTO;
import codehelper.model.dto.UserRegisterDTO;

public interface UserService {
    void register(UserRegisterDTO dto);
    String login(UserLoginDTO dto);
}