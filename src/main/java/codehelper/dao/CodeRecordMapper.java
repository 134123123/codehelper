package codehelper.dao;

import codehelper.model.entity.CodeRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Insert;

@Mapper
public interface CodeRecordMapper {
    @Insert("INSERT INTO code_record (user_id, code_content, language, create_time) VALUES (#{userId}, #{codeContent}, #{language}, #{createTime})")
    void insert(CodeRecord record);
}