package com.happy.lucky.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.happy.lucky.system.domain.SysMenu;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 系统菜单表 Mapper 接口
 * </p>
 *
 * @author psy <aileshang0226@163.com>
 * @since 2021-09-30
 */
@Mapper
@Component
public interface SysMenuMapper extends BaseMapper<SysMenu> {

}
