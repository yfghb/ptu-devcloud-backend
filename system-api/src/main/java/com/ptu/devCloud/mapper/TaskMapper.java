package com.ptu.devCloud.mapper;


import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ptu.devCloud.annotation.SeqName;
import com.ptu.devCloud.constants.TableSequenceConstants;
import com.ptu.devCloud.entity.Task;

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
	
	
}