package com.ptu.devCloud.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ptu.devCloud.annotation.SeqName;
import com.ptu.devCloud.constants.TableSequenceConstants;
import com.ptu.devCloud.entity.TaskRemark;

/**
 * TaskRemarkMapper
 * @author yang fan
 * @since 2024-01-25 09:27:01
 */
@Mapper
public interface TaskRemarkMapper extends BaseMapper<TaskRemark> {

	/**
     * 根据主键查询
     * @author yang fan
     * @since 2024-01-25 09:27:01
     * @param id 主键
     * @return 返回记录，没有返回null
     */
	TaskRemark getById(Long id);
	
	/**
     * 新增，插入所有字段
     * @author yang fan
     * @since 2024-01-25 09:27:01
     * @param taskRemark 新增的记录
     * @return 返回影响行数
     */
    @SeqName(value = TableSequenceConstants.TaskRemark)
	int insert(TaskRemark taskRemark);
	
	/**
     * 新增，忽略null字段
     * @author yang fan
     * @since 2024-01-25 09:27:01
     * @param taskRemark 新增的记录
     * @return 返回影响行数
     */
    @SeqName(value = TableSequenceConstants.TaskRemark)
	int insertIgnoreNull(TaskRemark taskRemark);
	
	/**
     * 修改，修改所有字段
     * @author yang fan
     * @since 2024-01-25 09:27:01
     * @param taskRemark 修改的记录
     * @return 返回影响行数
     */
	int update(TaskRemark taskRemark);
	
	/**
     * 修改，忽略null字段
     * @author yang fan
     * @since 2024-01-25 09:27:01
     * @param taskRemark 修改的记录
     * @return 返回影响行数
     */
	int updateIgnoreNull(TaskRemark taskRemark);
	
	
}