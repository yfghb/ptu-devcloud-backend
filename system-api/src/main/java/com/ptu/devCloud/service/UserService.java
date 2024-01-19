package com.ptu.devCloud.service;


import com.github.pagehelper.PageInfo;
import com.ptu.devCloud.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ptu.devCloud.entity.vo.StatusVO;
import com.ptu.devCloud.entity.vo.UserPageVO;

import java.util.List;


/**
 * UserService
 * @author yang fan
 * @since 2023-11-09 10:12:44
 */
public interface UserService extends IService<User> {

    /**
     * 用户登录
     * @author Yang Fan
     * @since 2023/11/9 14:48
     * @param user user实体
     * @return redis中 token的key
     */
    String login(User user);

    /**
     * 新增单个用户
     * @author Yang Fan
     * @since 2023/11/9 15:09
     * @param user user实体
     * @return boolean
     */
    boolean addUser(User user);

    /**
     * 查询用户列表
     * @author Yang Fan
     * @since 2023/12/27 14:38
     * @param pageVO UserPageVO
     * @return List<User>
     */
    PageInfo<User> getPage(UserPageVO pageVO);

    /**
     * 以用户id查询角色名称列表
     * @author Yang Fan
     * @since 2024/1/3 19:15
     * @param userId 用户id
     * @return List<String>
     */
    List<String> getRoleNameList(Long userId);

    /**
     * 改变用户状态
     * @author Yang Fan
     * @since 2024/1/3 19:39
     * @param statusVO StatusVO
     */
    void changeStatus(StatusVO statusVO);

    /**
     * 将当前key值的token续期超时时间
     * @author Yang Fan
     * @since 2024/1/18 16:22
     * @param userRedisKey 用户的token
     */
    void alive(String userRedisKey);

    /**
     * 用户登出
     * @author Yang Fan
     * @since 2024/1/19 11:25
     * @param userRedisKey 用户的token
     */
    void logout(String userRedisKey);
}