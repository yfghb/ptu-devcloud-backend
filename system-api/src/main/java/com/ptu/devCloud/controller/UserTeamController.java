package com.ptu.devCloud.controller;

import com.ptu.devCloud.entity.CommonResult;
import com.ptu.devCloud.entity.UserTeam;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import com.ptu.devCloud.service.UserTeamService;
import javax.annotation.Resource;
import java.util.Map;

/**
 * UserTeamController
 * @author yang fan
 * @since 2024-02-11 13:19:26
 */
@RestController
@RequestMapping("/UserTeamController")
public class UserTeamController {

    @Resource
    private UserTeamService userTeamService;

    /**
     * 添加用户-团队关系
     * @author Yang Fan
     * @since 2024/2/14 15:52
     * @param userTeam UserTeam
     * @return CommonResult<String>
     */
    @PostMapping("/add")
    @PreAuthorize("@permissionServiceImpl.hasPermission('ignorePermission')")
    public CommonResult<String> callbackAdd(@RequestBody UserTeam userTeam){
        userTeamService.addUserTeam(userTeam);
        return CommonResult.success("成功");
    }
}