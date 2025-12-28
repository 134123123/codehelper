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

    /**
     * 根据ID查询正常状态的用户
     * @param userId 用户ID
     * @return 用户实体（仅包含必要字段）
     */
    @Select("SELECT id, status FROM user WHERE id = #{userId} AND status = 1")
    User selectValidUserById(Long userId);
}