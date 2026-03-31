package com.uplift.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.uplift.common.exception.BusinessException;
import com.uplift.common.result.ResultCode;
import com.uplift.user.entity.App;
import com.uplift.user.mapper.AppMapper;
import com.uplift.user.service.AppService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 应用服务实现
 */
@Service
@RequiredArgsConstructor
public class AppServiceImpl extends ServiceImpl<AppMapper, App> implements AppService {

    @Override
    public App getByCode(String code) {
        return baseMapper.selectByCode(code);
    }

    @Override
    public void validateApp(String code) {
        App app = getByCode(code);
        if (app == null) {
            throw new BusinessException(ResultCode.PARAM_IS_INVALID, "应用编码不存在: " + code);
        }
        if (app.getStatus() == 0) {
            throw new BusinessException(ResultCode.PARAM_IS_INVALID, "应用已禁用: " + code);
        }
    }

    @Override
    public int getAppType(String code) {
        App app = getByCode(code);
        return app != null ? app.getAppType() : 1;
    }
}
