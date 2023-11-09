package com.ptu.devCloud.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ptu.devCloud.annotation.SeqName;
import com.ptu.devCloud.constants.TableSequenceConstants;
import com.ptu.devCloud.entity.RolePermission;

/**
 * RolePermissionMapper
 * @author yang fan
 * @since 2023-11-09 10:12:44
 */
@Mapper
public interface RolePermissionMapper extends BaseMapper<RolePermission> {

	/**
     * 查询所有记录
     * @author yang fan
     * @since 2023-11-09 10:12:44
     * @return 返回集合，没有返回空List
     */
	List<RolePermission> listAll();


	/**
     * 根据主键查询
     * @author yang fan
     * @since 2023-11-09 10:12:44
     * @param id 主键
     * @return 返回记录，没有返回null
     */
	RolePermission getById(Long id);
	
	/**
     * 新增，插入所有字段
     * @author yang fan
     * @since 2023-11-09 10:12:44
     * @param rolePermission 新增的记录
     * @return 返回影响行数
     */
    @SeqName(value = TableSequenceConstants.RolePermission)
	int insert(RolePermission rolePermission);
	
	/**
     * 新增，忽略null字段
     * @author yang fan
     * @since 2023-11-09 10:12:44
     * @param rolePermission 新增的记录
     * @return 返回影响行数
     */
    @SeqName(value = TableSequenceConstants.RolePermission)
	int insertIgnoreNull(RolePermission rolePermission);
	
	/**
     * 修改，修改所有字段
     * @author yang fan
     * @since 2023-11-09 10:12:44
     * @param rolePermission 修改的记录
     * @return 返回影响行数
     */
	int update(RolePermission rolePermission);
	
	/**
     * 修改，忽略null字段
     * @author yang fan
     * @since 2023-11-09 10:12:44
     * @param rolePermission 修改的记录
     * @return 返回影响行数
     */
	int updateIgnoreNull(RolePermission rolePermission);
	
	
}