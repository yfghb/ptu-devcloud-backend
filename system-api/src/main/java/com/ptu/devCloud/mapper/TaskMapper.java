package com.ptu.devCloud.mapper;


import com.ptu.devCloud.entity.dto.WorkplaceDTO;
import com.ptu.devCloud.entity.vo.TaskPageVO;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ptu.devCloud.annotation.SeqName;
import com.ptu.devCloud.constants.TableSequenceConstants;
import com.ptu.devCloud.entity.Task;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
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
	 * 以任务编号修改，忽略null字段
	 * @author Yang Fan
	 * @since 2024/2/1 13:39
	 * @param task Task
	 * @return boolean
	 */
	 boolean updateBySerialNumberIgnoreNull(Task task);

	/**
	 * 以任务id列表查询任务列表，忽略任务描述字段
	 * @author Yang Fan
	 * @since 2024/2/1 14:11
	 * @param ids 任务id列表
	 * @return List<Task>
	 */
	 List<Task> selectByIdsIgnoreRemark(@Param("ids") List<String> ids);

	/**
	 * 添加CorrelationIds到末尾（注意！请先去重，sql不含去重逻辑）
	 * @author Yang Fan
	 * @since 2024/2/1 15:46
	 * @param ids 任务id列表
	 * @param context String (1,2,3...)
	 * @return boolean
	 */
	boolean appendCorrelationIdsById(@Param("ids") List<String> ids, @Param("context") String context);

	/**
	 * 以任务编号查询
	 * @author Yang Fan
	 * @since 2024/2/2 10:20
	 * @param serialNumber 任务编号
	 * @return Task
	 */
	Task selectBySerialNumber(@Param("serialNumber") String serialNumber);

	/**
	 * 以任务id列表查询任务状态列表
	 * @author Yang Fan
	 * @since 2024/2/4 13:20
	 * @param ids 任务id列表
	 * @return List<String> 任务状态列表
	 */
	List<String> selectTaskStatusByIds(@Param("ids") List<String> ids);

	/**
	 * 以任务id列表更新任务状态
	 * @author Yang Fan
	 * @since 2024/2/4 13:43
	 * @param ids 任务id列表
	 * @param status 任务状态
	 * @return boolean
	 */
	boolean updateTaskStatusByIds(@Param("ids") List<String> ids, @Param("status") String status);

	/**
	 * 查询指定用户的待办任务
	 * @author Yang Fan
	 * @since 2024/3/21 18:43
	 * @param userId 用户id
	 * @return WorkplaceDTO
	 */
	WorkplaceDTO selectTaskTypeCnt(@Param("userId") Long userId);

	/**
	 * 查询用户列表中指定项目的不同任务类型未完成的数量
	 * @author Yang Fan
	 * @since 2024/3/25 10:09
	 * @param userIds 用户id列表
	 * @param projectId 项目id
	 * @return List<WorkplaceDTO>
	 */
	List<WorkplaceDTO> selectTaskTypeCntList(@Param("userIds") List<Long> userIds, @Param("projectId") Long projectId);

	/**
	 * 查询任务统计图表的数据
	 * @author Yang Fan
	 * @since 2024/3/21 22:38
	 * @param projectId 项目id
	 * @param startDate 开始时间
	 * @param endDate 结束时间
	 * @param filtered 是否只保留已完成和已关闭的任务
	 * @return List<Task>
	 */
	List<Task> selectTaskChartDataByProjectId(@Param("projectId") Long projectId,
											  @Param("startDate") Date startDate,
											  @Param("endDate") Date endDate,
											  @Param("filtered") Boolean filtered);

	/**
	 * 查询指定项目的上个月和这个月的任务数量
	 * @author Yang Fan
	 * @since 2024/3/22 16:21
	 * @param projectId 项目id
	 * @return WorkplaceDTO
	 */
	WorkplaceDTO selectLastMonthAndThisMonthTaskCnt(@Param("projectId") Long projectId);

}