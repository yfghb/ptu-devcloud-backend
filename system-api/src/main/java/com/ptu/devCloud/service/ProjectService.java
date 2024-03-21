package com.ptu.devCloud.service;

import com.ptu.devCloud.entity.Project;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ptu.devCloud.entity.TaskOperationLog;
import com.ptu.devCloud.entity.dto.WorkplaceDTO;

import java.util.List;
import java.util.Map;


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

	/**
	 * 计算参与项目的人数
	 * @author Yang Fan
	 * @since 2024/3/11 14:29
	 * @param projectId 项目id
	 * @return Integer 参与项目的人数
	 */
	Integer getMemberCount(Long projectId);

	/**
	 * 获取团队名称列表
	 * @author Yang Fan
	 * @since 2024/3/11 14:49
	 * @param projectId 项目id
	 * @return 团队名称列表
	 */
	List<String> getTeamName(Long projectId);

	/**
	 * 获取指定项目id的所有任务状态数量
	 * @author Yang Fan
	 * @since 2024/3/12 14:07
	 * @param projectId 项目id
	 * @return Project
	 */
	Project getTaskStatusCnt(Long projectId);

	/**
	 * 获取任务的操作日志
	 * @author Yang Fan
	 * @since 2024/3/12 16:41
	 * @param projectId 项目id
	 * @param startDate 开始时间
	 * @param endDate 结束时间
	 * @return List<TaskOperationLog>
	 */
	List<TaskOperationLog> getTaskLog(Long projectId, String startDate, String endDate);

	/**
	 * 调gitea的请求查询账户是否存在
	 * @author Yang Fan
	 * @since 2024/3/18 14:40
	 * @param giteaAccount gitea账户
	 * @return true-存在 / false-不存在
	 */
	Map<String, Object> checkGiteaUserIsAvailable(String giteaAccount);

	/**
	 * 以id更新，忽略null字段
	 * @author Yang Fan
	 * @since 2024/3/18 15:03
	 * @param project Project实体
	 */
	Project updateByIdIgnoreNull(Project project);

	/**
	 * 调用gitea API查询仓库列表
	 * @author Yang Fan
	 * @since 2024/3/18 15:42
	 * @param giteaId gitea 用户id
	 * @return List<Object>
	 */
	List<Object> getCodeRepoList(Long giteaId);

	/**
	 * 查询项目成员名称列表
	 * @author Yang Fan
	 * @since 2024/3/20 15:02
	 * @param projectId 项目id
	 * @return List<String>
	 */
	List<String> getMemberNameListById(Long projectId);

	/**
	 * 查询当前登录用户参与的项目数量，团队数量，以及完成过的任务数
	 * @author Yang Fan
	 * @since 2024/3/21 16:17
	 * @return WorkplaceDTO
	 */
	WorkplaceDTO getProjectTeamTaskCnt();
}