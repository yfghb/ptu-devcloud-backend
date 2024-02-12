package com.ptu.devCloud.controller;

import com.ptu.devCloud.entity.CommonResult;
import com.ptu.devCloud.entity.Team;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.ptu.devCloud.service.TeamService;
import javax.annotation.Resource;

/**
 * TeamController
 * @author yang fan
 * @since 2024-02-11 13:19:26
 */
@RestController
@RequestMapping("/TeamController")
public class TeamController {

    @Resource
    private TeamService teamService;

    /**
     * 添加团队
     * @author Yang Fan
     * @since 2024/2/12 12:39
     * @param team Team
     * @return CommonResult<String>
     */
    @PostMapping("/addTeam")
    @PreAuthorize("@permissionServiceImpl.hasPermission('ignorePermission')")
    public CommonResult<String> addTeam(@RequestBody Team team){
        teamService.addTeam(team);
        return CommonResult.success("成功");
    }

    /**
     * 以用户id查询团队信息和成员信息
     * @author Yang Fan
     * @since 2024/2/12 14:59
     * @param userId 用户id
     * @return CommonResult<Team>
     */
    @GetMapping("/getTeam")
    @PreAuthorize("@permissionServiceImpl.hasPermission('ignorePermission')")
    public CommonResult<Team> getTeam(Long userId){
        return CommonResult.successNoMsg(teamService.getTeamByUserId(userId));
    }
    
}