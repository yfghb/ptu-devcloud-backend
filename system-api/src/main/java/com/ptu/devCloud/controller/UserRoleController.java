package com.ptu.devCloud.controller;

import com.ptu.devCloud.annotation.EnableMethodLog;
import com.ptu.devCloud.entity.CommonResult;
import com.ptu.devCloud.entity.vo.IdsVO;
import com.ptu.devCloud.service.UserRoleService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


/**
 * UserRoleController
 * @author yang fan
 * @since 2023-11-09 10:12:44
 */
@RestController
@RequestMapping("/UserRoleController")
public class UserRoleController {

    @Resource
    private UserRoleService userRoleService;

    /**
     * 重新设置用户-角色关系
     * @author Yang Fan
     * @since 2024/1/3 18:34
     * @param idsVO IdsVO
     */
    @PostMapping("/resetUserRole")
    @PreAuthorize("@permissionServiceImpl.hasPermission('system-user-resetRole')")
    @EnableMethodLog(name = "重新设置用户-角色关系")
    public CommonResult<String> saveUserRole(@RequestBody IdsVO idsVO){
        userRoleService.resetUserRoleByIds(idsVO);
        return CommonResult.successWithMsg(null,"保存成功");
    }

    /**
     * 以用户id查询关联的角色id列表
     * @author Yang Fan
     * @since 2024/1/8 19:44
     * @param userId 用户id
     * @return List<String> 角色id列表
     */
    @GetMapping("/getRoleIds")
    @PreAuthorize("@permissionServiceImpl.hasPermission('ignorePermission')")
    public CommonResult<List<String>> getRoleIds(@RequestParam("userId") Long userId){
        return CommonResult.successNoMsg(userRoleService.getRoleIdsByUserId(userId));
    }
    
}