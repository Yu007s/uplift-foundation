package com.uplift.api.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;

/**
 * 登录请求 DTO
 */
@Data
public class LoginDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 应用编码，客户端必须传入，如 jb-c / jb-admin / lj-c / lj-admin
     */
    @NotBlank(message = "应用编码不能为空")
    private String appCode;

    /**
     * 登录类型：password / sms / email / wechat
     */
    @NotBlank(message = "登录类型不能为空")
    private String loginType;

    /**
     * 用户名 / 手机号 / 邮笱
     */
    @NotBlank(message = "账号不能为空")
    private String account;

    /**
     * 密码 / 验证码
     */
    private String credential;

    /**
     * 验证码 Key
     */
    private String captchaKey;

    /**
     * 验证码
     */
    private String captchaCode;
}
