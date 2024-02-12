package com.ptu.devCloud.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ptu.devCloud.annotation.SeqName;
import com.ptu.devCloud.constants.TableSequenceConstants;
import com.ptu.devCloud.entity.Role;
import com.ptu.devCloud.entity.RolePermission;
import com.ptu.devCloud.entity.vo.RolePageVO;
import com.ptu.devCloud.entity.vo.RoleVO;
import com.ptu.devCloud.exception.JobException;
import com.ptu.devCloud.mapper.RoleMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ptu.devCloud.service.RolePermissionService;
import com.ptu.devCloud.service.TableSequenceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopContext;
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


    @Override
    @SeqName(value = TableSequenceConstants.Role)
    public boolean saveBatch(Collection<Role> entityList) {
        return super.saveBatch(entityList);
    }

    @Override
    public PageInfo<RoleVO> getPage(RolePageVO pageVO) {
        if(pageVO == null || pageVO.getCurrent() == null || pageVO.getPageSize() == null) {
            return new PageInfo<>();
        }
        PageHelper.startPage(pageVO.getCurrent(), pageVO.getPageSize());
        List<RoleVO> roleVOList = roleMapper.selectListByQueryParams(pageVO);
        for(RoleVO vo:roleVOList){
            List<String> permissionIdList = rolePermissionService.lambdaQuery()
                    .eq(RolePermission::getRoleId, vo.getId()).list()
                    .stream()
                    .filter(obj -> obj.getPermissionId() != null)
                    .map(obj -> obj.getPermissionId().toString())
                    .collect(Collectors.toList());
            vo.setPermissionIdList(permissionIdList);
        }
        return new PageInfo<>(roleVOList);
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

    @Override
    public void removeRoleBatch(List<Long> idList) {
        if(CollUtil.isEmpty(idList))return;
        List<Long> rolePermissionList = new ArrayList<>();
        for(Long roleId:idList){
            // 获取当前roleId的角色-权限关系列表
            List<Long> list = rolePermissionService.lambdaQuery()
                    .eq(RolePermission::getRoleId, roleId).list()
                    .stream()
                    .map(RolePermission::getId)
                    .collect(Collectors.toList());
            rolePermissionList.addAll(list);
        }
        RoleServiceImpl proxy = (RoleServiceImpl)AopContext.currentProxy();
        transaction.execute(action -> {
           try {
               // 删除角色列表
               proxy.removeByIds(idList);
               // 删除角色-权限关系列表
               rolePermissionService.removeByIds(rolePermissionList);
               return true;
           }catch (Exception e){
               action.setRollbackOnly();
               throw new JobException("批量删除角色失败！问题：" + e.getMessage());
           }
        });
    }

    @Override
    public List<RoleVO> getRoleList() {
        LambdaQueryWrapper<Role> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Role::getStatus,true);
        List<Role> roleList = roleMapper.selectList(lqw);
        List<RoleVO> roleVOList = new ArrayList<>();
        for(Role role:roleList){
            RoleVO roleVO = new RoleVO();
            BeanUtil.copyProperties(role, roleVO);
            roleVO.setKey(role.getId().toString());
            roleVOList.add(roleVO);
        }
        return roleVOList;
    }

    /** 解析 roleVo 得到 RolePermissionList 和 role */
    private void parseRoleVO(RoleVO roleVO, List<RolePermission> list, Role role){
        if (roleVO == null) return;
        if(roleVO.getId() == null) {
            BeanUtil.copyProperties(roleVO, role, "id");
        } else {
            BeanUtil.copyProperties(roleVO, role);
        }
        // 收集角色-权限关系列表
        for(String permissionId: roleVO.getPermissionIdList()){
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(role.getId());
            if(StrUtil.isNotEmpty(permissionId)) {
                rolePermission.setPermissionId(Long.valueOf(permissionId));
            }
            list.add(rolePermission);
        }
    }
}