package com.ptu.devCloud.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ptu.devCloud.annotation.SeqName;
import com.ptu.devCloud.constants.TableSequenceConstants;
import com.ptu.devCloud.entity.UserRole;
import com.ptu.devCloud.entity.vo.IdsVO;
import com.ptu.devCloud.exception.JobException;
import com.ptu.devCloud.mapper.UserRoleMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;
import com.ptu.devCloud.service.UserRoleService;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * UserRoleServiceImpl
 * @author yang fan
 * @since 2023-11-09 10:12:44
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService{

    @Resource
    private UserRoleMapper userRoleMapper;
    @Resource
    private TransactionTemplate transaction;


    @Override
    @SeqName(value = TableSequenceConstants.UserRole)
    public boolean saveBatch(Collection<UserRole> entityList) {
        return super.saveBatch(entityList);
    }

    @Override
    public void resetUserRoleByIds(IdsVO idsVO) {
        if (idsVO == null || CollUtil.isEmpty(idsVO.getUserIds()) || CollUtil.isEmpty(idsVO.getRoleIds())){
            throw new JobException("id不能为空！");
        }
        List<Long> userIds = new ArrayList<>();
        idsVO.getUserIds().forEach(userId -> userIds.add(Long.valueOf(userId)));
        List<Long> roleIds = new ArrayList<>();
        idsVO.getRoleIds().forEach(roleId -> roleIds.add(Long.valueOf(roleId)));
        List<UserRole> newUserRoleList = new ArrayList<>();
        List<Long> oldUserRoleList = new ArrayList<>();
        for(Long userId:userIds){
            LambdaQueryWrapper<UserRole> lqw = new LambdaQueryWrapper<>();
            lqw.eq(UserRole::getUserId, userId);
            // 收集待删除的用户-角色关系列表
            List<UserRole> userRoleList = userRoleMapper.selectList(lqw);
            if (CollUtil.isNotEmpty(userRoleList)) {
                List<Long> idList = new ArrayList<>();
                userRoleList.forEach(obj -> idList.add(obj.getId()));
                oldUserRoleList.addAll(idList);
            }
            for(Long roleId:roleIds){
                UserRole userRole = new UserRole();
                userRole.setUserId(userId);
                userRole.setRoleId(roleId);
                newUserRoleList.add(userRole);
            }
        }
        UserRoleServiceImpl proxy = (UserRoleServiceImpl) AopContext.currentProxy();
        transaction.execute(action -> {
            try {
                proxy.removeByIds(oldUserRoleList);
                proxy.saveBatch(newUserRoleList);
                return true;
            }catch (Exception e){
                action.setRollbackOnly();
                throw new JobException("保存用户-角色关系失败");
            }
        });

    }

    @Override
    public List<String> getRoleIdsByUserId(Long userId) {
        if(userId == null) throw new JobException("用户id不能为空");
        LambdaQueryWrapper<UserRole> lqw = new LambdaQueryWrapper<>();
        lqw.eq(UserRole::getUserId, userId);
        List<UserRole> userRoleList = userRoleMapper.selectList(lqw);
        List<String> roleIds = new ArrayList<>();
        userRoleList.forEach(obj -> roleIds.add(String.valueOf(obj.getRoleId())));
        return roleIds;
    }
}