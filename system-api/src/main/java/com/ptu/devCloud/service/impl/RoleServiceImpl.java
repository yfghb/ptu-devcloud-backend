package com.ptu.devCloud.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ptu.devCloud.annotation.SeqName;
import com.ptu.devCloud.constants.TableSequenceConstants;
import com.ptu.devCloud.entity.Permission;
import com.ptu.devCloud.entity.Role;
import com.ptu.devCloud.entity.RolePermission;
import com.ptu.devCloud.entity.vo.RoleVO;
import com.ptu.devCloud.exception.JobException;
import com.ptu.devCloud.mapper.RoleMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ptu.devCloud.service.PermissionService;
import com.ptu.devCloud.service.RolePermissionService;
import com.ptu.devCloud.service.TableSequenceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.ptu.devCloud.service.RoleService;
import org.springframework.transaction.support.TransactionTemplate;


import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;


/**
 * RoleServiceImpl
 * @author yang fan
 * @since 2023-11-09 10:12:44
 */
@Slf4j
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService{

    @Resource
    private RoleMapper roleMapper;
    @Resource
    private TransactionTemplate transaction;
    @Resource
    private RolePermissionService rolePermissionService;
    @Resource
    private TableSequenceService tableSequenceService;
    @Resource
    private PermissionService permissionService;

    @Override
    @SeqName(value = TableSequenceConstants.Role)
    public boolean save(Role entity) {
        return super.save(entity);
    }

    @Override
    @SeqName(value = TableSequenceConstants.Role)
    public boolean saveBatch(Collection<Role> entityList) {
        return super.saveBatch(entityList);
    }

    @Override
    public List<RoleVO> list(String roleName) {
        LambdaQueryWrapper<Role> lqw = new LambdaQueryWrapper<>();
        lqw.like(StrUtil.isNotEmpty(roleName), Role::getRoleName, roleName);
        List<Role> roleList = roleMapper.selectList(lqw);
        List<RoleVO> roleVOList = new ArrayList<>();
        for(Role role:roleList){
            RoleVO roleVO = new RoleVO();
            BeanUtil.copyProperties(role, roleVO);
            List<Long> permissionIdList = rolePermissionService.lambdaQuery()
                    .eq(RolePermission::getRoleId, role.getId()).list()
                    .stream()
                    .map(RolePermission::getPermissionId)
                    .collect(Collectors.toList());
            roleVO.setPermissionIdList(permissionIdList);
            roleVOList.add(roleVO);
        }
        return roleVOList;
    }

    @Override
    public void addRole(RoleVO roleVO) {
        if (roleVO == null) return;
        List<RolePermission> rolePermissionList = new ArrayList<>();
        Role role = new Role();
        // 生成id
        role.setId(tableSequenceService.generateId(TableSequenceConstants.Role));
        // 解析vo得到role和rolePermission列表
        parseRoleVO(roleVO, rolePermissionList, role);
        transaction.execute(action -> {
            try {
                // 新增角色, 忽略null字段
                roleMapper.insertIgnoreNull(role);
                // 批量新增角色-权限关系
                rolePermissionService.saveBatch(rolePermissionList);
                return true;
            }catch (Exception e) {
                action.setRollbackOnly();
                throw new JobException("新增角色失败！问题：" + e.getMessage());
            }
        });
    }

    @Override
    public void editRole(RoleVO roleVO) {
        if (roleVO == null) return;
        List<RolePermission> rolePermissionList = new ArrayList<>();
        Role role = new Role();
        // 解析vo得到role和rolePermission列表
        parseRoleVO(roleVO, rolePermissionList, role);
        LambdaQueryWrapper<RolePermission> rolePermissionLQW = new LambdaQueryWrapper<>();
        rolePermissionLQW.eq(RolePermission::getRoleId, role.getId());
        transaction.execute(action -> {
            try {
                // 删除旧的角色-权限关系
                rolePermissionService.remove(rolePermissionLQW);
                // 更新角色
                roleMapper.updateIgnoreNull(role);
                // 新增角色-权限关系
                rolePermissionService.saveBatch(rolePermissionList);
                return true;
            }catch (Exception e){
                action.setRollbackOnly();
                throw new JobException("编辑角色失败！问题：" + e.getMessage());
            }
        });
    }

    /** 解析 roleVo 得到 RolePermissionList 和 role */
    private void parseRoleVO(RoleVO roleVO, List<RolePermission> list, Role role){
        if (roleVO == null) return;
        if(roleVO.getId() == null) {
            BeanUtil.copyProperties(roleVO, role, "id");
        } else {
            BeanUtil.copyProperties(roleVO, role);
        }
        Set<Long> idSet = new HashSet<>();
        // 收集角色-权限关系列表
        for(Long permissionId: roleVO.getPermissionIdList()){
            idSet.add(permissionId);
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(role.getId());
            rolePermission.setPermissionId(permissionId);
            list.add(rolePermission);
            // 获取权限对象的父id, 并添加
            Permission permission = permissionService.getById(permissionId);
            if(permission.getParentId() != 0 && idSet.add(permission.getParentId())){
                RolePermission obj = new RolePermission();
                obj.setRoleId(role.getId());
                obj.setPermissionId(permissionId);
                list.add(obj);
            }
        }
    }
}