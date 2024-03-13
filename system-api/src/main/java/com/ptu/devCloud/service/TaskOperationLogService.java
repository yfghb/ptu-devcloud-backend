package com.ptu.devCloud.service;

import com.ptu.devCloud.entity.TaskOperationLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


/**
 * TaskOperationLogService
 * @author yang fan
 * @since 2024-03-13 10:41:58
 */
public interface TaskOperationLogService extends IService<TaskOperationLog> {
    /**
     * 获取指定任务的操作日志
     * @author Yang Fan
     * @since 2024/3/13 11:29
     * @param taskId 任务id
     * @return List<TaskOperationLog>
     */
    List<TaskOperationLog> getTaskOperationLogList(Long taskId);

    /**
     * 获取指定项目id下的所有任务的操作日志
     * @author Yang Fan
     * @since 2024/3/13 11:29
     * @param projectId 项目id
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return List<TaskOperationLog>
     */
    List<TaskOperationLog> getTaskOperationLogListByProjectId(Long projectId, String startDate, String endDate);
}