package com.ptu.devCloud.service.impl;

import com.ptu.devCloud.entity.ProjectTeam;
import com.ptu.devCloud.mapper.ProjectTeamMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.ptu.devCloud.service.ProjectTeamService;
import javax.annotation.Resource;
import java.util.Collection;
import com.ptu.devCloud.annotation.SeqName;
import com.ptu.devCloud.constants.TableSequenceConstants;

/**
 * ProjectTeamServiceImpl
 * @author yang fan
 * @since 2024-02-17 16:52:55
 */
@Service
public class ProjectTeamServiceImpl extends ServiceImpl<ProjectTeamMapper, ProjectTeam> implements ProjectTeamService{

    @Resource
    private ProjectTeamMapper projectTeamMapper;


    @Override
    @SeqName(value = TableSequenceConstants.ProjectTeam)
    public boolean saveBatch(Collection<ProjectTeam> entityList) {
        return super.saveBatch(entityList);
    }


	
}