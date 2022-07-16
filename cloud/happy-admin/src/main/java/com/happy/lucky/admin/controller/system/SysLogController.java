package com.happy.lucky.admin.controller.system;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.happy.lucky.common.utils.R;
import com.happy.lucky.system.domain.SysOperLog;
import com.happy.lucky.system.dto.RequestOperListDto;
import com.happy.lucky.system.services.ISysOperLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 日志控制器
 *
 * @author songyangpeng
 */
@Api(tags = "日志模块")
@RestController
@RequestMapping("/sys-log")
public class SysLogController {

    @Autowired
    private ISysOperLogService sysOperLogService;

    @ApiOperation(value = "日志列表", notes = "操作权限 sys:log:list")
    @GetMapping("/list")
    @SaCheckPermission("sys:log:list")
    public R<IPage<SysOperLog>> list (RequestOperListDto dto){
        return R.success(sysOperLogService.lists(dto));
    }

    @ApiOperation(value = "日志详情", notes = "操作权限 sys:log:list")
    @GetMapping("/{id}")
    public R<SysOperLog> info(){}
}
