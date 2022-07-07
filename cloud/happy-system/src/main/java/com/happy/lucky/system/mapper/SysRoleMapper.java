package com.happy.lucky.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.happy.lucky.system.domain.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface SysRoleMapper extends BaseMapper<SysRole> {
}
