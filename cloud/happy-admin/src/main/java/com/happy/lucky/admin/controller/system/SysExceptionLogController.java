package com.happy.lucky.admin.controller.system;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.happy.lucky.common.utils.R;
import com.happy.lucky.system.domain.SysExceptionLog;
import com.happy.lucky.system.dto.RequestExceptionLogListDto;
import com.happy.lucky.system.services.ISysExceptionLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "异常日志模块")
@RestController
@RequestMapping("/sys-exception-log")
public class SysExceptionLogController {

    @Autowired
    private ISysExceptionLogService sysExceptionLogService;

    @ApiOperation(value = "异常日志列表", notes = "操作权限 sys:exception:log:list")
    @GetMapping("/list")
    @SaCheckPermission("sys:exception:log:list")
    public R<IPage<SysExceptionLog>> list(RequestExceptionLogListDto dto) {
        return R.success(sysExceptionLogService.lists(dto));
    }

    @ApiOperation(value = "异常日志详情", notes = "操作权限 sys:exception:log:list")
    @GetMapping("/{id}")
    @SaCheckPermission("sys:exception:log:info")
    public R<SysExceptionLog> info(@PathVariable("id") Long id) {
        return R.success(sysExceptionLogService.getById(id));
    }
}
