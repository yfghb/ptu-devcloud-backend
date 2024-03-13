package com.ptu.devCloud.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ptu.devCloud.annotation.SeqName;
import com.ptu.devCloud.constants.TableSequenceConstants;
import com.ptu.devCloud.entity.TaskOperationLog;
import org.apache.ibatis.annotations.Param;

/**
 * TaskOperationLogMapper
 * @author yang fan
 * @since 2024-03-13 10:41:58
 */
@Mapper
public interface TaskOperationLogMapper extends BaseMapper<TaskOperationLog> {

	/**
     * 根据主键查询
     * @author yang fan
     * @since 2024-03-13 10:41:58
     * @param id 主键
     * @return 返回记录，没有返回null
     */
	TaskOperationLog getById(Long id);
	
	/**
     * 新增，插入所有字段
     * @author yang fan
     * @since 2024-03-13 10:41:58
     * @param taskOperationLog 新增的记录
     * @return 返回影响行数
     */
    @SeqName(value = TableSequenceConstants.TaskOperationLog)
	int insert(TaskOperationLog taskOperationLog);
	
	/**
     * 新增，忽略null字段
     * @author yang fan
     * @since 2024-03-13 10:41:58
     * @param taskOperationLog 新增的记录
     * @return 返回影响行数
     */
    @SeqName(value = TableSequenceConstants.TaskOperationLog)
	int insertIgnoreNull(TaskOperationLog taskOperationLog);
	
	/**
     * 修改，修改所有字段
     * @author yang fan
     * @since 2024-03-13 10:41:58
     * @param taskOperationLog 修改的记录
     * @return 返回影响行数
     */
	int update(TaskOperationLog taskOperationLog);
	
	/**
     * 修改，忽略null字段
     * @author yang fan
     * @since 2024-03-13 10:41:58
     * @param taskOperationLog 修改的记录
     * @return 返回影响行数
     */
	int updateIgnoreNull(TaskOperationLog taskOperationLog);

	/**
	 * 查询指定任务的操作日志
	 * @author Yang Fan
	 * @since 2024/3/13 11:22
	 * @param taskId 任务id
	 * @return List<TaskOperationLog>
	 */
	List<TaskOperationLog> selectListByTaskId(@Param("taskId") Long taskId);

	/**
	 * 查询指定项目下所有任务的操作日志
	 * @author Yang Fan
	 * @since 2024/3/13 11:22
	 * @param projectId 项目id
	 * @return List<TaskOperationLog>
	 */
	List<TaskOperationLog> selectListByProjectId(@Param("projectId") Long projectId,
												 @Param("startDate") String startDate,
												 @Param("endDate") String endDate);
}