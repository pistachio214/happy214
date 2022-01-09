package com.happy.lucky.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.happy.lucky.system.domain.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface SysUserMapper extends BaseMapper<SysUser> {
    List<Long> getNavMenuIds(Long userId);
    
    List<Long> getNavMenu(Long userId);

    List<SysUser> listByMenuId(Long menuId);
}
