package com.ptu.devCloud.service;


import com.ptu.devCloud.entity.RolePermission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


/**
 * RolePermissionService
 * @author yang fan
 * @since 2023-11-09 10:12:44
 */
public interface RolePermissionService extends IService<RolePermission> {

    /**
     * 批量保存，不忽略null字段
     * @author Yang Fan
     * @since 2023/12/22 22:15
     * @param list List<RolePermission>
     */
    void saveRolePermissionList(List<RolePermission> list);
	
}