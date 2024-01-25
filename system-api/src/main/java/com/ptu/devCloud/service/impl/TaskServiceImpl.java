package com.ptu.devCloud.service.impl;

import com.ptu.devCloud.entity.Task;
import com.ptu.devCloud.mapper.TaskMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.ptu.devCloud.service.TaskService;
import javax.annotation.Resource;
import java.util.Collection;
import com.ptu.devCloud.annotation.SeqName;
import com.ptu.devCloud.constants.TableSequenceConstants;

/**
 * TaskServiceImpl
 * @author yang fan
 * @since 2024-01-25 09:27:01
 */
@Service
public class TaskServiceImpl extends ServiceImpl<TaskMapper, Task> implements TaskService{

    @Resource
    private TaskMapper taskMapper;
    
    @Override
    @SeqName(value = TableSequenceConstants.Task)
    public boolean save(Task entity) {
        return super.save(entity);
    }

    @Override
    @SeqName(value = TableSequenceConstants.Task)
    public boolean saveBatch(Collection<Task> entityList) {
        return super.saveBatch(entityList);
    }


	
}