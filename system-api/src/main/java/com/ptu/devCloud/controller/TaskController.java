package com.ptu.devCloud.controller;

import com.ptu.devCloud.entity.CommonResult;
import com.ptu.devCloud.entity.Task;
import com.ptu.devCloud.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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

}