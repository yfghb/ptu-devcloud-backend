package com.ptu.devCloud.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ptu.devCloud.annotation.SeqName;
import com.ptu.devCloud.constants.TableSequenceConstants;
import com.ptu.devCloud.entity.ProjectRepository;

/**
 * ProjectRepositoryMapper
 * @author yang fan
 * @since 2024-02-17 16:52:55
 */
@Mapper
public interface ProjectRepositoryMapper extends BaseMapper<ProjectRepository> {

	/**
     * 根据主键查询
     * @author yang fan
     * @since 2024-02-17 16:52:55
     * @param id 主键
     * @return 返回记录，没有返回null
     */
	ProjectRepository getById(Long id);
	
	/**
     * 新增，插入所有字段
     * @author yang fan
     * @since 2024-02-17 16:52:55
     * @param projectRepository 新增的记录
     * @return 返回影响行数
     */
    @SeqName(value = TableSequenceConstants.ProjectRepository)
	int insert(ProjectRepository projectRepository);
	
	/**
     * 新增，忽略null字段
     * @author yang fan
     * @since 2024-02-17 16:52:55
     * @param projectRepository 新增的记录
     * @return 返回影响行数
     */
    @SeqName(value = TableSequenceConstants.ProjectRepository)
	int insertIgnoreNull(ProjectRepository projectRepository);
	
	/**
     * 修改，修改所有字段
     * @author yang fan
     * @since 2024-02-17 16:52:55
     * @param projectRepository 修改的记录
     * @return 返回影响行数
     */
	int update(ProjectRepository projectRepository);
	
	/**
     * 修改，忽略null字段
     * @author yang fan
     * @since 2024-02-17 16:52:55
     * @param projectRepository 修改的记录
     * @return 返回影响行数
     */
	int updateIgnoreNull(ProjectRepository projectRepository);
	
	
}