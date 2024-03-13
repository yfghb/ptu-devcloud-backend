package com.ptu.devCloud.controller;

import com.ptu.devCloud.entity.CommonResult;
import com.ptu.devCloud.entity.Task;
import com.ptu.devCloud.entity.TaskOperationLog;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import com.ptu.devCloud.service.TaskOperationLogService;
import javax.annotation.Resource;
import java.util.List;

/**
 * TaskOperationLogController
 * @author yang fan
 * @since 2024-03-13 10:41:58
 */
@RestController
@RequestMapping("/TaskOperationLogController")
public class TaskOperationLogController {

    @Resource
    private TaskOperationLogService taskOperationLogService;

    /**
     * 获取指定任务的操作日志
     * @author Yang Fan
     * @since 2024/3/13 11:29
     * @param taskId 任务id
     * @return CommonResult<List<TaskOperationLog>>
     */
    @GetMapping("/getListByTaskId")
    @PreAuthorize("@permissionServiceImpl.hasPermission('ignorePermission')")
    public CommonResult<List<TaskOperationLog>> getListByTaskId(Long taskId){
        return CommonResult.successNoMsg(taskOperationLogService.getTaskOperationLogList(taskId));
    }
}