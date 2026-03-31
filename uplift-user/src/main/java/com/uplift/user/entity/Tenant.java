package com.uplift.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 租户实体
 */
@Data
@TableName("sys_tenant")
public class Tenant {

    /**
     * 租户ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 租户编码
     */
    private String code;

    /**
     * 租户名称
     */
    private String name;

    /**
     * 联系人
     */
    private String contactName;

    /**
     * 联系电话
     */
    private String contactPhone;

    /**
     * 联系邮箱
     */
    private String contactEmail;

    /**
     * 租户类型 1-企业 2-个人
     */
    private Integer type;

    /**
     * 状态 0-禁用 1-启用
     */
    private Integer status;

    /**
     * 过期时间
     */
    private LocalDate expireTime;

    /**
     * 账号数量限制
     */
    private Integer accountLimit;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 删除标志
     */
    @TableLogic
    private Integer deleted;
}
