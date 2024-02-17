package com.ptu.devCloud.service.impl;

import com.ptu.devCloud.entity.ProjectRepository;
import com.ptu.devCloud.mapper.ProjectRepositoryMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.ptu.devCloud.service.ProjectRepositoryService;
import javax.annotation.Resource;
import java.util.Collection;
import com.ptu.devCloud.annotation.SeqName;
import com.ptu.devCloud.constants.TableSequenceConstants;

/**
 * ProjectRepositoryServiceImpl
 * @author yang fan
 * @since 2024-02-17 16:52:55
 */
@Service
public class ProjectRepositoryServiceImpl extends ServiceImpl<ProjectRepositoryMapper, ProjectRepository> implements ProjectRepositoryService{

    @Resource
    private ProjectRepositoryMapper projectRepositoryMapper;


    @Override
    @SeqName(value = TableSequenceConstants.ProjectRepository)
    public boolean saveBatch(Collection<ProjectRepository> entityList) {
        return super.saveBatch(entityList);
    }


	
}