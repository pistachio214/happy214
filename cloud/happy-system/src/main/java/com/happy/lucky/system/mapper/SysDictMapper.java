package com.happy.lucky.system.mapper;

import com.happy.lucky.common.vo.SysDictAndItemVo;
import com.happy.lucky.system.domain.SysDict;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 字典表 Mapper 接口
 * </p>
 *
 * @author Roger Peng
 * @since 2022-01-14
 */
public interface SysDictMapper extends BaseMapper<SysDict> {

    SysDictAndItemVo findDictAndItem(@Param("key") String key);

}
