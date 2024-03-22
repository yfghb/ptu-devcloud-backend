package com.ptu.devCloud.controller;

import com.ptu.devCloud.entity.CommonResult;
import com.ptu.devCloud.entity.UserTeam;
import com.ptu.devCloud.entity.dto.WorkplaceDTO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
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

    /**
     * 查询当前用户的团队列表和当前团队
     * @author Yang Fan
     * @since 2024/3/22 13:53
     * @return WorkplaceDTO
     */
    @GetMapping("/getTeamList")
    @PreAuthorize("@permissionServiceImpl.hasPermission('ignorePermission')")
    public CommonResult<WorkplaceDTO> getTeamList(){
        return CommonResult.successNoMsg(userTeamService.getTeamList());
    }
}