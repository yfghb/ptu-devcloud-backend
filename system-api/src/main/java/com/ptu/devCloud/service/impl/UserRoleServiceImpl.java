package com.ptu.devCloud.service.impl;

import com.ptu.devCloud.annotation.SeqName;
import com.ptu.devCloud.constants.TableSequenceConstants;
import com.ptu.devCloud.entity.UserRole;
import com.ptu.devCloud.mapper.UserRoleMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.ptu.devCloud.service.UserRoleService;

import java.util.Collection;

/**
 * UserRoleServiceImpl
 * @author yang fan
 * @since 2023-11-09 10:12:44
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService{


    @Override
    @SeqName(value = TableSequenceConstants.UserRole)
    public boolean save(UserRole entity) {
        return super.save(entity);
    }

    @Override
    @SeqName(value = TableSequenceConstants.UserRole)
    public boolean saveBatch(Collection<UserRole> entityList) {
        return super.saveBatch(entityList);
    }
}