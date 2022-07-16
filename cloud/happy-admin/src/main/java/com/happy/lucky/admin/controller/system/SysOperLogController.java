package com.happy.lucky.admin.controller.system;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.happy.lucky.common.utils.R;
import com.happy.lucky.system.domain.SysOperLog;
import com.happy.lucky.system.dto.RequestOperLogListDto;
import com.happy.lucky.system.services.ISysOperLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 日志控制器
 *
 * @author songyangpeng
 */
@Api(tags = "日志模块")
@RestController
@RequestMapping("/sys-oper-log")
public class SysOperLogController {

    @Autowired
    private ISysOperLogService sysOperLogService;

    @ApiOperation(value = "日志列表", notes = "操作权限 sys:oper:log:list")
    @GetMapping("/list")
    @SaCheckPermission("sys:oper:log:list")
    public R<IPage<SysOperLog>> list(RequestOperLogListDto dto) {
        return R.success(sysOperLogService.lists(dto));
    }

    @ApiOperation(value = "日志详情", notes = "操作权限 sys:oper:log:info")
    @GetMapping("/{id}")
    @SaCheckPermission("sys:oper:log:info")
    public R<SysOperLog> info(@PathVariable("id") Long id) {
        return R.success(sysOperLogService.getById(id));
    }
}
