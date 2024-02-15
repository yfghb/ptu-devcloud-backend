package com.ptu.devCloud.controller;

import com.ptu.devCloud.entity.CommonResult;
import com.ptu.devCloud.entity.DictItem;
import com.ptu.devCloud.entity.Team;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.ptu.devCloud.service.TeamService;
import javax.annotation.Resource;
import java.util.List;

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

    /**
     * 修改团队
     * @author Yang Fan
     * @since 2024/2/15 14:31
     * @param team Team
     * @return CommonResult<String>
     */
    @PostMapping("/update")
    @PreAuthorize("@permissionServiceImpl.hasPermission('ignorePermission')")
    public CommonResult<String> update(@RequestBody Team team){
        teamService.updateTeam(team);
        return CommonResult.success("成功");
    }

    /**
     * 团队列表下拉框查询
     * @author Yang Fan
     * @since 2024/2/15 15:10
     * @param userId Long 用户id
     * @return CommonResult<List<DictItem>>
     */
    @GetMapping("/getListByUserId")
    @PreAuthorize("@permissionServiceImpl.hasPermission('ignorePermission')")
    public CommonResult<List<DictItem>> getListByUserId(Long userId){
        return CommonResult.successNoMsg(teamService.getTeamList(userId));
    }

    /**
     * 切换团队
     * @author Yang Fan
     * @since 2024/2/15 15:53
     * @param teamId Long 团队id
     * @return CommonResult<String>
     */
    @PostMapping("/changeTeam")
    @PreAuthorize("@permissionServiceImpl.hasPermission('ignorePermission')")
    public CommonResult<String> changeTeam(Long teamId){
        teamService.changeTeam(teamId);
        return CommonResult.success("成功");
    }
    
}