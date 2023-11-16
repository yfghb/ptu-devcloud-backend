package com.ptu.devCloud.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.ptu.devCloud.constants.CommonConstants;
import com.ptu.devCloud.entity.User;
import com.ptu.devCloud.mapper.UserMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ptu.devCloud.utils.JwtUtil;
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
    public String login(User user) {
        if(user == null || user.getLoginAccount() == null || user.getLoginPassword() == null)return null;
        User selectedUser = userMapper.selectByAccount(user.getLoginAccount());
        String saltyPwd = CommonConstants.PASSWORD_SALT + user.getLoginPassword();
        String md5Password = DigestUtils.md5DigestAsHex(saltyPwd.getBytes());
        if(selectedUser != null && Objects.equals(selectedUser.getLoginPassword(), md5Password)){
            // 即使token泄露也拿不到密码
            selectedUser.setLoginPassword("*");
            return JwtUtil.generate(selectedUser);
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addUser(User user) {
        if(user == null)return false;
        User selectedUser = userMapper.selectByAccount(user.getLoginAccount());
        if(selectedUser == null){
            if(StringUtils.checkValNull(user.getUserName())){
                user.setUserName("用户" + user.getLoginAccount());
            }
            // 加盐，并进行二次MD5加密，减小前端MD5加密后的字符串被泄露的安全风险
            String md5Password = DigestUtils.md5DigestAsHex((CommonConstants.PASSWORD_SALT + user.getLoginPassword()).getBytes());
            user.setLoginPassword(md5Password);
            user.setStatus(true);
            userMapper.insert(user);
            return true;
        }
        return false;
    }
}