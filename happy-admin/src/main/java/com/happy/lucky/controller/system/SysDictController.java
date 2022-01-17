package com.happy.lucky.controller.system;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.happy.lucky.common.utils.R;
import com.happy.lucky.system.dto.RequestDictCreateDto;
import com.happy.lucky.system.dto.RequestDictListDto;
import com.happy.lucky.system.domain.SysDict;
import com.happy.lucky.system.services.ISysDictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "系统数据字典模块")
@RestController
@RequestMapping("/sys-dict")
public class SysDictController {

    @Autowired
    private ISysDictService sysDictService;

    @ApiOperation(value = "数据字典列表", notes = "操作权限 sys:dict:list")
    @GetMapping("/list")
    @PreAuthorize("hasAnyAuthority('sys:user:list')")
    public R<IPage<SysDict>> list(RequestDictListDto dto) {
        return R.success(sysDictService.lists(dto));
    }

    @GetMapping("/save")
    public R<SysDict> save(@Validated @RequestBody RequestDictCreateDto dto) {
        return R.success(sysDictService.saveDict(dto));
    }
}
