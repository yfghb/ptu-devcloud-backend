package com.ptu.devCloud.mapper;


import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ptu.devCloud.annotation.SeqName;
import com.ptu.devCloud.constants.TableSequenceConstants;
import com.ptu.devCloud.entity.Team;

/**
 * TeamMapper
 * @author yang fan
 * @since 2024-02-11 13:19:26
 */
@Mapper
public interface TeamMapper extends BaseMapper<Team> {

	/**
     * 根据主键查询
     * @author yang fan
     * @since 2024-02-11 13:19:26
     * @param id 主键
     * @return 返回记录，没有返回null
     */
	Team getById(Long id);
	
	/**
     * 新增，插入所有字段
     * @author yang fan
     * @since 2024-02-11 13:19:26
     * @param team 新增的记录
     * @return 返回影响行数
     */
    @SeqName(value = TableSequenceConstants.Team)
	int insert(Team team);
	
	/**
     * 新增，忽略null字段
     * @author yang fan
     * @since 2024-02-11 13:19:26
     * @param team 新增的记录
     * @return 返回影响行数
     */
    @SeqName(value = TableSequenceConstants.Team)
	int insertIgnoreNull(Team team);
	
	/**
     * 修改，修改所有字段
     * @author yang fan
     * @since 2024-02-11 13:19:26
     * @param team 修改的记录
     * @return 返回影响行数
     */
	int update(Team team);
	
	/**
     * 修改，忽略null字段
     * @author yang fan
     * @since 2024-02-11 13:19:26
     * @param team 修改的记录
     * @return 返回影响行数
     */
	int updateIgnoreNull(Team team);
	
	
}