package com.uplift.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.uplift.user.entity.App;

/**
 * 应用服务
 */
public interface AppService extends IService<App> {

    /** 根据 code 查询应用 */
    App getByCode(String code);

    /** 校验应用是否合法 */
    void validateApp(String code);

    /** 获取应用类型 1-C端 2-后台 */
    int getAppType(String code);
}
