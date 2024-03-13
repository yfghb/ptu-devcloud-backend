package com.ptu.devCloud.controller;

import com.ptu.devCloud.entity.CommonResult;
import com.ptu.devCloud.entity.Project;
import com.ptu.devCloud.entity.TaskOperationLog;
import org.springframework.security.access.prepost.PreAuthorize;
import com.ptu.devCloud.service.ProjectService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * ProjectController
 * @author yang fan
 * @since 2024-02-17 16:24:23
 */
@RestController
@RequestMapping("/ProjectController")
public class ProjectController {

    @Resource
    private ProjectService projectService;

    /**
     * 新增项目
     * @author Yang Fan
     * @since 2024/2/17 16:34
     * @author Yang Fan
     * @since 2024/2/17 16:33
     * @param project Project
     */
    @PostMapping("/add")
    @PreAuthorize("@permissionServiceImpl.hasPermission('ignorePermission')")
    public CommonResult<String> add(@RequestBody Project project){
        projectService.add(project);
        return CommonResult.success("成功");
    }

    /**
     * 获取对应团队的项目列表
     * @author Yang Fan
     * @since 2024/2/17 17:19
     * @param teamId 团队id
     * @return CommonResult<List<Project>>
     */
    @GetMapping("/getListByTeamId")
    @PreAuthorize("@permissionServiceImpl.hasPermission('ignorePermission')")
    public CommonResult<List<Project>> getListByTeamId(Long teamId){
        return CommonResult.successNoMsg(projectService.getListByTeamId(teamId));
    }

    /**
     * 计算参与项目的人数
     * @author Yang Fan
     * @since 2024/3/11 14:29
     * @param projectId 项目id
     * @return CommonResult<Integer> 参与项目的人数
     */
    @GetMapping("/getMemberCount")
    @PreAuthorize("@permissionServiceImpl.hasPermission('ignorePermission')")
    public CommonResult<Integer> getMemberCount(Long projectId){
        return CommonResult.successNoMsg(projectService.getMemberCount(projectId));
    }

    /**
     * 获取团队名称列表
     * @author Yang Fan
     * @since 2024/3/11 14:49
     * @param projectId 项目id
     * @return CommonResult<List<String>> 团队名称列表
     */
    @GetMapping("/getTeamName")
    @PreAuthorize("@permissionServiceImpl.hasPermission('ignorePermission')")
    public CommonResult<List<String>> getTeamName(Long projectId){
        return CommonResult.successNoMsg(projectService.getTeamName(projectId));
    }

    /**
     * 获取指定项目id的所有任务状态数量
     * @author Yang Fan
     * @since 2024/3/12 14:07
     * @param projectId 项目id
     * @return CommonResult<Project>
     */
    @GetMapping("/getTaskStatusCnt")
    @PreAuthorize("@permissionServiceImpl.hasPermission('ignorePermission')")
    public CommonResult<Project> getTaskStatusCnt(Long projectId){
        return CommonResult.successNoMsg(projectService.getTaskStatusCnt(projectId));
    }

    /**
     * 获取任务的操作日志
     * @author Yang Fan
     * @since 2024/3/12 16:41
     * @param projectId 项目id
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return CommonResult<List<TaskOperationLog>>
     */
    @PostMapping("/getTaskLog")
    @PreAuthorize("@permissionServiceImpl.hasPermission('ignorePermission')")
    public CommonResult<List<TaskOperationLog>> getTaskLog(Long projectId, String startDate, String endDate){
        return CommonResult.successNoMsg(projectService.getTaskLog(projectId,startDate,endDate));
    }

}