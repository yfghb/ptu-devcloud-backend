package com.ptu.devCloud.service;

import com.ptu.devCloud.entity.Task;
import com.baomidou.mybatisplus.extension.service.IService;


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
	
}