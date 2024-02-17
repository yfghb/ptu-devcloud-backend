package com.ptu.devCloud.service.impl;

import cn.hutool.core.util.StrUtil;
import com.ptu.devCloud.entity.Project;
import com.ptu.devCloud.entity.ProjectTeam;
import com.ptu.devCloud.exception.JobException;
import com.ptu.devCloud.mapper.ProjectMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ptu.devCloud.service.ProjectTeamService;
import com.ptu.devCloud.service.TableSequenceService;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import com.ptu.devCloud.service.ProjectService;
import javax.annotation.Resource;
import java.util.Collection;
import com.ptu.devCloud.annotation.SeqName;
import com.ptu.devCloud.constants.TableSequenceConstants;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * ProjectServiceImpl
 * @author yang fan
 * @since 2024-02-17 16:24:23
 */
@Service
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, Project> implements ProjectService{

    @Resource
    private ProjectMapper projectMapper;
    @Resource
    private TransactionTemplate transaction;
    @Resource
    private ProjectTeamService projectTeamService;
    @Resource
    private TableSequenceService tableSequenceService;

    @Override
    @SeqName(value = TableSequenceConstants.Project)
    public boolean saveBatch(Collection<Project> entityList) {
        return super.saveBatch(entityList);
    }


    @Override
    public void add(Project project) {
        if(project == null || StrUtil.isEmpty(project.getProjectName()))return;
        if(project.getTeamId() == null){
            throw new JobException("参数缺失");
        }
        project.setId(tableSequenceService.generateId(TableSequenceConstants.Project));
        ProjectTeam projectTeam = new ProjectTeam();
        projectTeam.setProjectId(project.getId());
        projectTeam.setTeamId(project.getTeamId());
        transaction.execute(action -> {
            try {
                projectMapper.insertIgnoreNull(project);
                projectTeamService.save(projectTeam);
                return true;
            }catch (DuplicateKeyException e){
                throw new JobException("项目名称不能重复");
            }catch (Exception e){
                action.setRollbackOnly();
                throw new JobException("新增项目失败！系统繁忙");
            }
        });
    }
}