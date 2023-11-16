package com.ptu.devCloud.controller;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.ptu.devCloud.annotation.EnableMethodLog;
import com.ptu.devCloud.entity.CommonResult;
import com.ptu.devCloud.entity.User;
import com.ptu.devCloud.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;


/**
 * UserController
 * @author yang fan
 * @since 2023-11-09 10:12:44
 */
@RestController
@RequestMapping("/UserController")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 用户登录
     * @author Yang Fan
     * @since 2023/11/9 14:57
     * @param user user实体
     * @return tokenId 令牌id
     */
    @PostMapping("/login")
    @EnableMethodLog(name = "用户登录")
    public CommonResult<String> login(@RequestBody User user){
        String tokenId = userService.login(user);
        if(StringUtils.checkValNull(tokenId)){
            return CommonResult.error("账号或密码不正确");
        }
        return CommonResult.success(tokenId);
    }

    /**
     * 新增单个用户
     * @author Yang Fan
     * @since 2023/11/9 14:57
     * @param user user实体
     * @return CommonResult<Boolean>
     */
    @PostMapping("/add")
    @EnableMethodLog(name = "新增单个用户")
    public CommonResult<Boolean> add(@RequestBody User user){
        return CommonResult.success(userService.addUser(user));
    }
    
}