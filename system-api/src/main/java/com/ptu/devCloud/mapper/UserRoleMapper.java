package com.ptu.devCloud.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ptu.devCloud.entity.UserRole;

/**
 * UserRoleMapper
 * @author yang fan
 * @since 2023-11-09 10:12:44
 */
@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {

	/**
     * 查询所有记录
     * @author yang fan
     * @since 2023-11-09 10:12:44
     * @return 返回集合，没有返回空List
     */
	List<UserRole> listAll();


	/**
     * 根据主键查询
     * @author yang fan
     * @since 2023-11-09 10:12:44
     * @param id 主键
     * @return 返回记录，没有返回null
     */
	UserRole getById(Long id);
	
	/**
     * 新增，插入所有字段
     * @author yang fan
     * @since 2023-11-09 10:12:44
     * @param userRole 新增的记录
     * @return 返回影响行数
     */
	int insert(UserRole userRole);
	
	/**
     * 新增，忽略null字段
     * @author yang fan
     * @since 2023-11-09 10:12:44
     * @param userRole 新增的记录
     * @return 返回影响行数
     */
	int insertIgnoreNull(UserRole userRole);
	
	/**
     * 修改，修改所有字段
     * @author yang fan
     * @since 2023-11-09 10:12:44
     * @param userRole 修改的记录
     * @return 返回影响行数
     */
	int update(UserRole userRole);
	
	/**
     * 修改，忽略null字段
     * @author yang fan
     * @since 2023-11-09 10:12:44
     * @param userRole 修改的记录
     * @return 返回影响行数
     */
	int updateIgnoreNull(UserRole userRole);
	
	
}