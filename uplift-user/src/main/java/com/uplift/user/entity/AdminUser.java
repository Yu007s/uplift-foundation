package com.uplift.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 后台管理员，与 C端用户完全隔离
 */
@Data
@TableName("sys_admin")
public class AdminUser {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /** 应用编码，关联 sys_app.code（仅 appType=2 的后台应用） */
    private String appCode;

    /** 登录账号 */
    private String username;

    /** 密码（BCrypt） */
    private String password;

    /** 姓名 */
    private String realName;

    /** 手机号 */
    private String phone;

    /** 邮箱 */
    private String email;

    /** 头像 */
    private String avatar;

    /** 部门ID */
    private Long deptId;

    /** 状态 0-禁用 1-启用 */
    private Integer status;

    /** 最后登录IP */
    private String lastLoginIp;

    /** 最后登录时间 */
    private LocalDateTime lastLoginTime;

    private Long createBy;

    private Long updateBy;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    @TableLogic
    private Integer deleted;
}
