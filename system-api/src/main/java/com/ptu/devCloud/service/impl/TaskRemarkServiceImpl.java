package com.ptu.devCloud.service.impl;

import com.ptu.devCloud.entity.TaskRemark;
import com.ptu.devCloud.mapper.TaskRemarkMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;
import com.ptu.devCloud.service.TaskRemarkService;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ptu.devCloud.annotation.SeqName;
import com.ptu.devCloud.constants.TableSequenceConstants;

/**
 * TaskRemarkServiceImpl
 * @author yang fan
 * @since 2024-01-25 09:27:01
 */
@Service
public class TaskRemarkServiceImpl extends ServiceImpl<TaskRemarkMapper, TaskRemark> implements TaskRemarkService{

    @Resource
    private TaskRemarkMapper taskRemarkMapper;
    
    @Override
    @SeqName(value = TableSequenceConstants.TaskRemark)
    public boolean save(TaskRemark entity) {
        return super.save(entity);
    }

    @Override
    @SeqName(value = TableSequenceConstants.TaskRemark)
    public boolean saveBatch(Collection<TaskRemark> entityList) {
        return super.saveBatch(entityList);
    }


    @Override
    public void add(TaskRemark taskRemark) {
        if(taskRemark != null && taskRemark.getTaskId() != null){
            taskRemarkMapper.insertIgnoreNull(taskRemark);
        }
    }

    @Override
    public List<TaskRemark> getListByTaskId(Long taskId) {
        if(taskId == null)return new ArrayList<>();
        TaskRemarkServiceImpl proxy = (TaskRemarkServiceImpl) AopContext.currentProxy();
        List<TaskRemark> list = proxy.lambdaQuery()
                .eq(TaskRemark::getTaskId, taskId)
                .orderByDesc(TaskRemark::getCreateDate)
                .list();
        if(list != null && list.size() > 0) {
            TaskRemark taskRemark = list.get(0);
            taskRemark.setNewFlag(true);
            list.set(0, taskRemark);
        }
        return list;
    }
}