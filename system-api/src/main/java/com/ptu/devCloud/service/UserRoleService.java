package com.ptu.devCloud.service;


import com.ptu.devCloud.entity.UserRole;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ptu.devCloud.entity.vo.IdsVO;


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
	
}