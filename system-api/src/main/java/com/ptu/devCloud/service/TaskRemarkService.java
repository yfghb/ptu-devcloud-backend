package com.ptu.devCloud.service;

import com.ptu.devCloud.entity.TaskRemark;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


/**
 * TaskRemarkService
 * @author yang fan
 * @since 2024-01-25 09:27:01
 */
public interface TaskRemarkService extends IService<TaskRemark> {

	/**
	 * 新增，忽略null字段
	 * @author Yang Fan
	 * @since 2024/2/2 10:35
	 * @param taskRemark TaskRemark
	 */
	void add(TaskRemark taskRemark);

	/**
	 * 以任务id查询备注列表，以时间降序排序
	 * @author Yang Fan
	 * @since 2024/2/2 10:45
	 * @param taskId 任务id
	 * @return List<TaskRemark>
	 */
	List<TaskRemark> getListByTaskId(Long taskId);
}