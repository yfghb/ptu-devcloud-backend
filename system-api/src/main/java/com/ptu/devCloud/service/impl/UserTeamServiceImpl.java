package com.ptu.devCloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ptu.devCloud.entity.LoginUser;
import com.ptu.devCloud.entity.Team;
import com.ptu.devCloud.entity.User;
import com.ptu.devCloud.entity.UserTeam;
import com.ptu.devCloud.entity.dto.ChartDataDTO;
import com.ptu.devCloud.exception.JobException;
import com.ptu.devCloud.mapper.TeamMapper;
import com.ptu.devCloud.mapper.UserMapper;
import com.ptu.devCloud.mapper.UserTeamMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ptu.devCloud.utils.SecurityUtils;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;
import com.ptu.devCloud.service.UserTeamService;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ptu.devCloud.annotation.SeqName;
import com.ptu.devCloud.constants.TableSequenceConstants;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * UserTeamServiceImpl
 * @author yang fan
 * @since 2024-02-11 13:19:26
 */
@Service
public class UserTeamServiceImpl extends ServiceImpl<UserTeamMapper, UserTeam> implements UserTeamService{

    @Resource
    private UserTeamMapper userTeamMapper;

    @Resource
    private TransactionTemplate transaction;

    @Resource
    private UserMapper userMapper;

    @Resource
    private TeamMapper teamMapper;

    @Override
    @SeqName(value = TableSequenceConstants.UserTeam)
    public boolean saveBatch(Collection<UserTeam> entityList) {
        return super.saveBatch(entityList);
    }


    @Override
    public void addUserTeam(UserTeam userTeam) {
        if(userTeam == null || userTeam.getTeamId() == null || userTeam.getUserId() == null){
            throw new JobException("参数不能为空");
        }
        UserTeamServiceImpl proxy = (UserTeamServiceImpl) AopContext.currentProxy();
        List<UserTeam> list = proxy.lambdaQuery()
                .eq(UserTeam::getTeamId, userTeam.getTeamId())
                .eq(UserTeam::getUserId, userTeam.getUserId())
                .list();
        if(list != null && list.size() > 0){
            throw new JobException("已添加，请不要重复添加");
        }
        User member = userMapper.getById(userTeam.getUserId());
        if(member == null){
            throw new JobException("找不到用户");
        }
        boolean update = false;
        // 如果当前用户没有团队，则更新
        if(member.getCurrentTeamId() == null) {
            member.setCurrentTeamId(userTeam.getTeamId());
            update = true;
        }
        boolean finalUpdate = update;
        transaction.execute(action -> {
            try {
                userTeamMapper.insertIgnoreNull(userTeam);
                if(finalUpdate)userMapper.updateIgnoreNull(member);
                return true;
            }catch (Exception e){
                action.setRollbackOnly();
                throw new JobException("失败！");
            }
        });

    }

    @Override
    public ChartDataDTO getTeamList() {
        ChartDataDTO dto = new ChartDataDTO();
        LoginUser loginUser = SecurityUtils.getLoginUser();
        if(loginUser == null){
            throw new JobException("登录认证异常，请重新登录");
        }
        User user = loginUser.getUser();
        LambdaQueryWrapper<UserTeam> lqw = new LambdaQueryWrapper<>();
        lqw.eq(UserTeam::getUserId, user.getId());
        List<UserTeam> userTeamList = userTeamMapper.selectList(lqw);
        List<Long> teamIds = new ArrayList<>();
        userTeamList.forEach(o -> teamIds.add(o.getTeamId()));
        List<Team> teamList = teamMapper.selectBatchIds(teamIds);
        Team team = teamMapper.selectById(user.getCurrentTeamId());
        dto.setTeamList(teamList);
        dto.setCurrentTeam(team);
        return dto;
    }
}