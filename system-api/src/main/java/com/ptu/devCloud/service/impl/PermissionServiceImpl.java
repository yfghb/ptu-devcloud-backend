package com.ptu.devCloud.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.ptu.devCloud.constants.CommonConstants;
import com.ptu.devCloud.entity.Permission;
import com.ptu.devCloud.mapper.PermissionMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ptu.devCloud.utils.SecurityUtils;
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
    public List<Permission> getPermissionsByParentId(Long parentId, String type) {
        List<Permission> list = permissionMapper.listAll();
        if(list == null || list.isEmpty()) return new ArrayList<>();
        if(!StringUtils.checkValNull(type)){
            list = list.parallelStream().filter(obj -> type.equals(obj.getPermissionType())).collect(Collectors.toList());
        }
        return builderTree(list, parentId);
    }

    @Override
    public List<Permission> getMenu(Long parentId) {
        if(parentId == null) return new ArrayList<>();
        List<Permission> list = permissionMapper.selectMenuList(parentId);
        if(list==null || list.isEmpty())return new ArrayList<>();
        return list;
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