package com.ptu.devCloud.service.impl;

import com.ptu.devCloud.entity.UserTeam;
import com.ptu.devCloud.mapper.UserTeamMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.ptu.devCloud.service.UserTeamService;
import javax.annotation.Resource;
import java.util.Collection;
import com.ptu.devCloud.annotation.SeqName;
import com.ptu.devCloud.constants.TableSequenceConstants;

/**
 * UserTeamServiceImpl
 * @author yang fan
 * @since 2024-02-11 13:19:26
 */
@Service
public class UserTeamServiceImpl extends ServiceImpl<UserTeamMapper, UserTeam> implements UserTeamService{

    @Resource
    private UserTeamMapper userTeamMapper;
    
    @Override
    @SeqName(value = TableSequenceConstants.UserTeam)
    public boolean save(UserTeam entity) {
        return super.save(entity);
    }

    @Override
    @SeqName(value = TableSequenceConstants.UserTeam)
    public boolean saveBatch(Collection<UserTeam> entityList) {
        return super.saveBatch(entityList);
    }


	
}