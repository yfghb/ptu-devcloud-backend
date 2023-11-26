package com.ptu.devCloud.controller;


import com.ptu.devCloud.entity.CommonResult;
import com.ptu.devCloud.entity.Permission;
import com.ptu.devCloud.service.PermissionService;
import org.springframework.lang.Nullable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

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
     * 获取权限树
     * @author Yang Fan
     * @since 2023/11/10 17:39
     * @param parentId 父id
     * @return CommonResult<List<Permission>>
     */
    @GetMapping("/getPermissions")
    @PreAuthorize("@permissionServiceImpl.hasPermission('ignorePermission')")
    public CommonResult<List<Permission>> getPermissions(@Nullable Long parentId){
        if(parentId == null){
            parentId = 0L;
        }
        return CommonResult.success(permissionService.getPermissionsByParentId(parentId));
    }

    @GetMapping("/getMenu")
    @PreAuthorize("@permissionServiceImpl.hasPermission('ignorePermission')")
    public CommonResult<List<Permission>> getMenu(@Nullable Long parentId){
        if(parentId == null){
            parentId = 0L;
        }
        return CommonResult.success(permissionService.getMenu(parentId));
    }
    
}