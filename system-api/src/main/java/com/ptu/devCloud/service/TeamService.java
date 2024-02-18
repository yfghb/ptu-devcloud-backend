package com.ptu.devCloud.service;

import com.ptu.devCloud.entity.DictItem;
import com.ptu.devCloud.entity.Team;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


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

    /**
     * 修改团队
     * @author Yang Fan
     * @since 2024/2/15 14:31
     * @param team Team
     */
    void updateTeam(Team team);

    /**
     * 团队列表下拉框查询
     * @author Yang Fan
     * @since 2024/2/15 15:10
     * @param userId Long 用户id
     * @return List<DictItem>
     */
    List<DictItem> getTeamList(Long userId);

    /**
     * 切换团队
     * @author Yang Fan
     * @since 2024/2/15 15:53
     * @param teamId Long 团队id
     */
    void changeTeam(Long teamId);

    /**
     * 查询团队成员列表下拉框
     * @author Yang Fan
     * @since 2024/2/18 16:16
     * @param teamId 团队id
     * @return List<DictItem>
     */
    List<DictItem> getUserOptions(Long teamId);
}