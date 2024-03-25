package com.ptu.devCloud.controller;

import com.ptu.devCloud.entity.CommonResult;
import com.ptu.devCloud.entity.Project;
import com.ptu.devCloud.entity.TaskOperationLog;
import com.ptu.devCloud.entity.dto.ChartDataDTO;
import org.springframework.beans.factory.annotation.Value;
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
    @Value("${gitea.host}")
    public String GITEA_HOST;

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

    /**
     * 调gitea的请求查询账户是否存在
     * @author Yang Fan
     * @since 2024/3/18 14:40
     * @param giteaAccount gitea账户
     * @return true-存在 / false-不存在
     */
    @GetMapping("/checkGitea")
    @PreAuthorize("@permissionServiceImpl.hasPermission('ignorePermission')")
    public CommonResult<Boolean> checkGitea(String giteaAccount){
        return CommonResult.successNoMsg(projectService.checkGiteaUserIsAvailable(giteaAccount) != null);
    }

    /**
     * 以id更新，忽略null字段
     * @author Yang Fan
     * @since 2024/3/18 15:03
     * @param project Project实体
     */
    @PostMapping("/update")
    @PreAuthorize("@permissionServiceImpl.hasPermission('ignorePermission')")
    public CommonResult<Project> update(@RequestBody Project project){
        return CommonResult.successNoMsg(projectService.updateByIdIgnoreNull(project));
    }

    /**
     * 调用gitea API查询仓库列表
     * @author Yang Fan
     * @since 2024/3/18 15:42
     * @param giteaId gitea 用户id
     * @return List<Object>
     */
    @GetMapping("/getCodeRepoList")
    @PreAuthorize("@permissionServiceImpl.hasPermission('ignorePermission')")
    public CommonResult<List<Object>> getCodeRepoList(Long giteaId){
        return CommonResult.successNoMsg(projectService.getCodeRepoList(giteaId));
    }

    /**
     * 查询gitea的主机
     * @author Yang Fan
     * @since 2024/3/19 13:44
     * @return CommonResult<String>
     */
    @GetMapping("/getGiteaHost")
    @PreAuthorize("@permissionServiceImpl.hasPermission('ignorePermission')")
    public CommonResult<String> getGiteaHost(){
        return CommonResult.successNoMsg(GITEA_HOST);
    }

    /**
     * 查询项目成员名称列表
     * @author Yang Fan
     * @since 2024/3/20 15:02
     * @param projectId 项目id
     * @return List<String>
     */
    @GetMapping("/getMemberNameList")
    @PreAuthorize("@permissionServiceImpl.hasPermission('ignorePermission')")
    public CommonResult<List<String>> getMemberNameList(Long projectId){
        return CommonResult.successNoMsg(projectService.getMemberNameListById(projectId));
    }

    /**
     * 查询当前登录用户参与的项目数量，团队数量，以及完成过的任务数
     * @author Yang Fan
     * @since 2024/3/21 16:17
     * @return ChartDataDTO
     */
    @GetMapping("/getProjectTeamTaskCnt")
    @PreAuthorize("@permissionServiceImpl.hasPermission('ignorePermission')")
    public CommonResult<ChartDataDTO> getProjectTeamTaskCnt(){
        return CommonResult.successNoMsg(projectService.getProjectTeamTaskCnt());
    }

    /**
     * 查询指定团队下的项目名称列表以及任务数和完成的任务数数
     * @author Yang Fan
     * @since 2024/3/22 13:17
     * @param teamId 团队id
     * @return List<Project>
     */
    @GetMapping("/getProjectListAndTaskCnt")
    @PreAuthorize("@permissionServiceImpl.hasPermission('ignorePermission')")
    public CommonResult<List<Project>> getProjectListAndTaskCnt(Long teamId){
        return CommonResult.successNoMsg(projectService.getProjectListAndTaskCnt(teamId));
    }
}