package com.ptu.devCloud.mapper;


import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ptu.devCloud.annotation.SeqName;
import com.ptu.devCloud.constants.TableSequenceConstants;
import com.ptu.devCloud.entity.UserTeam;

/**
 * UserTeamMapper
 * @author yang fan
 * @since 2024-02-11 13:19:26
 */
@Mapper
public interface UserTeamMapper extends BaseMapper<UserTeam> {

	/**
     * 根据主键查询
     * @author yang fan
     * @since 2024-02-11 13:19:26
     * @param id 主键
     * @return 返回记录，没有返回null
     */
	UserTeam getById(Long id);
	
	/**
     * 新增，插入所有字段
     * @author yang fan
     * @since 2024-02-11 13:19:26
     * @param userTeam 新增的记录
     * @return 返回影响行数
     */
    @SeqName(value = TableSequenceConstants.UserTeam)
	int insert(UserTeam userTeam);
	
	/**
     * 新增，忽略null字段
     * @author yang fan
     * @since 2024-02-11 13:19:26
     * @param userTeam 新增的记录
     * @return 返回影响行数
     */
    @SeqName(value = TableSequenceConstants.UserTeam)
	int insertIgnoreNull(UserTeam userTeam);
	
	/**
     * 修改，修改所有字段
     * @author yang fan
     * @since 2024-02-11 13:19:26
     * @param userTeam 修改的记录
     * @return 返回影响行数
     */
	int update(UserTeam userTeam);
	
	/**
     * 修改，忽略null字段
     * @author yang fan
     * @since 2024-02-11 13:19:26
     * @param userTeam 修改的记录
     * @return 返回影响行数
     */
	int updateIgnoreNull(UserTeam userTeam);
	
	
}