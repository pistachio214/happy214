package com.happy.lucky.system.services.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.happy.lucky.common.utils.BaseUtil;
import com.happy.lucky.system.domain.SysExceptionLog;
import com.happy.lucky.system.dto.RequestExceptionLogListDto;
import com.happy.lucky.system.mapper.SysExceptionLogMapper;
import com.happy.lucky.system.services.ISysExceptionLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 异常日志表 服务实现类
 * </p>
 *
 * @author Roger Peng
 * @since 2022-07-12
 */
@Service
public class SysExceptionLogServiceImpl extends ServiceImpl<SysExceptionLogMapper, SysExceptionLog> implements ISysExceptionLogService {

    @Override
    public IPage<SysExceptionLog> lists(RequestExceptionLogListDto dto) {
        LambdaQueryWrapper<SysExceptionLog> lambdaQueryWrapper = new LambdaQueryWrapper<>();

        lambdaQueryWrapper.like(
                !BaseUtil.isEmpty(dto.getOperUserName()), SysExceptionLog::getOperUserName, dto.getOperUserName())
                .between((!BaseUtil.isEmpty(dto.getStartAt()) && !BaseUtil.isEmpty(dto.getEndAt())),
                        SysExceptionLog::getCreatedAt, dto.getStartAt(), dto.getEndAt())
                .orderByDesc(SysExceptionLog::getCreatedAt);

        return this.page(new Page<>(dto.getCurrent(), dto.getSize()), lambdaQueryWrapper);
    }
}
