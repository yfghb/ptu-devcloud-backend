package com.ptu.devCloud.service.impl;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ptu.devCloud.entity.Task;
import com.ptu.devCloud.entity.vo.TaskPageVO;
import com.ptu.devCloud.exception.JobException;
import com.ptu.devCloud.mapper.TaskMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ptu.devCloud.utils.RedisUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import com.ptu.devCloud.service.TaskService;
import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;

import com.ptu.devCloud.annotation.SeqName;
import com.ptu.devCloud.constants.TableSequenceConstants;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * TaskServiceImpl
 * @author yang fan
 * @since 2024-01-25 09:27:01
 */
@Service
public class TaskServiceImpl extends ServiceImpl<TaskMapper, Task> implements TaskService{

    @Resource
    private TaskMapper taskMapper;

    @Resource
    private RedisUtils redisUtils;

    @Resource
    private TransactionTemplate transaction;
    
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


    @Override
    public void addTask(Task task) {
        if(task == null){
            throw new JobException("task对象为空");
        }
        boolean lockSuccess = false;
        String uuid = UUID.randomUUID().toString(true);
        long startTime = System.currentTimeMillis();
        // 只给3s的机会加锁
        while (System.currentTimeMillis() - startTime < 3000){
            if(redisUtils.getLock("lockAddTask", uuid, 5)){
                lockSuccess = true;
                break;
            }
        }
        if(!lockSuccess){
            // 3s内都没加锁成功就返回系统繁忙
            throw new JobException("系统繁忙，请稍后再试");
        }else {
            transaction.execute(action -> {
                task.setSerialNumber(generateSerialNumber());
                try{
                    taskMapper.insert(task);
                }catch (DuplicateKeyException e){
                    action.setRollbackOnly();
                    throw new JobException("系统繁忙，请稍后再试");
                }
                // 如果时间大于5s则返回系统繁忙
                if(System.currentTimeMillis() - startTime > 5000){
                    action.setRollbackOnly();
                    throw new JobException("系统繁忙，请稍后再试");
                }else {
                    redisUtils.delLock("lockAddTask", uuid);
                }
                return true;
            });
        }
    }

    @Override
    public PageInfo<Task> getPage(TaskPageVO pageVO) {
        if(pageVO == null || pageVO.getCurrent() == null || pageVO.getPageSize() == null) {
            return new PageInfo<>();
        }
        PageHelper.startPage(pageVO.getCurrent(), pageVO.getPageSize());
        List<Task> list = taskMapper.selectListByQueryParams(pageVO);
        return new PageInfo<>(list);
    }

    private String generateSerialNumber(){
        String serialNumber = taskMapper.selectTodayMaxSerialNumber();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String today = formatter.format(LocalDateTime.now());
        if(StrUtil.isEmpty(serialNumber)){
            serialNumber = today + "1";
        }
        else serialNumber = today + (Integer.parseInt(serialNumber.substring(8)) + 1);
        return serialNumber;
    }
}