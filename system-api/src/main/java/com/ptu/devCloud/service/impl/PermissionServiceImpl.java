package com.ptu.devCloud.service.impl;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ptu.devCloud.constants.CommonConstants;
import com.ptu.devCloud.entity.Permission;
import com.ptu.devCloud.mapper.PermissionMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ptu.devCloud.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import com.ptu.devCloud.service.PermissionService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
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
    public List<Permission> getPermissionsByParentId(Long parentId, String type) {
        List<Permission> list = permissionMapper.listAll();
        if(list == null || list.isEmpty()) return new ArrayList<>();
        if(StrUtil.isNotEmpty(type)){
            list = list.parallelStream().filter(obj -> type.equals(obj.getPermissionType())).collect(Collectors.toList());
        }
        return builderTree(list, parentId);
    }

    @Override
    public boolean hasPermission(String permissionName) {
        if(CommonConstants.IGNORE_PERMISSION.equals(permissionName))return true;
        List<String> permissions = SecurityUtils.getLoginUser().getPermissions();
        if(permissions == null){
            return false;
        }
        return permissions.contains(permissionName);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(Permission permission) {
        if(permission == null)return;
        if(StrUtil.isNotEmpty(permission.getParent())){
            LambdaQueryWrapper<Permission> lqw = new LambdaQueryWrapper<>();
            lqw.eq(Permission::getName, permission.getParent());
            // 设置父id
            Permission parent = permissionMapper.selectOne(lqw);
            if(parent!=null)permission.setParentId(parent.getId());
        }
        permissionMapper.insertIgnoreNull(permission);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePermissionById(Permission permission) {
        if(permission == null || permission.getId() == null)return;
        permissionMapper.updateIgnoreNull(permission);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deletePermissionById(Long id) {
        if(Objects.nonNull(id)){
            permissionMapper.deleteById(id);
        }
    }

    /** 获取权限树 */
    private List<Permission> builderTree(List<Permission> permissions, Long parentId) {
        return permissions.parallelStream()
                .filter(permission -> permission.getParentId().equals(parentId))
                .peek(permission -> {
                    permission.setChildren(getChildren(permission, permissions));
                    if(permission.getChildren().isEmpty()){
                        permission.setChildren(null);
                    }
                })
                .sorted((Comparator.comparingInt(Permission::getOrderNum)))
                .collect(Collectors.toList());
    }

    /** 获取子权限 */
    private List<Permission> getChildren(Permission permission, List<Permission> permissions) {
        return permissions.parallelStream()
                .filter(p -> p.getParentId().equals(permission.getId()))
                .peek(p -> {
                    p.setChildren(getChildren(p, permissions));
                    if(p.getChildren().isEmpty()){
                        p.setChildren(null);
                    }
                })
                .sorted((Comparator.comparingInt(Permission::getOrderNum)))
                .collect(Collectors.toList());
    }


}