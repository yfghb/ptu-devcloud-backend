package com.ptu.devCloud.service.impl;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ptu.devCloud.annotation.SeqName;
import com.ptu.devCloud.constants.CommonConstants;
import com.ptu.devCloud.constants.TableSequenceConstants;
import com.ptu.devCloud.entity.Permission;
import com.ptu.devCloud.entity.RolePermission;
import com.ptu.devCloud.mapper.PermissionMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ptu.devCloud.service.RolePermissionService;
import com.ptu.devCloud.utils.SecurityUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import com.ptu.devCloud.service.PermissionService;
import javax.annotation.Resource;
import java.util.*;
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

    @Resource
    private RolePermissionService rolePermissionService;


    @Override
    @SeqName(value = TableSequenceConstants.Permission)
    public boolean save(Permission entity) {
        return super.save(entity);
    }

    @Override
    @SeqName(value = TableSequenceConstants.Permission)
    public boolean saveBatch(Collection<Permission> entityList) {
        return super.saveBatch(entityList);
    }

    @Override
    @Cacheable(cacheNames = "perm",
            condition = "#parentId == null && #type == null && #roleId == null",
            key = "'allMenu'",
            sync = true)
    public List<Permission> getPermissions(Long parentId, String type, Long roleId) {
        List<Permission> list = permissionMapper.listAll();
        if(list == null || list.isEmpty()) return new ArrayList<>();
        if(parentId == null){
            parentId = 0L;
        }
        if(StrUtil.isNotEmpty(type)){
            list = list.stream().filter(obj -> type.equals(obj.getPermissionType())).collect(Collectors.toList());
        }
        if(roleId != null) {
            // 获取当前角色下的权限id列表
            Map<Long, Boolean> map = new HashMap<>(32);
            List<RolePermission> rolePermissionList = rolePermissionService.lambdaQuery()
                    .eq(RolePermission::getRoleId, roleId).list();
            if (CollUtil.isNotEmpty(rolePermissionList)) {
                rolePermissionList.forEach(obj -> map.putIfAbsent(obj.getPermissionId(), true));
            }
            // 保留当前角色的权限，过滤其他权限
            list = list.stream().filter(obj -> map.get(obj.getId())!=null).collect(Collectors.toList());
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
    @CacheEvict(value = "perm", key = "'allMenu'")
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
    @CacheEvict(value = "perm", key = "'allMenu'")
    public void updatePermissionById(Permission permission) {
        if(permission == null || permission.getId() == null)return;
        permissionMapper.updateIgnoreNull(permission);
    }

    @Override
    @CacheEvict(value = "perm", key = "'allMenu'")
    public void deletePermissionById(Long id) {
        if(Objects.nonNull(id)){
            permissionMapper.deleteById(id);
        }
    }

    /** 获取权限树 */
    private List<Permission> builderTree(List<Permission> permissions, Long parentId) {
        return permissions.stream()
                .filter(permission -> permission.getParentId().equals(parentId))
                .peek(permission -> {
                    permission.setChildren(getChildren(permission, permissions));
                    permission.setKey(permission.getId().toString());
                    permission.setValue(permission.getId().toString());
                    if(permission.getChildren().isEmpty()){
                        permission.setChildren(null);
                    }
                })
                .sorted((Comparator.comparingInt(Permission::getOrderNum)))
                .collect(Collectors.toList());
    }

    /** 获取子权限 */
    private List<Permission> getChildren(Permission permission, List<Permission> permissions) {
        return permissions.stream()
                .filter(p -> p.getParentId().equals(permission.getId()))
                .peek(p -> {
                    p.setChildren(getChildren(p, permissions));
                    p.setKey(p.getId().toString());
                    p.setValue(p.getId().toString());
                    if(p.getChildren().isEmpty()){
                        p.setChildren(null);
                    }
                })
                .sorted((Comparator.comparingInt(Permission::getOrderNum)))
                .collect(Collectors.toList());
    }


}