package com.ptu.devCloud.service;

import com.ptu.devCloud.entity.Project;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


/**
 * ProjectService
 * @author yang fan
 * @since 2024-02-17 16:24:23
 */
public interface ProjectService extends IService<Project> {

	/**
	 * 新增项目
	 * @author Yang Fan
	 * @since 2024/2/17 16:33
	 * @param project Project
	 */
	void add(Project project);

	/**
	 * 获取对应团队的项目列表
	 * @author Yang Fan
	 * @since 2024/2/17 17:19
	 * @param teamId 团队id
	 * @return List<Project>
	 */
	List<Project> getListByTeamId(Long teamId);
}