package com.ptu.devCloud.service.impl;

import com.ptu.devCloud.entity.Permission;
import com.ptu.devCloud.mapper.PermissionMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.ptu.devCloud.service.PermissionService;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


/**
 * PermissionServiceImpl
 * @author yang fan
 * @since 2023-11-09 10:12:44
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService{

    @Resource
    private PermissionMapper permissionMapper;

    @Override
    public List<Permission> getPermissionsByParentId(Long parentId) {
        List<Permission> list = permissionMapper.listAll();
        if(list == null || list.isEmpty()) return new ArrayList<>();
        return builderTree(list, parentId);
    }

    private List<Permission> builderTree(List<Permission> permissions, Long parentId) {
        return permissions.parallelStream()
                .filter(permission -> permission.getParentId().equals(parentId))
                .map(permission -> permission.setChildren(getChildren(permission, permissions)))
                .sorted((Comparator.comparingInt(Permission::getOrderNum)))
                .collect(Collectors.toList());
    }
    private List<Permission> getChildren(Permission permission, List<Permission> permissions) {
        return permissions.parallelStream()
                .filter(p -> p.getParentId().equals(permission.getId()))
                .map(p -> p.setChildren(getChildren(p,permissions)))
                .sorted((Comparator.comparingInt(Permission::getOrderNum)))
                .collect(Collectors.toList());
    }
}