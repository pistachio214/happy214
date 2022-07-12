package com.happy.lucky.system.services.impl;

import com.happy.lucky.system.domain.SysExceptionLog;
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

}
