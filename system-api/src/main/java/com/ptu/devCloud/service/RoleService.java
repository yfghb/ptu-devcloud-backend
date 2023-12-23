package com.ptu.devCloud.service;



import com.ptu.devCloud.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ptu.devCloud.entity.vo.RoleVO;
import java.util.List;


/**
 * RoleService
 * @author yang fan
 * @since 2023-11-09 10:12:44
 */
public interface RoleService extends IService<Role> {


	/**
	 * 查询角色列表
	 * @param roleName 角色名称
	 * @return List<RoleVO>
	 */
	List<RoleVO> list(String roleName);

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
}