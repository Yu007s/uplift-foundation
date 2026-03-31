package com.uplift.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.uplift.user.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * C端用户 Mapper
 */
public interface UserMapper extends BaseMapper<User> {

    @Select("SELECT * FROM sys_user WHERE app_code = #{appCode} AND username = #{username} AND deleted = 0")
    User selectByAppCodeAndUsername(@Param("appCode") String appCode, @Param("username") String username);

    @Select("SELECT * FROM sys_user WHERE app_code = #{appCode} AND phone = #{phone} AND deleted = 0")
    User selectByAppCodeAndPhone(@Param("appCode") String appCode, @Param("phone") String phone);

    @Select("SELECT * FROM sys_user WHERE app_code = #{appCode} AND email = #{email} AND deleted = 0")
    User selectByAppCodeAndEmail(@Param("appCode") String appCode, @Param("email") String email);

    @Select("SELECT r.code FROM sys_role r " +
            "INNER JOIN sys_user_role ur ON r.id = ur.role_id " +
            "WHERE ur.user_id = #{userId} AND r.deleted = 0 AND r.status = 1")
    List<String> selectUserRoles(@Param("userId") Long userId);

    @Select("SELECT p.code FROM sys_permission p " +
            "INNER JOIN sys_role_permission rp ON p.id = rp.permission_id " +
            "INNER JOIN sys_user_role ur ON rp.role_id = ur.role_id " +
            "WHERE ur.user_id = #{userId} AND p.app_code = #{appCode} " +
            "AND p.deleted = 0 AND p.status = 1 AND p.code IS NOT NULL")
    List<String> selectUserPermissions(@Param("userId") Long userId, @Param("appCode") String appCode);
}
