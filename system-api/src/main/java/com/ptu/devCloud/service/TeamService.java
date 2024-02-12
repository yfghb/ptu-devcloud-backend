package com.ptu.devCloud.service;

import com.ptu.devCloud.entity.Team;
import com.baomidou.mybatisplus.extension.service.IService;


/**
 * TeamService
 * @author yang fan
 * @since 2024-02-11 13:19:26
 */
public interface TeamService extends IService<Team> {

    /**
     * 添加团队
     * @author Yang Fan
     * @since 2024/2/12 12:37
     * @param team Team
     */
    void addTeam(Team team);

    /**
     * 以用户id查询团队信息和成员信息
     * @author Yang Fan
     * @since 2024/2/12 14:59
     * @param userId 用户id
     * @return Team
     */
	Team getTeamByUserId(Long userId);
}