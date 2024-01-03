package com.ptu.devCloud.controller;

import com.ptu.devCloud.entity.CommonResult;
import com.ptu.devCloud.entity.vo.IdsVO;
import com.ptu.devCloud.service.UserRoleService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


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
     * 去除旧的，保存新的用户-关系
     * @author Yang Fan
     * @since 2024/1/3 18:34
     * @param idsVO IdsVO
     */
    @PostMapping("/resetUserRole")
    @PreAuthorize("@permissionServiceImpl.hasPermission('ignorePermission')")
    public CommonResult<String> saveUserRole(@RequestBody IdsVO idsVO){
        userRoleService.resetUserRoleByIds(idsVO);
        return CommonResult.successWithMsg(null,"保存成功");
    }

    
}