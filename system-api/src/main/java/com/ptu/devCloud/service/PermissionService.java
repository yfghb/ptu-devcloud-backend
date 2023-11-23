package com.ptu.devCloud.service;


import com.ptu.devCloud.entity.Permission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


/**
 * PermissionService
 * @author yang fan
 * @since 2023-11-09 10:12:44
 */
public interface PermissionService extends IService<Permission> {

    /**
     * 以父id获取权限树
     * @author Yang Fan
     * @since 2023/11/10 17:18
     * @param parentId 父id(若为0则代表获取所有菜单)
     * @return List<Permission>
     */
    List<Permission> getPermissionsByParentId(Long parentId);

    /**
     * 判断当前用户是否有某个权限
     * @param permissionName 权限名
     * @return true/false
     */
    boolean hasPermission(String permissionName);
	
}