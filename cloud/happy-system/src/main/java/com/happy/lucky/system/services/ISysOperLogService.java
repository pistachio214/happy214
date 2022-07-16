package com.happy.lucky.system.services;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.happy.lucky.system.domain.SysOperLog;
import com.baomidou.mybatisplus.extension.service.IService;
import com.happy.lucky.system.dto.RequestOperListDto;

/**
 * <p>
 * 日志记录表 服务类
 * </p>
 *
 * @author Roger Peng
 * @since 2022-07-12
 */
public interface ISysOperLogService extends IService<SysOperLog> {

    IPage<SysOperLog> lists(RequestOperListDto dto);
}
