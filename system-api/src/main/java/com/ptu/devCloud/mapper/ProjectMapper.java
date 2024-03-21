package com.ptu.devCloud.mapper;

import java.util.Date;
import java.util.List;

import com.ptu.devCloud.entity.dto.WorkplaceDTO;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ptu.devCloud.annotation.SeqName;
import com.ptu.devCloud.constants.TableSequenceConstants;
import com.ptu.devCloud.entity.Project;
import org.apache.ibatis.annotations.Param;

/**
 * ProjectMapper
 * @author yang fan
 * @since 2024-02-17 16:24:23
 */
@Mapper
public interface ProjectMapper extends BaseMapper<Project> {

	/**
     * 根据主键查询
     * @author yang fan
     * @since 2024-02-17 16:24:23
     * @param id 主键
     * @return 返回记录，没有返回null
     */
	Project getById(Long id);
	
	/**
     * 新增，插入所有字段
     * @author yang fan
     * @since 2024-02-17 16:24:23
     * @param project 新增的记录
     * @return 返回影响行数
     */
    @SeqName(value = TableSequenceConstants.Project)
	int insert(Project project);
	
	/**
     * 新增，忽略null字段
     * @author yang fan
     * @since 2024-02-17 16:24:23
     * @param project 新增的记录
     * @return 返回影响行数
     */
    @SeqName(value = TableSequenceConstants.Project)
	int insertIgnoreNull(Project project);
	
	/**
     * 修改，修改所有字段
     * @author yang fan
     * @since 2024-02-17 16:24:23
     * @param project 修改的记录
     * @return 返回影响行数
     */
	int update(Project project);
	
	/**
     * 修改，忽略null字段
     * @author yang fan
     * @since 2024-02-17 16:24:23
     * @param project 修改的记录
     * @return 返回影响行数
     */
	int updateIgnoreNull(Project project);

	/**
	 * 查询所属团队的项目列表
	 * @author Yang Fan
	 * @since 2024/2/17 17:18
	 * @param teamId Long
	 * @return List<Project>
	 */
	List<Project> selectProjectListByTeamId(@Param("teamId") Long teamId);

	/**
	 * 查询参与项目的人数
	 * @author Yang Fan
	 * @since 2024/3/11 14:28
	 * @param projectId 项目id
	 * @return Integer 参与项目的人数
	 */
	Integer selectMemberCountByProjectId(@Param("projectId") Long projectId);

	/**
	 * 查询团队名称列表
	 * @author Yang Fan
	 * @since 2024/3/11 14:49
	 * @param projectId 项目id
	 * @return 团队名称列表
	 */
	List<String> selectTeamNameByProjectId(@Param("projectId") Long projectId);

	/**
	 * 查询指定项目id的所有任务状态数量
	 * @author Yang Fan
	 * @since 2024/3/12 14:05
	 * @param projectId 项目id
	 * @return Project
	 */
	Project selectTaskStatusCntByProjectId(@Param("projectId") Long projectId);

	/**
	 * 查询指定项目的项目成员名称列表
	 * @author Yang Fan
	 * @since 2024/3/20 15:01
	 * @param projectId 项目id
	 * @return List<String>
	 */
	List<String> selectMemberNameByProjectId(@Param("projectId") Long projectId);

	/**
	 * 查询指定用户的项目数，团队数，完成过的任务数
	 * @author Yang Fan
	 * @since 2024/3/21 15:45
	 * @param userId
	 * @return
	 */
	WorkplaceDTO selectProjectTeamTaskCnt(@Param("userId") Long userId);
}