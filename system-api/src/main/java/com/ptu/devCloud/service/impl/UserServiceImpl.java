package com.ptu.devCloud.service.impl;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ptu.devCloud.annotation.SeqName;
import com.ptu.devCloud.constants.CommonConstants;
import com.ptu.devCloud.constants.TableSequenceConstants;
import com.ptu.devCloud.entity.*;
import com.ptu.devCloud.entity.vo.StatusVO;
import com.ptu.devCloud.entity.vo.UserPageVO;
import com.ptu.devCloud.exception.JobException;
import com.ptu.devCloud.mapper.UserMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ptu.devCloud.service.RoleService;
import com.ptu.devCloud.service.UserRoleService;
import com.ptu.devCloud.service.UserTeamService;
import com.ptu.devCloud.utils.JwtUtil;
import com.ptu.devCloud.utils.RedisUtils;
import com.ptu.devCloud.utils.SecurityUtils;
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
import java.util.*;


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

    @Resource
    private UserRoleService userRoleService;

    @Resource
    private RoleService roleService;

    @Resource
    private UserTeamService userTeamService;

    @Resource
    private RedisUtils redisUtils;


    @Override
    @SeqName(value = TableSequenceConstants.User)
    public boolean saveBatch(Collection<User> entityList) {
        return super.saveBatch(entityList);
    }

    @Override
    public String login(User user) {
        if(user == null || user.getLoginAccount() == null || user.getLoginPassword() == null)return null;
        // 校验账号密码
        UsernamePasswordAuthenticationToken token = new
                UsernamePasswordAuthenticationToken(user.getLoginAccount(),user.getLoginPassword());
        Authentication authentication = authenticationManager.authenticate(token);
        if(Objects.isNull(authentication)){
            throw new JobException("账号或密码不正确");
        }
        SecurityUtils.setAuthentication(authentication);
        // 使用账号加密后的字符串作为redisKey，保证同一个用户只会生成一个登录token
        String userRedisKey = SecurityUtils.aesEncrypt(user.getLoginAccount(), CommonConstants.SECRET_KEY_16);
        String userRedisToken = JwtUtil.generate(authentication.getPrincipal());
        // 在redis设置token，过期时间为两小时（此处配合心跳连接，如果两个小时客户端没有发送alive请求，则自动过期）
        redisUtils.set(userRedisKey, userRedisToken, 60 * 60 * 2);
        return userRedisKey;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addUser(User user) {
        if(user == null || user.getLoginAccount() == null)return false;
        User selectedUser = userMapper.selectByAccount(user.getLoginAccount());
        if(selectedUser == null){
            if(StrUtil.isEmpty(user.getUserName())){
                user.setUserName("用户" + user.getLoginAccount());
            }
            // 加密密码
            String encodePassword = passwordEncoder.encode(user.getLoginPassword());
            user.setLoginPassword(encodePassword);
            user.setStatus("1");
            userMapper.insert(user);
            return true;
        }
        return false;
    }

    @Override
    public PageInfo<User> getPage(UserPageVO pageVO) {
        if(pageVO == null || pageVO.getCurrent() == null || pageVO.getPageSize() == null) {
            return new PageInfo<>();
        }
        PageHelper.startPage(pageVO.getCurrent(), pageVO.getPageSize());
        List<User> userList = userMapper.selectListByQueryParams(pageVO);
        return new PageInfo<>(userList);
    }

    @Override
    public List<String> getRoleNameList(Long userId) {
        List<String> roleList = new ArrayList<>();
        if(userId == null) return roleList;
        List<UserRole> userRoleList = userRoleService.lambdaQuery().eq(UserRole::getUserId, userId).list();
        for(UserRole userRole:userRoleList){
            Role role = roleService.lambdaQuery().eq(Role::getId, userRole.getRoleId()).one();
            if(role != null && StrUtil.isNotEmpty(role.getRoleName())){
                roleList.add(role.getRoleName());
            }
        }
        return roleList;
    }

    @Override
    public void changeStatus(StatusVO statusVO) {
        if(CollUtil.isEmpty(statusVO.getUserIds()) || statusVO.getStatus() == null)return;
        List<Long> list = new ArrayList<>();
        statusVO.getUserIds().forEach(id -> list.add(Long.valueOf(id)));
        userMapper.updateStatusByIdList(list, statusVO.getStatus());
    }

    @Override
    public void alive(String userRedisKey) {
        if(redisUtils.get(userRedisKey)!=null){
            // 将它的过期时间重置成两个小时
            redisUtils.expire(userRedisKey, 60 * 60 * 2);
        }
    }

    @Override
    public void logout(String userRedisKey) {
        redisUtils.del(userRedisKey);
    }

    @Override
    public String getIdempotenceToken() {
        String uuid = UUID.randomUUID().toString(true);
        redisUtils.set(uuid, true, 300);
        return uuid;
    }

    @Override
    public List<String> getUserNameByIds(List<String> userIds) {
        if(CollUtil.isEmpty(userIds))return new ArrayList<>();
        return userMapper.selectUsernameByIds(userIds);
    }

    @Override
    public List<User> getUserListByIds(List<Long> userIds) {
        if(CollUtil.isEmpty(userIds))return new ArrayList<>();
        return userMapper.selectUserListByIds(userIds);
    }

    @Override
    public User getByAccount(String account) {
        if(StrUtil.isEmpty(account))return null;
        return userMapper.selectByAccount(account);
    }


    @Override
    public UserDetails loadUserByUsername(String LoginAccount) throws UsernameNotFoundException {
        // 查询当前账号的用户是否已存在
        User user = userMapper.selectByAccount(LoginAccount);
        if(Objects.isNull(user)){
            return null;
        }
        if("0".equals(user.getStatus())){
            throw new JobException("对不起！当前用户封禁中");
        }
        // 获取权限
        List<String> permissions = userMapper.selectPermissionStrByUserId(user.getId());
        // 补充view的权限
        Map<String,Boolean> map = new HashMap<>(32);
        for(String perm:permissions){
            map.putIfAbsent(perm, true);
            String[] splitPerm = perm.split("-");
            if (splitPerm[splitPerm.length-1].equals("view"))continue;
            StringBuilder builder = new StringBuilder();
            for(int i=0;i<splitPerm.length-1;i++){
                builder.append(splitPerm[i]).append("-");
                map.putIfAbsent(builder + "view", true);
            }
        }
        // 获取团队信息
        List<UserTeam> userTeamList = userTeamService.lambdaQuery().eq(UserTeam::getUserId, user.getId()).list();
        List<Long> userTeamIds = null;
        if(CollUtil.isNotEmpty(userTeamList)){
            Set<Long> teamIds = new HashSet<>();
            userTeamList.forEach(obj -> {
                if(obj.getTeamId() != null)teamIds.add(obj.getTeamId());
            });
            userTeamIds = new ArrayList<>(teamIds);
        }
        LoginUser loginUser = new LoginUser();
        loginUser.setUser(user);
        loginUser.setPermissions(new ArrayList<>(map.keySet()));
        loginUser.setTeamIds(userTeamIds);
        return loginUser;
    }
}