package com.ptu.devCloud.service.impl;


import com.ptu.devCloud.annotation.SeqName;
import com.ptu.devCloud.constants.TableSequenceConstants;
import com.ptu.devCloud.entity.RolePermission;
import com.ptu.devCloud.mapper.RolePermissionMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.ptu.devCloud.service.RolePermissionService;

import java.util.Collection;


/**
 * RolePermissionServiceImpl
 * @author yang fan
 * @since 2023-11-09 10:12:44
 */
@Service
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermission> implements RolePermissionService{


    @Override
    @SeqName(value = TableSequenceConstants.RolePermission)
    public boolean saveBatch(Collection<RolePermission> entityList) {
        return super.saveBatch(entityList);
    }
}