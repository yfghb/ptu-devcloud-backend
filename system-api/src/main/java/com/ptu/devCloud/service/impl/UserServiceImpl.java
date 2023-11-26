package com.ptu.devCloud.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.ptu.devCloud.entity.LoginUser;
import com.ptu.devCloud.entity.User;
import com.ptu.devCloud.mapper.UserMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ptu.devCloud.utils.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.ptu.devCloud.service.UserService;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * UserServiceImpl
 * @author yang fan
 * @since 2023-11-09 10:12:44
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService, UserDetailsService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    public String login(User user) {
        if(user == null || user.getLoginAccount() == null || user.getLoginPassword() == null)return null;
        // 校验账号密码
        UsernamePasswordAuthenticationToken token = new
                UsernamePasswordAuthenticationToken(user.getLoginAccount(),user.getLoginPassword());
        Authentication authentication = authenticationManager.authenticate(token);
        if(Objects.isNull(authentication)){
            return null;
        }
        return JwtUtil.generate(authentication.getPrincipal());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addUser(User user) {
        if(user == null || user.getLoginAccount() == null)return false;
        User selectedUser = userMapper.selectByAccount(user.getLoginAccount());
        if(selectedUser == null){
            if(StringUtils.checkValNull(user.getUserName())){
                user.setUserName("用户" + user.getLoginAccount());
            }
            // 加密密码
            String encodePassword = passwordEncoder.encode(user.getLoginPassword());
            user.setLoginPassword(encodePassword);
            user.setStatus(true);
            userMapper.insert(user);
            return true;
        }
        return false;
    }

    @Override
    public UserDetails loadUserByUsername(String LoginAccount) throws UsernameNotFoundException {
        // 查询当前账号的用户是否已存在
        User user = userMapper.selectByAccount(LoginAccount);
        if(Objects.isNull(user)){
            return null;
        }
        // TODO 以用户id查询权限表达式列表
        List<String> permissions = new ArrayList<>();
        return new LoginUser(user, permissions);
    }
}