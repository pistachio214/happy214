package com.happy.lucky.system.services;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.happy.lucky.system.domain.SysExceptionLog;
import com.baomidou.mybatisplus.extension.service.IService;
import com.happy.lucky.system.dto.RequestExceptionLogListDto;

/**
 * <p>
 * 异常日志表 服务类
 * </p>
 *
 * @author Roger Peng
 * @since 2022-07-12
 */
public interface ISysExceptionLogService extends IService<SysExceptionLog> {

    IPage<SysExceptionLog> lists(RequestExceptionLogListDto dto);
}
