package com.ptu.devCloud.controller;

import com.github.pagehelper.PageInfo;
import com.ptu.devCloud.entity.CommonResult;
import com.ptu.devCloud.entity.vo.RolePageVO;
import com.ptu.devCloud.entity.vo.RoleVO;
import com.ptu.devCloud.service.RoleService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
     * 查询角色列表(附带角色-权限关系)
     * @author Yang Fan
     * @since 2023/12/18 15:47
     * @return CommonResult<List<Role>>
     */
    @PostMapping("/page")
    @PreAuthorize("@permissionServiceImpl.hasPermission('ignorePermission')")
    public CommonResult<PageInfo<RoleVO>> page(@RequestBody RolePageVO rolePageVO){
        return CommonResult.successNoMsg(roleService.getPage(rolePageVO));
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
        return CommonResult.successWithMsg(null,"添加角色成功！");
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
        return CommonResult.successWithMsg(null,"修改角色成功！");
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
        return CommonResult.successWithMsg(null,"删除成功！");
    }

    /**
     * 查询角色列表(不带角色-权限关系)
     * @author Yang Fan
     * @since 2024/1/3 15:52
     * @return CommonResult<List<RoleVO>>
     */
    @GetMapping("/roleList")
    @PreAuthorize("@permissionServiceImpl.hasPermission('ignorePermission')")
    public CommonResult<List<RoleVO>> roleList(){
        return CommonResult.successNoMsg(roleService.getRoleList());
    }
}