package com.happy.lucky.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.happy.lucky.common.dto.SysMenuDto;
import com.happy.lucky.system.domain.SysMenu;

import java.util.List;

/**
 * <p>
 * 系统菜单表 服务类
 * </p>
 *
 * @author psy <aileshang0226@163.com>
 * @since 2021-09-30
 */
public interface ISysMenuService extends IService<SysMenu> {
    List<SysMenuDto> getCurrentUserNav();

    List<SysMenu> tree();
}
