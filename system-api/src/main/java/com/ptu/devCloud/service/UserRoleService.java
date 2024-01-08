package com.ptu.devCloud.service;


import com.ptu.devCloud.entity.UserRole;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ptu.devCloud.entity.vo.IdsVO;

import java.util.List;


/**
 * UserRoleService
 * @author yang fan
 * @since 2023-11-09 10:12:44
 */
public interface UserRoleService extends IService<UserRole> {

    /**
     * 去除旧的，保存新的用户-关系
     * @author Yang Fan
     * @since 2024/1/3 18:34
     * @param idsVO IdsVO
     */
    void resetUserRoleByIds(IdsVO idsVO);

    /**
     * 以用户id查询关联的角色id列表
     * @author Yang Fan
     * @since 2024/1/8 19:44
     * @param userId 用户id
     * @return List<String> 角色id列表
     */
    List<String> getRoleIdsByUserId(Long userId);
	
}