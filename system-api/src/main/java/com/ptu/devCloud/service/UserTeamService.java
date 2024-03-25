package com.ptu.devCloud.service;

import com.ptu.devCloud.entity.UserTeam;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ptu.devCloud.entity.dto.ChartDataDTO;


/**
 * UserTeamService
 * @author yang fan
 * @since 2024-02-11 13:19:26
 */
public interface UserTeamService extends IService<UserTeam> {

	/**
	 * 添加用户-团队关系
	 * @author Yang Fan
	 * @since 2024/2/14 15:52
	 * @param userTeam UserTeam
	 */
	void addUserTeam(UserTeam userTeam);

	/**
	 * 查询当前用户的团队列表和当前团队
	 * @author Yang Fan
	 * @since 2024/3/22 13:53
	 * @return ChartDataDTO
	 */
	ChartDataDTO getTeamList();
}