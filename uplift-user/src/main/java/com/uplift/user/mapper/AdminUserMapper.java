package com.uplift.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.uplift.user.entity.AdminUser;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 后台管理员 Mapper
 */
public interface AdminUserMapper extends BaseMapper<AdminUser> {

    @Select("SELECT * FROM sys_admin WHERE app_code = #{appCode} AND username = #{username} AND deleted = 0")
    AdminUser selectByAppCodeAndUsername(@Param("appCode") String appCode, @Param("username") String username);

    @Select("SELECT r.code FROM sys_role r " +
            "INNER JOIN sys_admin_role ar ON r.id = ar.role_id " +
            "WHERE ar.admin_id = #{adminId} AND r.deleted = 0 AND r.status = 1")
    List<String> selectAdminRoles(@Param("adminId") Long adminId);

    @Select("SELECT p.code FROM sys_permission p " +
            "INNER JOIN sys_role_permission rp ON p.id = rp.permission_id " +
            "INNER JOIN sys_admin_role ar ON rp.role_id = ar.role_id " +
            "WHERE ar.admin_id = #{adminId} AND p.app_code = #{appCode} " +
            "AND p.deleted = 0 AND p.status = 1 AND p.code IS NOT NULL")
    List<String> selectAdminPermissions(@Param("adminId") Long adminId, @Param("appCode") String appCode);
}
