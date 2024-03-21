package com.ptu.devCloud.controller;

import com.github.pagehelper.PageInfo;
import com.ptu.devCloud.entity.CommonResult;
import com.ptu.devCloud.entity.Task;
import com.ptu.devCloud.entity.dto.WorkplaceDTO;
import com.ptu.devCloud.entity.vo.IdsVO;
import com.ptu.devCloud.entity.vo.LinkTaskVO;
import com.ptu.devCloud.entity.vo.TaskCardVO;
import com.ptu.devCloud.entity.vo.TaskPageVO;
import com.ptu.devCloud.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * TaskController
 * @author yang fan
 * @since 2024-01-25 09:27:01
 */
@Slf4j
@RestController
@RequestMapping("/TaskController")
public class TaskController {

    @Resource
    private TaskService taskService;



    /**
     * 新增任务
     * @author Yang Fan
     * @since 2024/1/26 11:38
     * @param task Task
     * @return CommonResult<String> 提示信息
     */
    @PostMapping("/add")
    @PreAuthorize("@permissionServiceImpl.hasPermission('ignorePermission')")
    public CommonResult<String> add(@RequestBody Task task){
        taskService.addTask(task);
        return CommonResult.successWithMsg(null,"新增成功");
    }

    /**
     * 分页查询
     * @author Yang Fan
     * @since 2024/1/27 16:06
     * @param pageVO TaskPageVO
     * @return CommonResult<PageInfo<Task>>
     */
    @PostMapping("/page")
    @PreAuthorize("@permissionServiceImpl.hasPermission('task-view')")
    public CommonResult<PageInfo<Task>> page(@RequestBody TaskPageVO pageVO){
        return CommonResult.successNoMsg(taskService.getPage(pageVO));
    }

    /**
     * 查询任务详细
     * @author Yang Fan
     * @since 2024/1/28 13:10
     * @param serialNumber 任务编号
     * @return CommonResult<Task>
     */
    @GetMapping("/detail")
    @PreAuthorize("@permissionServiceImpl.hasPermission('ignorePermission')")
    public CommonResult<Task> detail(@RequestParam("serialNumber") String serialNumber){
        return CommonResult.successNoMsg(taskService.getDetailBySerialNumber(serialNumber));
    }

    /**
     * 编辑任务
     * @author Yang Fan
     * @since 2024/1/30 13:59
     * @param task Task
     * @return CommonResult<String>
     */
    @PostMapping("/edit")
    @PreAuthorize("@permissionServiceImpl.hasPermission('ignorePermission')")
    public CommonResult<String> edit(@RequestBody Task task){
        taskService.editById(task);
        return CommonResult.successWithMsg(null, "修改成功");
    }

    /**
     * 改变任务状态
     * @author Yang Fan
     * @since 2024/1/30 14:16
     * @param serialNumber 任务编号
     * @param taskStatus 任务状态
     * @return CommonResult<String>
     */
    @PostMapping("/changeStatus")
    @PreAuthorize("@permissionServiceImpl.hasPermission('ignorePermission')")
    public CommonResult<String> start(@RequestParam String serialNumber,@RequestParam String taskStatus){
        taskService.changeStatus(serialNumber, taskStatus);
        return CommonResult.successNoMsg("成功");
    }

    /**
     * 关联任务
     * @author Yang Fan
     * @since 2024/2/1 13:43
     * @param vo LinkTaskVO
     * @return CommonResult<String>
     */
    @PostMapping("/link")
    @PreAuthorize("@permissionServiceImpl.hasPermission('ignorePermission')")
    public CommonResult<String> link(@RequestBody LinkTaskVO vo){
        taskService.linkTask(vo.getTaskIds(),vo.getSerialNumber());
        return CommonResult.successWithMsg(null,"关联成功");
    }

    /**
     * 以任务id列表查询任务列表，忽略任务描述字段
     * @author Yang Fan
     * @since 2024/2/1 14:13
     * @param correlationIds 任务id列表
     * @return CommonResult<List<Task>>
     */
    @GetMapping("/getLinkTaskList")
    @PreAuthorize("@permissionServiceImpl.hasPermission('ignorePermission')")
    public CommonResult<List<Task>> getLinkTaskList(String correlationIds){
        return CommonResult.successNoMsg(taskService.getTaskListBySerialNumberList(correlationIds));
    }

    /**
     * 取消关联
     * @author Yang Fan
     * @since 2024/2/2 14:14
     * @param firId 任务id
     * @param secId 任务id
     * @return CommonResult<String>
     */
    @PostMapping("/unlink")
    @PreAuthorize("@permissionServiceImpl.hasPermission('ignorePermission')")
    public CommonResult<String> unlink(@RequestParam("firId") Long firId,@RequestParam("secId") Long secId){
        taskService.unlink(firId,secId);
        return CommonResult.successWithMsg(null, "取消关联成功");
    }

    /**
     * 批量删除
     * @author Yang Fan
     * @since 2024/2/4 13:28
     * @param vo IdsVO
     * @return CommonResult<String>
     */
    @PostMapping("/deleteBatch")
    @PreAuthorize("@permissionServiceImpl.hasPermission('ignorePermission')")
    public CommonResult<String> deleteBatch(@RequestBody IdsVO vo){
        taskService.deleteBatch(vo);
        return CommonResult.success("成功");
    }

    /**
     * 批量修改任务状态为’已关闭‘
     * @author Yang Fan
     * @since 2024/2/4 13:40
     * @param vo IdsVO
     * @return CommonResult<String>
     */
    @PostMapping("/closeBatch")
    @PreAuthorize("@permissionServiceImpl.hasPermission('ignorePermission')")
    public CommonResult<String> closeBatch(@RequestBody IdsVO vo){
        taskService.closeBatch(vo);
        return CommonResult.success("成功");
    }

    /**
     * 转派任务
     * @author Yang Fan
     * @since 2024/2/4 15:09
     * @param currentUserId 当前任务操作人
     * @param changeToUserId 转派人
     * @param taskId 任务id
     * @return CommonResult<String>
     */
    @PostMapping("/changeCurrentOperator")
    @PreAuthorize("@permissionServiceImpl.hasPermission('ignorePermission')")
    public CommonResult<String> changeCurrentOperator(@RequestParam("currentUserId") Long currentUserId,
                                                      @RequestParam("changeToUserId") Long changeToUserId,
                                                      @RequestParam("taskId") Long taskId){
        taskService.changeCurrentOperator(currentUserId,changeToUserId,taskId);
        return CommonResult.success("成功");
    }

    /**
     * 条件查询 任务看板中的卡片
     * @author Yang Fan
     * @since 2024/3/9 18:58
     * @param params TaskPageVO
     * @return CommonResult<List<TaskCardVO>
     */
    @PostMapping("/getTaskCardList")
    @PreAuthorize("@permissionServiceImpl.hasPermission('ignorePermission')")
    public CommonResult<List<TaskCardVO>> getTaskCardList(@RequestBody TaskPageVO params){
        return CommonResult.successNoMsg(taskService.getTaskCardList(params));
    }

    /**
     * 查询指定用户的待办任务数量
     * @author Yang Fan
     * @since 2024/3/21 18:44
     * @param userId 用户id
     * @return WorkplaceDTO
     */
    @GetMapping("/getTaskTypeCnt")
    @PreAuthorize("@permissionServiceImpl.hasPermission('ignorePermission')")
    public CommonResult<WorkplaceDTO> getTaskTypeCnt(Long userId){
        return CommonResult.successNoMsg(taskService.getTaskTypeCnt(userId));
    }

    /**
     * 获取统计任务的图表的数据
     * @author Yang Fan
     * @since 2024/3/21 21:23
     * @param projectId 项目id
     * @return WorkplaceDTO
     */
    @GetMapping("/getTaskCntChart")
    @PreAuthorize("@permissionServiceImpl.hasPermission('ignorePermission')")
    public CommonResult<WorkplaceDTO> getTaskCntChart(Long projectId){
        return CommonResult.successNoMsg(taskService.getTaskCntChart(projectId));
    }
}