package com.ptu.devCloud.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ptu.devCloud.entity.Permission;
import org.apache.ibatis.annotations.Param;

/**
 * PermissionMapper
 * @author yang fan
 * @since 2023-11-09 10:12:44
 */
@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {

	/**
     * 查询所有记录
     * @author yang fan
     * @since 2023-11-09 10:12:44
     * @return 返回集合，没有返回空List
     */
	List<Permission> listAll();


	/**
     * 根据主键查询
     * @author yang fan
     * @since 2023-11-09 10:12:44
     * @param id 主键
     * @return 返回记录，没有返回null
     */
	Permission getById(Long id);
	
	/**
     * 新增，插入所有字段
     * @author yang fan
     * @since 2023-11-09 10:12:44
     * @param permission 新增的记录
     * @return 返回影响行数
     */
	int insert(Permission permission);
	
	/**
     * 新增，忽略null字段
     * @author yang fan
     * @since 2023-11-09 10:12:44
     * @param permission 新增的记录
     * @return 返回影响行数
     */
	int insertIgnoreNull(Permission permission);
	
	/**
     * 修改，修改所有字段
     * @author yang fan
     * @since 2023-11-09 10:12:44
     * @param permission 修改的记录
     * @return 返回影响行数
     */
	int update(Permission permission);
	
	/**
     * 修改，忽略null字段
     * @author yang fan
     * @since 2023-11-09 10:12:44
     * @param permission 修改的记录
     * @return 返回影响行数
     */
	int updateIgnoreNull(Permission permission);


	/**
	 * 根据父id查询子菜单列表
	 * @author Yang Fan
	 * @since 2023/11/23 14:39
	 * @param parentId 父id(若为0, 则查询一级菜单)
	 * @return 子菜单列表
	 */
	List<Permission> selectMenuList(@Param(value = "parentId") Long parentId);
}