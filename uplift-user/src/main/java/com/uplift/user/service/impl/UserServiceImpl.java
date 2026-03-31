package com.uplift.user.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.uplift.api.user.dto.LoginDTO;
import com.uplift.api.user.dto.TokenDTO;
import com.uplift.common.constant.UserConstant;
import com.uplift.common.exception.BusinessException;
import com.uplift.common.result.ResultCode;
import com.uplift.user.entity.User;
import com.uplift.user.mapper.UserMapper;
import com.uplift.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

/**
 * C端用户服务实现
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public TokenDTO login(LoginDTO loginDTO) {
        String appCode = loginDTO.getAppCode();
        User user;

        switch (loginDTO.getLoginType()) {
            case UserConstant.LOGIN_TYPE_PASSWORD:
                user = baseMapper.selectByAppCodeAndUsername(appCode, loginDTO.getAccount());
                if (user == null) {
                    user = baseMapper.selectByAppCodeAndPhone(appCode, loginDTO.getAccount());
                }
                if (user == null) {
                    throw new BusinessException(ResultCode.USER_NOT_EXIST);
                }
                if (!passwordEncoder.matches(loginDTO.getCredential(), user.getPassword())) {
                    throw new BusinessException(ResultCode.USER_PASSWORD_ERROR);
                }
                break;
            case UserConstant.LOGIN_TYPE_SMS:
                user = baseMapper.selectByAppCodeAndPhone(appCode, loginDTO.getAccount());
                if (user == null) {
                    throw new BusinessException(ResultCode.USER_NOT_EXIST);
                }
                // TODO: 验证短信验证码
                break;
            default:
                throw new BusinessException(ResultCode.PARAM_IS_INVALID, "不支持的登录类型");
        }

        if (user.getStatus() == UserConstant.USER_STATUS_DISABLED) {
            throw new BusinessException(ResultCode.USER_ACCOUNT_DISABLED);
        }

        List<String> roles = getUserRoles(user.getId());
        List<String> permissions = getUserPermissions(user.getId(), appCode);

        // Sa-Token 使用 "appCode:userId" 作为 loginId，实现不同 App 完全隔离
        String loginId = appCode + ":" + user.getId();
        StpUtil.login(loginId);
        StpUtil.getTokenSession().set("userId", user.getId());
        StpUtil.getTokenSession().set("appCode", appCode);
        StpUtil.getTokenSession().set("userType", UserConstant.USER_TYPE_C);
        StpUtil.getTokenSession().set("username", user.getUsername());
        StpUtil.getTokenSession().set("roles", new HashSet<>(roles));
        StpUtil.getTokenSession().set("permissions", new HashSet<>(permissions));

        user.setLastLoginTime(LocalDateTime.now());
        updateById(user);

        TokenDTO tokenDTO = new TokenDTO();
        tokenDTO.setAccessToken(StpUtil.getTokenValue());
        tokenDTO.setTokenType(UserConstant.TOKEN_TYPE_BEARER);
        tokenDTO.setExpiresIn(StpUtil.getTokenTimeout());
        tokenDTO.setUserId(user.getId());
        tokenDTO.setAppCode(appCode);
        tokenDTO.setUserType(UserConstant.USER_TYPE_C);
        tokenDTO.setUsername(user.getUsername());
        tokenDTO.setNickname(user.getNickname());
        tokenDTO.setAvatar(user.getAvatar());
        tokenDTO.setRoles(new HashSet<>(roles));
        tokenDTO.setPermissions(new HashSet<>(permissions));

        log.info("C端用户登录成功 - appCode: {}, userId: {}", appCode, user.getId());
        return tokenDTO;
    }

    @Override
    public void logout(Long userId, String appCode) {
        String loginId = appCode + ":" + userId;
        StpUtil.logout(loginId);
        log.info("C端用户登出 - appCode: {}, userId: {}", appCode, userId);
    }

    @Override
    public User getByAppCodeAndUsername(String appCode, String username) {
        return baseMapper.selectByAppCodeAndUsername(appCode, username);
    }

    @Override
    public User getByAppCodeAndPhone(String appCode, String phone) {
        return baseMapper.selectByAppCodeAndPhone(appCode, phone);
    }

    @Override
    public List<String> getUserRoles(Long userId) {
        return baseMapper.selectUserRoles(userId);
    }

    @Override
    public List<String> getUserPermissions(Long userId, String appCode) {
        return baseMapper.selectUserPermissions(userId, appCode);
    }
}
