package com.uplift.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.uplift.user.entity.App;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 应用 Mapper
 */
public interface AppMapper extends BaseMapper<App> {

    @Select("SELECT * FROM sys_app WHERE code = #{code} AND deleted = 0")
    App selectByCode(@Param("code") String code);
}
