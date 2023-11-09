package com.ptu.devCloud.service.impl;

import com.ptu.devCloud.entity.User;
import com.ptu.devCloud.mapper.UserMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.ptu.devCloud.service.UserService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.Objects;


/**
 * UserServiceImpl
 * @author yang fan
 * @since 2023-11-09 10:12:44
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService{

    @Resource
    private UserMapper userMapper;

    @Override
    public User login(User user) {
        if(user == null || user.getLoginAccount() == null || user.getLoginPassword() == null)return null;
        User selectedUser = userMapper.selectByAccount(user.getLoginAccount());
        String md5Password = DigestUtils.md5DigestAsHex(user.getLoginPassword().getBytes());
        if(selectedUser != null && Objects.equals(selectedUser.getLoginPassword(), md5Password))return selectedUser;
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addUser(User user) {
        if(user == null)return false;
        User selectedUser = userMapper.selectByAccount(user.getLoginAccount());
        if(selectedUser == null){
            String md5Password = DigestUtils.md5DigestAsHex(user.getLoginPassword().getBytes());
            user.setLoginPassword(md5Password);
            userMapper.insert(user);
            return true;
        }
        return false;
    }
}