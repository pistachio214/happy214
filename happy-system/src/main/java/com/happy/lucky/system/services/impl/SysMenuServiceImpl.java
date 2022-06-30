package com.happy.lucky.system.services.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.happy.lucky.common.dto.SysMenuDto;
import com.happy.lucky.common.utils.RedisUtil;
import com.happy.lucky.system.domain.SysMenu;
import com.happy.lucky.system.mapper.SysMenuMapper;
import com.happy.lucky.system.mapper.SysUserMapper;
import com.happy.lucky.system.services.ISysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;

import static java.util.Comparator.comparingLong;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

/**
 * <p>
 * 系统菜单表 服务实现类
 * </p>
 *
 * @author psy <aileshang0226@163.com>
 * @since 2021-09-30
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private ISysMenuService iSysMenuService;

    @Override
    public List<SysMenuDto> getCurrentUserNav(Long id) {

        List<Long> menuIds = sysUserMapper.getNavMenu(id);
        List<SysMenu> menus = generatorMenuList(menuIds);

        // 转树状结构
        List<SysMenu> menuTree = buildTreeMenu(menus);

        // 实体转DTO
        return convert(menuTree);
    }

    @Override
    public List<SysMenu> tree() {
        // 获取所有菜单信息
        QueryWrapper<SysMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("orderNum");

        List<SysMenu> sysMenus = baseMapper.selectList(queryWrapper);

        // 转成树状结构
        return buildTreeMenu(sysMenus);
    }

    /**
     * 递归处理当前权限的上级
     *
     * @param ids 已知权限的id
     * @return List<SysMenu>
     */
    private List<SysMenu> generatorMenuList(List<Long> ids) {
        List<SysMenu> menus = new ArrayList<>();

        ids.forEach(id -> {
            menus.addAll(generatorMenu(id));
        });

        // 根据id去重
        return menus.stream().collect(
                collectingAndThen(
                        toCollection(() -> new TreeSet<>(comparingLong(SysMenu::getId))), ArrayList::new)
        );
    }

    private List<SysMenu> generatorMenu(Long id) {
        List<SysMenu> menuArray = new ArrayList<>();

        SysMenu sysMenu = iSysMenuService.getById(id);
        if (sysMenu != null) {
            if (sysMenu.getParentId() != 0) {
                menuArray.addAll(generatorMenu(sysMenu.getParentId()));
            }

            Integer[] indexArray = new Integer[]{0, 1};
            if (Arrays.asList(indexArray).contains(sysMenu.getType())) {
                menuArray.add(sysMenu);
            }
        }

        return menuArray;
    }

    private List<SysMenuDto> convert(List<SysMenu> menuTree) {
        List<SysMenuDto> menuDtos = new ArrayList<>();

        menuTree.forEach(m -> {
            SysMenuDto dto = new SysMenuDto();

            dto.setId(m.getId());
            dto.setName(m.getPerms());
            dto.setTitle(m.getName());
            dto.setComponent(m.getComponent());
            dto.setPath(m.getPath());
            dto.setIcon(m.getIcon());

            if (m.getChildren().size() > 0) {
                // 子节点调用当前方法进行再次转换
                dto.setChildren(convert(m.getChildren()));
            }

            menuDtos.add(dto);
        });

        return menuDtos;
    }

    private List<SysMenu> buildTreeMenu(List<SysMenu> menus) {
        List<SysMenu> finalMenus = new ArrayList<>();
        // 先各自寻找到各自的孩子
        for (SysMenu menu : menus) {
            for (SysMenu e : menus) {
                if (menu.getId().equals(e.getParentId())) {
                    menu.getChildren().add(e);
                }
            }
            // 提取出父节点
            if (menu.getParentId() == 0L) {
                finalMenus.add(menu);
            }
        }

        return finalMenus;
    }
}
