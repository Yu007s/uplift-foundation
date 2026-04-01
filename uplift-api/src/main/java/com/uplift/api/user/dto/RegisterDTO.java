package com.uplift.api.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;

/**
 * 注册请求 DTO
 */
@Data
public class RegisterDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 应用编码，客户端必须传入，如 jb-c / jb-admin / gostep-c / gostep-admin
     */
    @NotBlank(message = "应用编码不能为空")
    private String appCode;

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    private String username;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    private String password;

    /**
     * 确认密码
     */
    @NotBlank(message = "确认密码不能为空")
    private String confirmPassword;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 昵称
     */
    private String nickname;
}
