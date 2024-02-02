package com.ptu.devCloud.controller;

import com.ptu.devCloud.entity.CommonResult;
import com.ptu.devCloud.entity.TaskRemark;
import com.ptu.devCloud.service.TaskRemarkService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import java.util.List;

/**
 * TaskRemarkController
 * @author yang fan
 * @since 2024-01-25 09:27:01
 */
@RestController
@RequestMapping("/TaskRemarkController")
public class TaskRemarkController {

    @Resource
    private TaskRemarkService taskRemarkService;

    /**
     * 新增备注
     * @author Yang Fan
     * @since 2024/2/2 10:38
     * @param taskRemark TaskRemark
     * @return CommonResult<String>
     */
    @PostMapping("/add")
    @PreAuthorize("@permissionServiceImpl.hasPermission('ignorePermission')")
    public CommonResult<String> add(@RequestBody TaskRemark taskRemark){
        taskRemarkService.add(taskRemark);
        return CommonResult.successNoMsg("成功");
    }

    /**
     * 以任务id查询备注列表，以时间降序排序
     * @author Yang Fan
     * @since 2024/2/2 10:45
     * @param taskId 任务id
     * @return CommonResult<List<TaskRemark>>
     */
    @GetMapping("/getListByTaskId")
    @PreAuthorize("@permissionServiceImpl.hasPermission('ignorePermission')")
    public CommonResult<List<TaskRemark>> getListByTaskId(Long taskId){
        return CommonResult.successNoMsg(taskRemarkService.getListByTaskId(taskId));
    }
    
}