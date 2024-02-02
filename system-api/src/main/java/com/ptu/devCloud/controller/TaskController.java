package com.ptu.devCloud.controller;

import com.github.pagehelper.PageInfo;
import com.ptu.devCloud.entity.CommonResult;
import com.ptu.devCloud.entity.Task;
import com.ptu.devCloud.entity.vo.LinkTaskVO;
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


}