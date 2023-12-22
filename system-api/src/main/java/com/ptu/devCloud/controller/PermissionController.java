package com.ptu.devCloud.controller;


import com.ptu.devCloud.annotation.EnableMethodLog;
import com.ptu.devCloud.entity.CommonResult;
import com.ptu.devCloud.entity.Permission;
import com.ptu.devCloud.service.PermissionService;
import org.springframework.lang.Nullable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


/**
 * PermissionController
 * @author yang fan
 * @since 2023-11-09 10:12:44
 */
@RestController
@RequestMapping("/PermissionController")
public class PermissionController {

    @Resource
    private PermissionService permissionService;

    /**
     * 获取资源树
     * @author Yang Fan
     * @since 2023/11/10 17:39
     * @param parentId 父id
     * @param type 'M' 菜单, 'B' 按钮
     * @return CommonResult<List<Permission>> 资源树
     */
    @GetMapping("/getPermissions")
    @PreAuthorize("@permissionServiceImpl.hasPermission('ignorePermission')")
    public CommonResult<List<Permission>> getPermissions(@Nullable Long parentId, @Nullable String type, @Nullable Long roleId){
        return CommonResult.successNoMsg(permissionService.getPermissions(parentId, type, roleId));
    }

    /**
     * 新增权限，忽略null字段
     * @author Yang Fan
     * @since 2023/11/29 16:45
     * @param permission Permission实体
     * @return CommonResult<String> 提示信息
     */
    @PostMapping("/add")
    @PreAuthorize("@permissionServiceImpl.hasPermission('ignorePermission')")
    @EnableMethodLog(name = "新增菜单/按钮")
    public CommonResult<String> add(@RequestBody Permission permission){
        permissionService.add(permission);
        return CommonResult.success("操作成功！");
    }

    /**
     * 修改，忽略null字段
     * @author Yang Fan
     * @since 2023/11/29 16:55
     * @param permission Permission实体
     * @return CommonResult<String> 提示信息
     */
    @PostMapping("/updateById")
    @PreAuthorize("@permissionServiceImpl.hasPermission('ignorePermission')")
    @EnableMethodLog(name = "修改菜单/按钮")
    public CommonResult<String> updateById(@RequestBody Permission permission){
        permissionService.updatePermissionById(permission);
        return CommonResult.success("操作成功！");
    }

    /**
     * 以id删除
     * @author Yang Fan
     * @since 2023/11/29 17:03
     * @param id 权限id
     * @return CommonResult<String> 提示信息
     */
    @DeleteMapping("/deleteById")
    @PreAuthorize("@permissionServiceImpl.hasPermission('ignorePermission')")
    @EnableMethodLog(name = "删除菜单/按钮")
    public CommonResult<String> deleteById(@RequestParam Long id){
        permissionService.deletePermissionById(id);
        return CommonResult.success("操作成功！");
    }

}