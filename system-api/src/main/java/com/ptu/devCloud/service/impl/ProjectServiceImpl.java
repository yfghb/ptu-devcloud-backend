package com.ptu.devCloud.service.impl;

import cn.hutool.core.util.StrUtil;
import com.ptu.devCloud.entity.Project;
import com.ptu.devCloud.entity.ProjectTeam;
import com.ptu.devCloud.entity.TaskOperationLog;
import com.ptu.devCloud.exception.JobException;
import com.ptu.devCloud.mapper.ProjectMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ptu.devCloud.service.ProjectTeamService;
import com.ptu.devCloud.service.TableSequenceService;
import com.ptu.devCloud.service.TaskOperationLogService;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import com.ptu.devCloud.service.ProjectService;
import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

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
    @Resource
    private TaskOperationLogService taskOperationLogService;

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

    @Override
    public List<Project> getListByTeamId(Long teamId) {
        if(teamId == null)return new ArrayList<>();
        return projectMapper.selectProjectListByTeamId(teamId);
    }

    @Override
    public Integer getMemberCount(Long projectId) {
        if(projectId==null)return null;
        return projectMapper.selectMemberCountByProjectId(projectId);
    }

    @Override
    public List<String> getTeamName(Long projectId) {
        if(projectId==null)return new ArrayList<>();
        return projectMapper.selectTeamNameByProjectId(projectId);
    }

    @Override
    public Project getTaskStatusCnt(Long projectId) {
        if(projectId==null)return null;
        return projectMapper.selectTaskStatusCntByProjectId(projectId);
    }

    @Override
    public List<TaskOperationLog> getTaskLog(Long projectId, String startDate, String endDate) {
        if(projectId==null)return new ArrayList<>();
        return taskOperationLogService.getTaskOperationLogListByProjectId(projectId,startDate,endDate);
    }
}