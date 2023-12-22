package com.ptu.devCloud.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.ptu.devCloud.entity.RolePermission;
import com.ptu.devCloud.mapper.RolePermissionMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.ptu.devCloud.service.RolePermissionService;

import javax.annotation.Resource;
import java.util.List;


/**
 * RolePermissionServiceImpl
 * @author yang fan
 * @since 2023-11-09 10:12:44
 */
@Service
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermission> implements RolePermissionService{

    @Resource
    private RolePermissionMapper rolePermissionMapper;

    @Override
    public void saveRolePermissionList(List<RolePermission> list) {
        if(CollUtil.isEmpty(list))return;
        for(RolePermission item:list) {
            rolePermissionMapper.insert(item);
        }
    }
}