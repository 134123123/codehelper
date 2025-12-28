package codehelper.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserRoleMapper {

    /**
     * 查询ROLE_ADMIN角色的ID（数据库已初始化该角色）
     */
    @Select("SELECT id FROM role WHERE role_name = 'ROLE_ADMIN'")
    Long getAdminRoleId();

    /**
     * 校验用户是否已拥有管理员角色（避免重复关联）
     */
    @Select("SELECT COUNT(1) FROM user_role WHERE user_id = #{userId} AND role_id = #{roleId}")
    Integer checkAdminRoleExists(Long userId, Long roleId);

    /**
     * 为用户分配管理员角色（插入用户-角色关联记录）
     */
    @Insert("INSERT INTO user_role (user_id, role_id) VALUES (#{userId}, #{roleId})")
    void insertUserAdminRole(Long userId, Long roleId);


    // 新增：查询用户所有角色名称（关键修复）
    @Select("SELECT r.role_name FROM role r " +
            "JOIN user_role ur ON r.id = ur.role_id " +
            "WHERE ur.user_id = #{userId}")
    List<String> selectRolesByUserId(Long userId);

}