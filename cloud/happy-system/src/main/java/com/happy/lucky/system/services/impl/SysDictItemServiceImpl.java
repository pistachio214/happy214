package com.happy.lucky.system.services.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.happy.lucky.common.utils.ConvertUtil;
import com.happy.lucky.system.domain.SysDictItem;
import com.happy.lucky.system.dto.RequestDictItemCreateDto;
import com.happy.lucky.system.dto.RequestDictItemEditDto;
import com.happy.lucky.system.mapper.SysDictItemMapper;
import com.happy.lucky.system.services.ISysDictItemService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 字典项 服务实现类
 * </p>
 *
 * @author Roger Peng
 * @since 2022-01-20
 */
@Service
public class SysDictItemServiceImpl extends ServiceImpl<SysDictItemMapper, SysDictItem> implements ISysDictItemService {

    @Override
    public IPage<SysDictItem> selectByDictId(Long id) {
        LambdaQueryWrapper<SysDictItem> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SysDictItem::getDictId, id).orderByAsc(SysDictItem::getSort);

        return baseMapper.selectPage(new Page<>(), lambdaQueryWrapper);
    }

    @Override
    public SysDictItem saveDictItem(RequestDictItemCreateDto dto) {
        SysDictItem sysDictItem = ConvertUtil.map(dto, SysDictItem.class);

        if (!this.save(sysDictItem)) {
            throw new RuntimeException("创建数据字典项失败");
        }

        return sysDictItem;
    }

    @Override
    public int deleteDictItem(Long id) {
        return baseMapper.deleteById(id);
    }

    @Override
    public SysDictItem editDictItem(RequestDictItemEditDto dto) {
        SysDictItem sysDictItem = ConvertUtil.map(dto, SysDictItem.class);

        System.out.println(sysDictItem.toString());
        if (!this.updateById(sysDictItem)) {
            throw new RuntimeException("更新数据字典项失败");
        }

        return sysDictItem;
    }
}
