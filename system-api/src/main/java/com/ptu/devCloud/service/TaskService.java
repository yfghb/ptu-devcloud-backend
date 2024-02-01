package com.ptu.devCloud.service;

import com.github.pagehelper.PageInfo;
import com.ptu.devCloud.entity.Task;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ptu.devCloud.entity.vo.TaskPageVO;

import java.util.List;


/**
 * TaskService
 * @author yang fan
 * @since 2024-01-25 09:27:01
 */
public interface TaskService extends IService<Task> {

    /**
     * 新增任务(使用分布式锁保证自动生成的任务编号是唯一的)
     * @author Yang Fan
     * @since 2024/1/26 11:34
     * @param task Task
     */
    void addTask(Task task);


    /**
     * 分页查询
     * @author Yang Fan
     * @since 2024/1/27 16:05
     * @param pageVO TaskPageVO
     * @return PageInfo<Task>
     */
    PageInfo<Task> getPage(TaskPageVO pageVO);

    /**
     * 编辑任务
     * @author Yang Fan
     * @since 2024/1/30 13:57
     * @param task Task
     */
    void editById(Task task);

    /**
     * 改变任务状态
     * @author Yang Fan
     * @since 2024/1/30 14:16
     * @param serialNumber 任务编号
     * @param taskStatus 任务状态
     */
    void changeStatus(String serialNumber, String taskStatus);

    /**
     * 关联任务
     * @author Yang Fan
     * @since 2024/2/1 13:43
     * @param taskIds List<String> 任务id列表
     * @param serialNumber String 任务编号
     */
    void linkTask(List<String> taskIds,String serialNumber);

    /**
     * 以任务id列表查询任务列表，忽略任务描述字段
     * @author Yang Fan
     * @since 2024/2/1 14:13
     * @param correlationIds 任务id列表
     * @return List<Task>
     */
    List<Task> getTaskListBySerialNumberList(String correlationIds);

	
}