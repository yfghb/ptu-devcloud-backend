package com.ptu.devCloud.service.impl;

import com.ptu.devCloud.entity.TaskOperationLog;
import com.ptu.devCloud.mapper.TaskOperationLogMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.ptu.devCloud.service.TaskOperationLogService;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ptu.devCloud.annotation.SeqName;
import com.ptu.devCloud.constants.TableSequenceConstants;

/**
 * TaskOperationLogServiceImpl
 * @author yang fan
 * @since 2024-03-13 10:41:58
 */
@Service
public class TaskOperationLogServiceImpl extends ServiceImpl<TaskOperationLogMapper, TaskOperationLog> implements TaskOperationLogService{

    @Resource
    private TaskOperationLogMapper taskOperationLogMapper;

    @Override
    @SeqName(value = TableSequenceConstants.TaskOperationLog)
    public boolean saveBatch(Collection<TaskOperationLog> entityList) {
        return super.saveBatch(entityList);
    }


    @Override
    public List<TaskOperationLog> getTaskOperationLogList(Long taskId) {
        if(taskId == null)return new ArrayList<>();
        return taskOperationLogMapper.selectListByTaskId(taskId);
    }

    @Override
    public List<TaskOperationLog> getTaskOperationLogListByProjectId(Long projectId, String startDate, String endDate) {
        if(projectId == null)return new ArrayList<>();
        return taskOperationLogMapper.selectListByProjectId(projectId,startDate,endDate);
    }
}