package com.uplift.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 权限表（菜单+按钮+接口权限统一管理）
 */
@Data
@TableName("sys_permission")
public class Permission {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /** 应用编码 */
    private String appCode;

    /** 父级ID，顶级为0 */
    private Long parentId;

    /** 权限名称 */
    private String name;

    /** 权限编码，如 user:list、user:add */
    private String code;

    /** 权限类型 1-菜单 2-按钮 3-接口 */
    private Integer permType;

    /** 路由路径（菜单类型有效） */
    private String path;

    /** 前端组件（菜单类型有效） */
    private String component;

    /** 图标 */
    private String icon;

    /** 排序 */
    private Integer sort;

    /** 是否隐藏 0-显示 1-隐藏 */
    private Integer hidden;

    /** 状态 0-禁用 1-启用 */
    private Integer status;

    private Long createBy;

    private Long updateBy;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    @TableLogic
    private Integer deleted;
}
