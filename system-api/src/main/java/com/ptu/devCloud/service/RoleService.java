package com.ptu.devCloud.service;



import com.github.pagehelper.PageInfo;
import com.ptu.devCloud.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ptu.devCloud.entity.vo.RolePageVO;
import com.ptu.devCloud.entity.vo.RoleVO;
import java.util.List;


/**
 * RoleService
 * @author yang fan
 * @since 2023-11-09 10:12:44
 */
public interface RoleService extends IService<Role> {


	/**
	 * 查询角色列表(附带角色-权限关系)
	 * @author Yang Fan
	 * @since 2024/1/5 15:24
	 * @param pageVO RolePageVO
	 * @return PageInfo<RoleVO>
	 */
	PageInfo<RoleVO> getPage(RolePageVO pageVO);

	/**
	 * 添加角色，角色-权限关系
	 * @author Yang Fan
	 * @since 2023/12/22 16:56
	 * @param roleVO RoleVO
	 */
	void addRole(RoleVO roleVO);

	/**
	 * 修改角色，角色-权限关系
	 * @author Yang Fan
	 * @since 2023/12/22 17:13
	 * @param roleVO RoleVO
	 */
	void editRole(RoleVO roleVO);

	/**
	 * 批量删除角色和角色-权限关系
	 * @author Yang Fan
	 * @since 2023/12/23 19:34
	 * @param idList 角色id列表
	 */
	void removeRoleBatch(List<Long> idList);

	/**
	 * 查询角色列表(不带角色-权限关系)
	 * @author Yang Fan
	 * @since 2024/1/3 15:48
	 * @return List<RoleVO>
	 */
	List<RoleVO> getRoleList();
}