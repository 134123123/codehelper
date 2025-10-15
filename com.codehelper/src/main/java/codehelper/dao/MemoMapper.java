package codehelper.dao;

import codehelper.model.entity.Memo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface MemoMapper {
    @Insert("INSERT INTO memo (user_id, title, content, create_time, update_time) " +
            "VALUES (#{userId}, #{title}, #{content}, NOW(), NOW())")
    void insert(Memo memo);

    @Select("SELECT * FROM memo WHERE user_id = #{userId} AND is_deleted = 0")
    List<Memo> selectByUserId(Long userId);

    @Update("UPDATE memo SET title = #{title}, content = #{content}, update_time = NOW() " +
            "WHERE id = #{id} AND user_id = #{userId}")
    void update(Memo memo);

    @Update("UPDATE memo SET is_deleted = 1 WHERE id = #{id} AND user_id = #{userId}")
    void delete(Long id, Long userId);
}
