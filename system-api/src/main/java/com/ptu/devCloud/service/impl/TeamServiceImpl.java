package com.ptu.devCloud.service.impl;

import cn.hutool.core.util.StrUtil;
import com.ptu.devCloud.constants.CommonConstants;
import com.ptu.devCloud.entity.*;
import com.ptu.devCloud.exception.JobException;
import com.ptu.devCloud.mapper.TeamMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ptu.devCloud.service.TableSequenceService;
import com.ptu.devCloud.service.UserService;
import com.ptu.devCloud.service.UserTeamService;
import com.ptu.devCloud.utils.JwtUtil;
import com.ptu.devCloud.utils.RedisUtils;
import com.ptu.devCloud.utils.SecurityUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import com.ptu.devCloud.service.TeamService;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ptu.devCloud.annotation.SeqName;
import com.ptu.devCloud.constants.TableSequenceConstants;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * TeamServiceImpl
 * @author yang fan
 * @since 2024-02-11 13:19:26
 */
@Service
public class TeamServiceImpl extends ServiceImpl<TeamMapper, Team> implements TeamService{

    @Resource
    private TeamMapper teamMapper;

    @Resource
    private UserService userService;

    @Resource
    private UserTeamService userTeamService;

    @Resource
    private TransactionTemplate transaction;

    @Resource
    private TableSequenceService tableSequenceService;

    @Resource
    private RedisUtils redisUtils;

    @Override
    @SeqName(value = TableSequenceConstants.Team)
    public boolean saveBatch(Collection<Team> entityList) {
        return super.saveBatch(entityList);
    }


    @Override
    public void addTeam(Team team) {
        if(SecurityUtils.getLoginUser() == null){
            throw new JobException("登录认证异常，请重新登录");
        }
        if(team == null){
            throw new JobException("参数不正确");
        }
        LoginUser loginUser = SecurityUtils.getLoginUser();
        User user = loginUser.getUser();
        if(team.getSuperAdmin() == null){
            team.setSuperAdmin(user.getId());
        }
        Long teamId = tableSequenceService.generateId(TableSequenceConstants.Team);
        team.setId(teamId);
        List<Long> teamIds = loginUser.getTeamIds() == null ? new ArrayList<>() : loginUser.getTeamIds();
        teamIds.add(teamId);
        // 更新当前团队id
        user.setCurrentTeamId(teamId);
        // 更新团队id列表
        loginUser.setTeamIds(teamIds);
        String userRedisKey = SecurityUtils.aesEncrypt(user.getLoginAccount(), CommonConstants.SECRET_KEY_16);
        String userRedisToken = JwtUtil.generate(loginUser);
        // 新增userTeam关系
        UserTeam userTeam = new UserTeam();
        userTeam.setUserId(user.getId());
        userTeam.setTeamId(teamId);
        transaction.execute(action -> {
            try {
                teamMapper.insertIgnoreNull(team);
                userTeamService.save(userTeam);
                if(team.getChangeTeam()){
                    userService.updateById(user);
                    // 如果立即切换团队，则更新token
                    redisUtils.set(userRedisKey, userRedisToken, 60 * 60 * 2);
                }
                return true;
            }catch (DuplicateKeyException e){
                action.setRollbackOnly();
                throw new JobException("当前团队名已存在，请重新输入");
            }catch (Exception e){
                action.setRollbackOnly();
                throw new JobException("新建团队失败，系统繁忙");
            }
        });


    }

    @Override
    public Team getTeamByUserId(Long userId) {
        if(userId == null)return null;
        User user = userService.getById(userId);
        Long teamId = user.getCurrentTeamId();
        if(teamId == null)return null;
        // 查询团队信息
        Team team = teamMapper.getById(teamId);
        if(team == null)return null;
        // 查询成员信息
        List<UserTeam> userTeamList = userTeamService.lambdaQuery().eq(UserTeam::getTeamId, teamId).list();
        List<Long> userIds = new ArrayList<>();
        userTeamList.forEach(obj -> userIds.add(obj.getUserId()));
        team.setUserList(userService.getUserListByIds(userIds));
        return team;
    }

    @Override
    public void updateTeam(Team team) {
        if(team == null || team.getId() == null || StrUtil.isEmpty(team.getTeamName())){
            throw new JobException("参数错误");
        }
        teamMapper.updateIgnoreNull(team);
    }

    @Override
    public List<DictItem> getTeamList(Long userId) {
        if(userId == null)return new ArrayList<>();
        return teamMapper.selectTeamListByUserId(userId);
    }

    @Override
    public void changeTeam(Long teamId) {
        if(teamId == null) throw new JobException("参数不能为空");
        LoginUser loginUser = SecurityUtils.getLoginUser();
        if(loginUser == null) throw new JobException("登录认证异常，请重新登录");
        User user = loginUser.getUser();
        Team team = teamMapper.selectById(teamId);
        if(team == null) throw new JobException("团队不存在或已被删除");
        user.setCurrentTeamId(teamId);
        String userRedisKey = SecurityUtils.aesEncrypt(user.getLoginAccount(), CommonConstants.SECRET_KEY_16);
        String userRedisToken = JwtUtil.generate(loginUser);
        transaction.execute(action -> {
            try {
                userService.updateById(user);
                redisUtils.set(userRedisKey, userRedisToken, 60 * 60 * 2);
                return true;
            }catch (Exception e){
                action.setRollbackOnly();
                throw new JobException("切换团队失败，系统繁忙");
            }
        });
    }
}