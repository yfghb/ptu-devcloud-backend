package com.ptu.devCloud.mapper;

import java.util.List;

import com.ptu.devCloud.entity.vo.RolePageVO;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ptu.devCloud.annotation.SeqName;
import com.ptu.devCloud.constants.TableSequenceConstants;
import com.ptu.devCloud.entity.Role;
import org.apache.ibatis.annotations.Param;

/**
 * RoleMapper
 * @author yang fan
 * @since 2023-11-09 10:12:44
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {

	/**
     * 查询所有记录
     * @author yang fan
     * @since 2023-11-09 10:12:44
     * @return 返回集合，没有返回空List
     */
	List<Role> listAll();


	/**
     * 根据主键查询
     * @author yang fan
     * @since 2023-11-09 10:12:44
     * @param id 主键
     * @return 返回记录，没有返回null
     */
	Role getById(Long id);
	
	/**
     * 新增，插入所有字段
     * @author yang fan
     * @since 2023-11-09 10:12:44
     * @param role 新增的记录
     * @return 返回影响行数
     */
    @SeqName(value = TableSequenceConstants.Role)
	int insert(Role role);
	
	/**
     * 新增，忽略null字段
     * @author yang fan
     * @since 2023-11-09 10:12:44
     * @param role 新增的记录
     * @return 返回影响行数
     */
    @SeqName(value = TableSequenceConstants.Role)
	int insertIgnoreNull(Role role);
	
	/**
     * 修改，修改所有字段
     * @author yang fan
     * @since 2023-11-09 10:12:44
     * @param role 修改的记录
     * @return 返回影响行数
     */
	int update(Role role);
	
	/**
     * 修改，忽略null字段
     * @author yang fan
     * @since 2023-11-09 10:12:44
     * @param role 修改的记录
     * @return 返回影响行数
     */
	int updateIgnoreNull(Role role);

	/**
	 * 条件查询
	 * @author Yang Fan
	 * @since 2024/1/5 15:32
	 * @param params RolePageVO
	 * @return List<Role>
	 */
	List<Role> selectListByQueryParams(@Param("params") RolePageVO params);
	
}