package com.happy.lucky.controller.system;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.happy.lucky.common.utils.R;
import com.happy.lucky.system.domain.SysDictItem;
import com.happy.lucky.system.dto.RequestDictItemCreateDto;
import com.happy.lucky.system.dto.RequestDictItemEditDto;
import com.happy.lucky.system.services.ISysDictItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Api(tags = "系统数据字典项模块")
@RestController
@RequestMapping("/sys-dict-item")
public class SysDictItemController {

    @Autowired
    private ISysDictItemService sysDictItemService;

    @ApiOperation(value = "数据字典项列表", notes = "操作权限 sys:dict:item:list")
    @GetMapping("/list/{id}")
    @SaCheckPermission("sys:dict:item:list")
    public R<IPage<SysDictItem>> list(@PathVariable("id") Long id) {
        return R.success(sysDictItemService.selectByDictId(id));
    }

    @ApiOperation(value = "数据字典项详情", notes = "操作权限 sys:dict:item:list")
    @GetMapping("/{id}")
    @SaCheckPermission("sys:dict:item:list")
    public R<SysDictItem> info(@PathVariable("id") Long id) {
        return R.success(sysDictItemService.getById(id));
    }

    @ApiOperation(value = "新增数据字典项", notes = "操作权限 sys:dict:item:save")
    @PostMapping("/save")
    @SaCheckPermission("sys:dict:item:save")
    public R<SysDictItem> save(@Validated @RequestBody RequestDictItemCreateDto dto) {
        return R.success(sysDictItemService.saveDictItem(dto));
    }

    @ApiOperation(value = "更新数据字典项", notes = "操作权限 sys:dict:item:edit")
    @PutMapping("/edit")
    @SaCheckPermission("sys:dict:item:edit")
    public R<SysDictItem> edit(@Validated @RequestBody RequestDictItemEditDto dto) {
        return R.success(sysDictItemService.editDictItem(dto));
    }

    @ApiOperation(value = "删除数据字典", notes = "操作权限 sys:dict:item:delete")
    @DeleteMapping("/{id}")
    @SaCheckPermission("sys:dict:item:delete")
    public R delete(@PathVariable("id") Long id) {
        return R.success(sysDictItemService.deleteDictItem(id));
    }

}
