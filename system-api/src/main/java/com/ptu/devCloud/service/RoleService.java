package com.ptu.devCloud.service;


import com.ptu.devCloud.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

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
	 * @return List<Role>
	 */
	List<Role> list(String roleName);
}