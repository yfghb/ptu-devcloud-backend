package com.ptu.devCloud.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ptu.devCloud.entity.Role;
import com.ptu.devCloud.mapper.RoleMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.ptu.devCloud.service.RoleService;

import javax.annotation.Resource;
import java.util.List;


/**
 * RoleServiceImpl
 * @author yang fan
 * @since 2023-11-09 10:12:44
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService{

    @Resource
    private RoleMapper roleMapper;

    @Override
    public List<Role> list(String roleName) {
        LambdaQueryWrapper<Role> lqw = new LambdaQueryWrapper<>();
        lqw.like(StrUtil.isNotEmpty(roleName), Role::getRoleName, roleName);
        return roleMapper.selectList(lqw);
    }
}