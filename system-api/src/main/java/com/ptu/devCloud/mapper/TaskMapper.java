package com.ptu.devCloud.mapper;


import com.ptu.devCloud.entity.vo.TaskPageVO;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ptu.devCloud.annotation.SeqName;
import com.ptu.devCloud.constants.TableSequenceConstants;
import com.ptu.devCloud.entity.Task;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * TaskMapper
 * @author yang fan
 * @since 2024-01-25 09:27:01
 */
@Mapper
public interface TaskMapper extends BaseMapper<Task> {

	/**
     * 根据主键查询
     * @author yang fan
     * @since 2024-01-25 09:27:01
     * @param id 主键
     * @return 返回记录，没有返回null
     */
	Task getById(Long id);
	
	/**
     * 新增，插入所有字段
     * @author yang fan
     * @since 2024-01-25 09:27:01
     * @param task 新增的记录
     * @return 返回影响行数
     */
    @SeqName(value = TableSequenceConstants.Task)
	int insert(Task task);
	
	/**
     * 新增，忽略null字段
     * @author yang fan
     * @since 2024-01-25 09:27:01
     * @param task 新增的记录
     * @return 返回影响行数
     */
    @SeqName(value = TableSequenceConstants.Task)
	int insertIgnoreNull(Task task);
	
	/**
     * 修改，修改所有字段
     * @author yang fan
     * @since 2024-01-25 09:27:01
     * @param task 修改的记录
     * @return 返回影响行数
     */
	int update(Task task);
	
	/**
     * 修改，忽略null字段
     * @author yang fan
     * @since 2024-01-25 09:27:01
     * @param task 修改的记录
     * @return 返回影响行数
     */
	int updateIgnoreNull(Task task);

	/**
	 * 查询今天最大的任务号（格式：YYYYMMDD1, YYYYMMDD2...）
	 * @author Yang Fan
	 * @since 2024/1/26 10:33
	 * @return 任务号
	 */
	String selectTodayMaxSerialNumber();

	/**
	 * 条件查询
	 * @author Yang Fan
	 * @since 2024/1/27 15:58
	 * @param params TaskPageVO
	 * @return List<Task>
	 */
	List<Task> selectListByQueryParams(@Param("params") TaskPageVO params);

	/**
	 * 以任务编号更新任务状态
	 * @author Yang Fan
	 * @since 2024/1/30 14:11
	 * @param serialNumber 任务编号
	 * @param taskStatus 任务状态
	 * @return boolean
	 */
	 boolean updateTaskStatusBySerialNumber(@Param("serialNumber") String serialNumber,
									   		@Param("taskStatus") String taskStatus);

	/**
	 * 添加操作日志到末尾
	 * @author Yang Fan
	 * @since 2024/1/30 16:16
	 * @param serialNumber 任务编号
	 * @param context 操作日志
	 * @return boolean
	 */
	 boolean updateOperationLogBySerialNumber(@Param("serialNumber") String serialNumber,
											  @Param("context") String context);
}