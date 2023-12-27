package com.ptu.devCloud.service;


import com.github.pagehelper.PageInfo;
import com.ptu.devCloud.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
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
     * @return tokenId 令牌id
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
    PageInfo<User> getList(UserPageVO pageVO);
	
}