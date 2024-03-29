package com.happy.lucky.system.services.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.happy.lucky.common.utils.BaseUtil;
import com.happy.lucky.common.utils.ConvertUtil;
import com.happy.lucky.common.utils.StringUtil;
import com.happy.lucky.common.vo.SysDictAndItemVo;
import com.happy.lucky.system.domain.SysDict;
import com.happy.lucky.system.dto.RequestDictCreateDto;
import com.happy.lucky.system.dto.RequestDictEditDto;
import com.happy.lucky.system.dto.RequestDictListDto;
import com.happy.lucky.system.mapper.SysDictMapper;
import com.happy.lucky.system.services.ISysDictService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 字典表 服务实现类
 * </p>
 *
 * @author Roger Peng
 * @since 2022-01-14
 */
@Service
public class SysDictServiceImpl extends ServiceImpl<SysDictMapper, SysDict> implements ISysDictService {

    @Override
    public IPage<SysDict> lists(RequestDictListDto dto) {
        LambdaQueryWrapper<SysDict> lambdaQueryWrapper = new LambdaQueryWrapper<>();

        lambdaQueryWrapper.like(!BaseUtil.isEmpty(dto.getType()), SysDict::getType, dto.getType())
                .eq(!BaseUtil.isEmpty(dto.getSystem()), SysDict::getSystem, dto.getSystem())
                .orderByDesc(SysDict::getId);

        return this.page(new Page<>(dto.getCurrent(), dto.getSize()), lambdaQueryWrapper);
    }

    @Override
    public SysDict saveDict(RequestDictCreateDto dto) {
        SysDict sysDict = ConvertUtil.map(dto, SysDict.class);

        if (!this.save(sysDict)) {
            throw new RuntimeException("创建数据字典失败");
        }

        return sysDict;
    }

    @Override
    public SysDict editDict(RequestDictEditDto dto) {
        SysDict sysDict = ConvertUtil.map(dto, SysDict.class);

        if (!this.updateById(sysDict)) {
            throw new RuntimeException("更新数据字典失败");
        }

        return sysDict;
    }

    @Override
    public int deleteDict(Long id) {
        return baseMapper.deleteById(id);
    }

    @Override
    public SysDictAndItemVo findByKey(String key) {
        return baseMapper.findDictAndItem(key);
    }
}
