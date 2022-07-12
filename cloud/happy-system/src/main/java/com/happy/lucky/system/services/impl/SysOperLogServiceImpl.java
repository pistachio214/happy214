package com.happy.lucky.system.services.impl;

import com.happy.lucky.system.domain.SysOperLog;
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

}
