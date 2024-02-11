package com.ptu.devCloud.service.impl;

import com.ptu.devCloud.entity.Team;
import com.ptu.devCloud.mapper.TeamMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.ptu.devCloud.service.TeamService;
import javax.annotation.Resource;
import java.util.Collection;
import com.ptu.devCloud.annotation.SeqName;
import com.ptu.devCloud.constants.TableSequenceConstants;

/**
 * TeamServiceImpl
 * @author yang fan
 * @since 2024-02-11 13:19:26
 */
@Service
public class TeamServiceImpl extends ServiceImpl<TeamMapper, Team> implements TeamService{

    @Resource
    private TeamMapper teamMapper;
    
    @Override
    @SeqName(value = TableSequenceConstants.Team)
    public boolean save(Team entity) {
        return super.save(entity);
    }

    @Override
    @SeqName(value = TableSequenceConstants.Team)
    public boolean saveBatch(Collection<Team> entityList) {
        return super.saveBatch(entityList);
    }


	
}