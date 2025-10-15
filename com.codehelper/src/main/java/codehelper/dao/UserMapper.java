package codehelper.dao;

import codehelper.model.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Insert;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM user WHERE username = #{username}")
    User selectByUsername(String username);

    @Insert("INSERT INTO user (username, password, nickname, create_time, update_time) " +
            "VALUES (#{username}, #{password}, #{nickname}, NOW(), NOW())")
    void insert(User user);
}