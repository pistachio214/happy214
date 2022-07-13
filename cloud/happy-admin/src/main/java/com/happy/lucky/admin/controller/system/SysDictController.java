package com.happy.lucky.admin.controller.system;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.happy.lucky.common.lang.Const;
import com.happy.lucky.common.utils.R;
import com.happy.lucky.common.vo.SysDictAndItemVo;
import com.happy.lucky.framework.annotation.OperLog;
import com.happy.lucky.system.dto.RequestDictCreateDto;
import com.happy.lucky.system.dto.RequestDictEditDto;
import com.happy.lucky.system.dto.RequestDictListDto;
import com.happy.lucky.system.domain.SysDict;
import com.happy.lucky.system.services.ISysDictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author Pengsy
 */
@Api(tags = "系统数据字典模块")
@RestController
@RequestMapping("/sys-dict")
public class SysDictController {

    @Autowired
    private ISysDictService sysDictService;

    @ApiOperation(value = "数据字典列表", notes = "操作权限 sys:dict:list")
    @OperLog(operModul = "字典模块 - 字典列表", operType = Const.LIST, operDesc = "字典的全部列表")
    @GetMapping("/list")
    @SaCheckPermission("sys:dict:list")
    public R<IPage<SysDict>> list(RequestDictListDto dto) {
        return R.success(sysDictService.lists(dto));
    }

    @ApiOperation(value = "数据字典详情", notes = "操作权限 sys:dict:list")
    @OperLog(operModul = "字典模块 - 字典详情", operType = Const.INFO, operDesc = "字典的详情")
    @GetMapping("/{id}")
    @SaCheckPermission("sys:dict:list")
    public R<SysDict> info(@PathVariable("id") Long id) {
        return R.success(sysDictService.getById(id));
    }

    @ApiOperation(value = "新增数据字典", notes = "操作权限 sys:dict:save")
    @OperLog(operModul = "字典模块 - 添加字典", operType = Const.SAVE, operDesc = "添加字典")
    @PostMapping("/save")
    @SaCheckPermission("sys:dict:save")
    public R<SysDict> save(@Validated @RequestBody RequestDictCreateDto dto) {
        return R.success(sysDictService.saveDict(dto));
    }

    @ApiOperation(value = "更新数据字典", notes = "操作权限 sys:dict:edit")
    @OperLog(operModul = "字典模块 - 更新字典", operType = Const.EDIT, operDesc = "更新字典")
    @PutMapping("/edit")
    @SaCheckPermission("sys:dict:edit")
    public R<SysDict> edit(@Validated @RequestBody RequestDictEditDto dto) {
        return R.success(sysDictService.editDict(dto));
    }

    @ApiOperation(value = "删除数据字典", notes = "操作权限 sys:dict:delete")
    @OperLog(operModul = "字典模块 - 删除字典", operType = Const.DELETE, operDesc = "删除字典")
    @DeleteMapping("/{id}")
    @SaCheckPermission("sys:dict:delete")
    public R<Integer> delete(@PathVariable("id") Long id) {
        return R.success(sysDictService.deleteDict(id));
    }

    @ApiOperation(value = "获取字典数据和字典项数据")
    @OperLog(operModul = "字典模块 - 字典数据和字典项数据", operType = Const.LIST, operDesc = "字典数据和字典项数据")
    @GetMapping("/findByKey/{key}")
    public R<SysDictAndItemVo> findByKey(@PathVariable("key") String key) {
        return R.success(sysDictService.findByKey(key));
    }
}
