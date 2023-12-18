package com.ptu.devCloud.controller;

import com.ptu.devCloud.entity.CommonResult;
import com.ptu.devCloud.entity.Role;
import com.ptu.devCloud.service.RoleService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;


/**
 * RoleController
 * @author yang fan
 * @since 2023-11-09 10:12:44
 */
@RestController
@RequestMapping("/RoleController")
public class RoleController {

    @Resource
    private RoleService roleService;

    /**
     * 查询角色列表
     * @author Yang Fan
     * @since 2023/12/18 15:47
     * @return CommonResult<List<Role>>
     */
    @GetMapping("/list")
    @PreAuthorize("@permissionServiceImpl.hasPermission('ignorePermission')")
    public CommonResult<List<Role>> list(String roleName){
        return CommonResult.successNoMsg(roleService.list(roleName));
    }


    
}