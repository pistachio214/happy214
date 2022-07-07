package com.happy.lucky.system.services;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.happy.lucky.system.domain.SysDictItem;
import com.baomidou.mybatisplus.extension.service.IService;
import com.happy.lucky.system.dto.RequestDictItemCreateDto;
import com.happy.lucky.system.dto.RequestDictItemEditDto;

/**
 * <p>
 * 字典项 服务类
 * </p>
 *
 * @author Roger Peng
 * @since 2022-01-20
 */
public interface ISysDictItemService extends IService<SysDictItem> {

    IPage<SysDictItem> selectByDictId(Long id);

    SysDictItem saveDictItem(RequestDictItemCreateDto dto);

    int deleteDictItem(Long id);

    SysDictItem editDictItem(RequestDictItemEditDto dto);
}
