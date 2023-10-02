package com.ptu.devCloud.mapper;

import java.util.List;

import com.ptu.devCloud.annotation.SeqName;
import com.ptu.devCloud.constants.TableSequenceConstants;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ptu.devCloud.entity.MethodLog;

/**
 * MethodLogMapper
 * @author yang fan
 * @since 2023-09-30 16:24:11
 */
@Mapper
public interface MethodLogMapper extends BaseMapper<MethodLog> {

	/**
     * 查询所有记录
     * @author yang fan
     * @since 2023-09-30 16:24:11
     * @return 返回集合，没有返回空List
     */
	List<MethodLog> listAll();


	/**
     * 根据主键查询
     * @author yang fan
     * @since 2023-09-30 16:24:11
     * @param id 主键
     * @return 返回记录，没有返回null
     */
	MethodLog getById(Long id);
	
	/**
     * 新增，插入所有字段
     * @author yang fan
     * @since 2023-09-30 16:24:11
     * @param methodLog 新增的记录
     * @return 返回影响行数
     */
	@SeqName(value = TableSequenceConstants.MethodLog)
	int insert(MethodLog methodLog);
	
	/**
     * 新增，忽略null字段
     * @author yang fan
     * @since 2023-09-30 16:24:11
     * @param methodLog 新增的记录
     * @return 返回影响行数
     */
	@SeqName(value = TableSequenceConstants.MethodLog)
	int insertIgnoreNull(MethodLog methodLog);
	
	/**
     * 修改，修改所有字段
     * @author yang fan
     * @since 2023-09-30 16:24:11
     * @param methodLog 修改的记录
     * @return 返回影响行数
     */
	int update(MethodLog methodLog);
	
	/**
     * 修改，忽略null字段
     * @author yang fan
     * @since 2023-09-30 16:24:11
     * @param methodLog 修改的记录
     * @return 返回影响行数
     */
	int updateIgnoreNull(MethodLog methodLog);
	
	
}