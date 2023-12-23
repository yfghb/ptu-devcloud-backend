package com.ptu.devCloud.controller;

import com.ptu.devCloud.entity.CommonResult;
import com.ptu.devCloud.entity.vo.RoleVO;
import com.ptu.devCloud.service.RoleService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
    public CommonResult<List<RoleVO>> list(String roleName){
        return CommonResult.successNoMsg(roleService.list(roleName));
    }

    /**
     * 添加角色
     * @author Yang Fan
     * @since 2023/12/22 16:57
     * @param roleVO RoleVO
     * @return CommonResult<String>提示信息
     */
    @PostMapping("/add")
    @PreAuthorize("@permissionServiceImpl.hasPermission('ignorePermission')")
    public CommonResult<String> add(@RequestBody RoleVO roleVO){
        roleService.addRole(roleVO);
        return CommonResult.success("添加角色成功！");
    }

    /**
     * 修改角色
     * @author Yang Fan
     * @since 2023/12/22 17:12
     * @param roleVO RoleVO
     * @return CommonResult<String>提示信息
     */
    @PostMapping("/edit")
    @PreAuthorize("@permissionServiceImpl.hasPermission('ignorePermission')")
    public CommonResult<String> edit(@RequestBody RoleVO roleVO){
        roleService.editRole(roleVO);
        return CommonResult.success("修改角色成功！");
    }

    /**
     * 批量删除角色
     * @author Yang Fan
     * @since 2023/12/23 19:39
     * @param idList 角色id列表
     * @return CommonResult<String>提示信息
     */
    @DeleteMapping("/delete")
    @PreAuthorize("@permissionServiceImpl.hasPermission('ignorePermission')")
    public CommonResult<String> delete(@RequestBody List<Long> idList){
        roleService.removeRoleBatch(idList);
        return CommonResult.success("删除成功！");
    }
}