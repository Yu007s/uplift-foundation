package com.uplift.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 应用表 - 每个子公司的每个系统对应一条记录
 * 示例数据：
 *   code=jb-c      name=AI鉴宝C端
 *   code=jb-admin  name=AI鉴宝后台
 *   code=gostep-c      name=路径记录C端
 *   code=gostep-admin  name=路径记录后台
 */
@Data
@TableName("sys_app")
public class App {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /** 应用唯一编码，登录时由客户端传入 */
    private String code;

    /** 应用名称 */
    private String name;

    /** 所属公司 1-AI鉴宝 2-路径记录 */
    private Integer companyId;

    /** 应用类型 1-C端 2-后台管理 */
    private Integer appType;

    /** 应用密钥（接口鉴权用） */
    private String appSecret;

    /** 状态 0-禁用 1-启用 */
    private Integer status;

    /** 备注 */
    private String remark;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    @TableLogic
    private Integer deleted;
}
