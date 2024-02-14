package com.ptu.devCloud.service.impl;

import com.ptu.devCloud.entity.User;
import com.ptu.devCloud.entity.UserTeam;
import com.ptu.devCloud.exception.JobException;
import com.ptu.devCloud.mapper.UserMapper;
import com.ptu.devCloud.mapper.UserTeamMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;
import com.ptu.devCloud.service.UserTeamService;
import javax.annotation.Resource;
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
}