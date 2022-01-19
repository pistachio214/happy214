package com.happy.lucky.system.services;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.happy.lucky.system.domain.SysDict;
import com.baomidou.mybatisplus.extension.service.IService;
import com.happy.lucky.system.dto.RequestDictCreateDto;
import com.happy.lucky.system.dto.RequestDictEditDto;
import com.happy.lucky.system.dto.RequestDictListDto;

/**
 * <p>
 * 字典表 服务类
 * </p>
 *
 * @author Roger Peng
 * @since 2022-01-14
 */
public interface ISysDictService extends IService<SysDict> {

    IPage<SysDict> lists(RequestDictListDto dto);

    SysDict saveDict(RequestDictCreateDto dto);

    SysDict editDict(RequestDictEditDto dto);

    int deleteDict(Long id);
}
