package com.ptu.devCloud.controller;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageInfo;
import com.ptu.devCloud.annotation.EnableMethodLog;
import com.ptu.devCloud.entity.CommonResult;
import com.ptu.devCloud.entity.User;
import com.ptu.devCloud.entity.vo.StatusVO;
import com.ptu.devCloud.entity.vo.UserPageVO;
import com.ptu.devCloud.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


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
     * @return CommonResult<String> 令牌id
     */
    @PostMapping("/login")
    @EnableMethodLog(name = "用户登录")
    public CommonResult<String> login(@RequestBody User user){
        String tokenId = userService.login(user);
        if(StrUtil.isEmpty(tokenId)){
            return CommonResult.error("账号或密码不正确");
        }
        return CommonResult.successNoMsg(tokenId);
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
        return CommonResult.successWithMsg(userService.addUser(user), "添加用户成功");
    }

    /**
     * 查询用户列表分页
     * @author Yang Fan
     * @since 2023/12/27 14:41
     * @param pageVO UserPageVO
     * @return CommonResult<List<User>>
     */
    @PostMapping("/page")
    @PreAuthorize("@permissionServiceImpl.hasPermission('ignorePermission')")
    public CommonResult<PageInfo<User>> page(@RequestBody UserPageVO pageVO){
        return CommonResult.successNoMsg(userService.getList(pageVO));
    }

    /**
     * 以用户id查询角色名称列表
     * @author Yang Fan
     * @since 2024/1/3 19:15
     * @param userId 用户id
     * @return CommonResult<List<String>>
     */
    @GetMapping("/getRoleNameList")
    @PreAuthorize("@permissionServiceImpl.hasPermission('ignorePermission')")
    public CommonResult<List<String>> getRoleNameList(@RequestParam("userId") Long userId){
        return CommonResult.successNoMsg(userService.getRoleNameList(userId));
    }

    /**
     * 改变用户状态
     * @author Yang Fan
     * @since 2024/1/3 19:39
     * @param statusVO StatusVO
     * @return CommonResult<String>
     */
    @PostMapping("/changeStatus")
    @PreAuthorize("@permissionServiceImpl.hasPermission('ignorePermission')")
    public CommonResult<String> changeStatus(@RequestBody StatusVO statusVO) throws InterruptedException {
        userService.changeStatus(statusVO);
        return CommonResult.success(null);
    }
    
}