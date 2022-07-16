package com.happy.lucky.system.services.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.happy.lucky.common.utils.BaseUtil;
import com.happy.lucky.system.domain.SysOperLog;
import com.happy.lucky.system.dto.RequestOperLogListDto;
import com.happy.lucky.system.mapper.SysOperLogMapper;
import com.happy.lucky.system.services.ISysOperLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 日志记录表 服务实现类
 * </p>
 *
 * @author Roger Peng
 * @since 2022-07-12
 */
@Service
public class SysOperLogServiceImpl extends ServiceImpl<SysOperLogMapper, SysOperLog> implements ISysOperLogService {

    @Override
    public IPage<SysOperLog> lists(RequestOperLogListDto dto) {
        LambdaQueryWrapper<SysOperLog> lambdaQueryWrapper = new LambdaQueryWrapper<>();

        lambdaQueryWrapper.like(
                !BaseUtil.isEmpty(dto.getOperUserName()), SysOperLog::getOperUserName, dto.getOperUserName())
                .between((!BaseUtil.isEmpty(dto.getStartAt()) && !BaseUtil.isEmpty(dto.getEndAt())),
                        SysOperLog::getCreatedAt, dto.getStartAt(), dto.getEndAt())
                .orderByDesc(SysOperLog::getCreatedAt);

        return this.page(new Page<>(dto.getCurrent(), dto.getSize()), lambdaQueryWrapper);
    }
}
