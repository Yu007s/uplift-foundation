package com.uplift.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/** 管理员角色关联 */
@Data
@TableName("sys_admin_role")
public class AdminRole {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private Long adminId;

    private Long roleId;
}
