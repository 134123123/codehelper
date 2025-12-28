package codehelper.dao;

import codehelper.model.entity.Banner;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface BannerMapper {

    @Insert("INSERT INTO banner (title, image_url, link_url, sort_order, is_enabled) " +
            "VALUES (#{title}, #{imageUrl}, #{linkUrl}, #{sortOrder}, #{isEnabled})")
    void insert(Banner banner);

    @Select("SELECT * FROM banner WHERE is_enabled = 1 ORDER BY sort_order ASC")
    List<Banner> selectEnabled();

    @Select("SELECT * FROM banner ORDER BY sort_order ASC")
    List<Banner> selectAll();

    @Select("SELECT * FROM banner WHERE id = #{id}")
    Banner selectById(Long id);

    @Update("UPDATE banner SET title = #{title}, image_url = #{imageUrl}, link_url = #{linkUrl}, " +
            "sort_order = #{sortOrder}, is_enabled = #{isEnabled} WHERE id = #{id}")
    void update(Banner banner);

    @Update("DELETE FROM banner WHERE id = #{id}")
    void deleteById(Long id);
}