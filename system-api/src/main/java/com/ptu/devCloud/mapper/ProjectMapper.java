package com.ptu.devCloud.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ptu.devCloud.annotation.SeqName;
import com.ptu.devCloud.constants.TableSequenceConstants;
import com.ptu.devCloud.entity.Project;

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
	
	
}