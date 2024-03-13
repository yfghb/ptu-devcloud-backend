package com.ptu.devCloud.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ptu.devCloud.entity.*;
import com.ptu.devCloud.entity.vo.IdsVO;
import com.ptu.devCloud.entity.vo.TaskCardVO;
import com.ptu.devCloud.entity.vo.TaskPageVO;
import com.ptu.devCloud.exception.JobException;
import com.ptu.devCloud.mapper.TaskMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ptu.devCloud.service.*;
import com.ptu.devCloud.utils.RedisUtils;
import com.ptu.devCloud.utils.SecurityUtils;
import org.springframework.aop.framework.AopContext;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

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
    private UserService userService;

    @Resource
    private UserTeamService userTeamService;

    @Resource
    private ProjectTeamService projectTeamService;

    @Resource
    private TransactionTemplate transaction;

    @Resource
    private TaskOperationLogService taskOperationLogService;

    @Resource
    private TableSequenceService tableSequenceService;


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
        if(task.getTaskName().contains("##")){
            throw new JobException("任务名称不能包含’##‘");
        }
        boolean lockSuccess = false;
        String uuid = UUID.randomUUID().toString(true);
        long startTime = System.currentTimeMillis();
        LoginUser loginUser = SecurityUtils.getLoginUser();
        String username = loginUser == null ? " " : loginUser.getUser().getUserName();
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
            task.setId(tableSequenceService.generateId(TableSequenceConstants.Task));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String now = formatter.format(LocalDateTime.now()) + " ";
            TaskOperationLog taskOperationLog = new TaskOperationLog();
            taskOperationLog.setTaskId(task.getId());
            taskOperationLog.setOperationLog(now + username + " 创建了任务：" + task.getTaskName());
            task.setCurrentOperator(loginUser == null ? null : loginUser.getUser().getId());
            task.setParticipant(loginUser == null ? null : loginUser.getUser().getId().toString());
            transaction.execute(action -> {
                task.setSerialNumber(generateSerialNumber());
                try{
                    taskMapper.insert(task);
                    taskOperationLogService.save(taskOperationLog);
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

    @Override
    public void editById(Task task) {
        if(task == null || task.getId() == null)return;
        Task origin = taskMapper.selectById(task.getId());
        if(origin == null)throw new JobException("当前任务已经被删除！");
        // 校验用户是否有编辑该任务的权限
        checkTeamAndProject(task);
        if(!origin.getTeamId().equals(task.getTeamId()) || !origin.getProjectId().equals(task.getProjectId())){
            // 校验团队-项目关系是否存在
            List<ProjectTeam> list = projectTeamService.lambdaQuery()
                    .eq(ProjectTeam::getTeamId, task.getTeamId())
                    .eq(ProjectTeam::getProjectId, task.getProjectId())
                    .list();
            if(list == null || list.size() != 1){
                throw new JobException("操作被拒绝，原因：错误的团队-项目关系。请检查该团队是否存在所属项目信息");
            }
        }
        List<TaskOperationLog> operationLogs = new ArrayList<>();
        if(!origin.getTaskStatus().equals(task.getTaskStatus())){
            TaskOperationLog statusChangeLog = new TaskOperationLog();
            // 新增任务状态变更的日志
            statusChangeLog.setTaskId(task.getId());
            statusChangeLog.setOperationLog(generateStatusOperationLog(task.getTaskStatus(), task.getTaskName()));
            operationLogs.add(statusChangeLog);
        }
        if(!Objects.equals(origin.getCurrentOperator(), task.getCurrentOperator())){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String now = formatter.format(LocalDateTime.now()) + " ";
            User beforeOperator = userService.getById(origin.getCurrentOperator());
            String taskUserName = "";
            String beforeOperatorName = beforeOperator == null ? "未知用户" : beforeOperator.getUserName();
            if(task.getCurrentOperator() != null){
                User user = userService.getById(task.getCurrentOperator());
                taskUserName = user == null ? "未知用户" : user.getUserName();
            }
            TaskOperationLog userChangeLog = new TaskOperationLog();
            userChangeLog.setTaskId(task.getId());
            userChangeLog.setOperationLog(now + beforeOperatorName + " 将任务: " + task.getTaskName() + " 转派给 " + taskUserName);
            // 新增任务转派日志
            operationLogs.add(userChangeLog);
            // 更新参与者
            String participant = task.getParticipant();
            Set<String> set = new HashSet<>();
            StringBuilder builder = new StringBuilder();
            if(StrUtil.isNotEmpty(participant)){
                for(String id:participant.split(",")){
                    if(set.add(id))builder.append(id).append(",");
                }
            }
            if(set.add(task.getCurrentOperator().toString()))builder.append(task.getCurrentOperator()).append(",");
            if(builder.length() > 0 && builder.toString().charAt(builder.length()-1) == ','){
                builder.deleteCharAt(builder.length()-1);
            }
            task.setParticipant(builder.toString());
        }
        transaction.execute(action -> {
            try {
                taskMapper.updateIgnoreNull(task);
                if(!operationLogs.isEmpty()){
                    taskOperationLogService.saveBatch(operationLogs);
                }
                return true;
            }catch (Exception e){
                action.setRollbackOnly();
                throw new JobException("操作失败，可能是系统繁忙");
            }
        });
    }

    private void checkTeamAndProject(Task task){
        LoginUser loginUser = SecurityUtils.getLoginUser();
        boolean success = false;
        if(loginUser == null){
            throw new JobException("登录认证异常，清重新登录");
        }
        if(task == null)return;
        User user = loginUser.getUser();
        List<UserTeam> list = userTeamService.lambdaQuery().eq(UserTeam::getUserId, user.getId()).list();
        for(UserTeam userTeam:list){
            if (userTeam.getTeamId().equals(task.getTeamId())) {
                success = true;
                break;
            }
        }
        if(!success){
            throw new JobException("无法编辑当前任务！原因：您不在所属团队中。");
        }
    }

    @Override
    public void changeStatus(String serialNumber, String taskStatus) {
        Task task = taskMapper.selectBySerialNumber(serialNumber);
        checkTeamAndProject(task);
        TaskOperationLog statusChangeLog = new TaskOperationLog();
        statusChangeLog.setTaskId(task.getId());
        statusChangeLog.setOperationLog(generateStatusOperationLog(taskStatus, task.getTaskName()));
        transaction.execute(action -> {
            try{
                taskMapper.updateTaskStatusBySerialNumber(serialNumber, taskStatus);
                taskOperationLogService.save(statusChangeLog);
                return true;
            }catch (Exception e){
                action.setRollbackOnly();
                throw new JobException(e.getMessage());
            }
        });
    }

    @Override
    public void linkTask(List<String> taskIds, String serialNumber) {
        if(StrUtil.isEmpty(serialNumber)){
            throw new JobException("输入的任务编号不能为空");
        }
        TaskServiceImpl proxy = (TaskServiceImpl) AopContext.currentProxy();
        Task task = proxy.lambdaQuery().eq(Task::getSerialNumber, serialNumber).one();
        if(task == null) {
            throw new JobException("找不到当前输入的任务编号下的任务，无法关联");
        }
        String correlationIds = task.getCorrelationIds();
        List<String> originIds = StrUtil.isNotEmpty(correlationIds) ?
                Arrays.asList(correlationIds.split(",")) : new ArrayList<>();
        taskIds = taskIds.stream()
                // 过滤掉已经关联的
                .filter(id -> {
                    if(CollUtil.isEmpty(originIds))return true;
                    return !originIds.contains(id);
                })
                // 过滤掉自己
                .filter(id -> !id.equals(task.getId().toString()))
                .collect(Collectors.toList());
        StringBuilder builder = new StringBuilder();
        for(int i=0;i<taskIds.size();i++){
            if(i != taskIds.size() - 1){
                builder.append(taskIds.get(i)).append(",");
            }else builder.append(taskIds.get(i));
        }
        List<String> finalTaskIds = taskIds;
        transaction.execute(action -> {
            try {
                // 双向关联
                if(CollUtil.isNotEmpty(finalTaskIds)){
                    taskMapper.appendCorrelationIdsById(Collections.singletonList(task.getId().toString()), builder.toString());
                    taskMapper.appendCorrelationIdsById(finalTaskIds, task.getId().toString());
                }
                return true;
            }catch (Exception e){
                action.setRollbackOnly();
                throw new JobException(e.getMessage());
            }
        });
    }

    @Override
    public List<Task> getTaskListBySerialNumberList(String correlationIds) {
        if(StrUtil.isEmpty(correlationIds))return new ArrayList<>();
        return taskMapper.selectByIdsIgnoreRemark(Arrays.asList(correlationIds.split(",")));
    }

    @Override
    public Task getDetailBySerialNumber(String serialNumber) {
        return taskMapper.selectBySerialNumber(serialNumber);
    }

    @Override
    public void unlink(Long id1, Long id2) {
        if(id1 == null || id2 == null || id1.equals(id2)) return;
        Task task1 = taskMapper.selectById(id1);
        Task task2 = taskMapper.selectById(id2);
        if(task1 == null || task2 == null)return;
        if(StrUtil.isEmpty(task1.getCorrelationIds()) || StrUtil.isEmpty(task2.getCorrelationIds()))return;
        StringBuilder builder1 = new StringBuilder();
        StringBuilder builder2 = new StringBuilder();
        for(String id:task1.getCorrelationIds().split(",")){
            if(!(Long.valueOf(id)).equals(task2.getId())){
                builder1.append(id).append(",");
            }
        }
        for(String id:task2.getCorrelationIds().split(",")){
            if(!(Long.valueOf(id)).equals(task1.getId())){
                builder2.append(id).append(",");
            }
        }
        if(builder1.length() > 0)builder1.deleteCharAt(builder1.length() - 1);
        if(builder2.length() > 0)builder2.deleteCharAt(builder2.length() - 1);
        task1.setCorrelationIds(builder1.length()==0?null:builder1.toString());
        task2.setCorrelationIds(builder2.length()==0?null:builder2.toString());
        transaction.execute(action -> {
            try {
                taskMapper.update(task1);
                taskMapper.update(task2);
                return true;
            }catch (Exception e){
                action.setRollbackOnly();
                throw new JobException(e.getMessage());
            }
        });
    }

    @Override
    public void deleteBatch(IdsVO vo) {
        if(vo == null || CollUtil.isEmpty(vo.getTaskIds()))return;
        List<String> list = taskMapper.selectTaskStatusByIds(vo.getTaskIds());
        boolean errFlag = false;
        for (String status : list) {
            if (!status.equals("close")) {
                errFlag = true;
                break;
            }
        }
        if(errFlag) throw new JobException("批量删除失败！任务状态必须全为'已关闭'才可删除！");
        TaskServiceImpl proxy = (TaskServiceImpl) AopContext.currentProxy();
        proxy.removeByIds(vo.getTaskIds());
    }

    @Override
    public void closeBatch(IdsVO vo) {
        if(vo == null || CollUtil.isEmpty(vo.getTaskIds()))return;
        LoginUser loginUser = SecurityUtils.getLoginUser();
        String username = loginUser == null ? "未知用户" : loginUser.getUser().getUserName();
        List<Task> tasks = taskMapper.selectByIdsIgnoreRemark(vo.getTaskIds());
        List<TaskOperationLog> logList = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String now = formatter.format(LocalDateTime.now()) + " ";
        for(Task task:tasks){
            TaskOperationLog taskOperationLog = new TaskOperationLog();
            String logStr = now + username + " 将任务: " + task.getTaskName() + " 的状态改为 已关闭";
            taskOperationLog.setTaskId(task.getId());
            taskOperationLog.setOperationLog(logStr);
            logList.add(taskOperationLog);
        }
        transaction.execute(action -> {
            try {
                taskOperationLogService.saveBatch(logList);
                // 修改任务状态为‘已关闭’
                taskMapper.updateTaskStatusByIds(vo.getTaskIds(), "close");
                return true;
            }catch (Exception e){
                action.setRollbackOnly();
                throw new JobException("失败！");
            }
        });
    }

    @Override
    public void changeCurrentOperator(Long currentUserId, Long changeToUserId, Long taskId) {
        Task task = taskMapper.selectById(taskId);
        User curUser = userService.getById(currentUserId);
        User changeToUser = userService.getById(changeToUserId);
        if(task == null)throw new JobException("当前任务已被删除！");
        if("close".equals(task.getTaskStatus()))throw new JobException("当前任务已关闭，不能转派！");
        if(curUser == null || changeToUser == null) throw new JobException("找不到用户，不能转派");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String now = formatter.format(LocalDateTime.now()) + " ";
        task.setCurrentOperator(changeToUser.getId());
        TaskOperationLog userChangeLog = null;
        if(!Objects.equals(currentUserId, changeToUserId)){
            userChangeLog = new TaskOperationLog();
            userChangeLog.setTaskId(task.getId());
            userChangeLog.setOperationLog(now + curUser.getUserName() + " 将任务转派给 " + changeToUser.getUserName());
        }
        String participant = task.getParticipant();
        Set<String> set = new HashSet<>();
        StringBuilder builder = new StringBuilder();
        if(StrUtil.isNotEmpty(participant)){
            for(String id:participant.split(",")){
                if(set.add(id))builder.append(id).append(",");
            }
        }else {
            if(set.add(currentUserId.toString()))builder.append(currentUserId).append(",");
        }
        if(set.add(changeToUserId.toString()))builder.append(changeToUserId).append(",");
        if(builder.length() > 0 && builder.toString().charAt(builder.length()-1) == ','){
            builder.deleteCharAt(builder.length()-1);
        }
        task.setParticipant(builder.toString());
        TaskOperationLog finalUserChangeLog = userChangeLog;
        transaction.execute(action -> {
            try {
                taskMapper.updateIgnoreNull(task);
                if(finalUserChangeLog != null)taskOperationLogService.save(finalUserChangeLog);
                return true;
            }catch (Exception e){
                action.setRollbackOnly();
                throw new JobException("操作失败，系统繁忙");
            }
        });
    }

    @Override
    public List<TaskCardVO> getTaskCardList(TaskPageVO params) {
        List<Task> tasks = taskMapper.selectListByQueryParams(params);
        TaskCardVO newCard = new TaskCardVO();
        TaskCardVO todoCard = new TaskCardVO();
        TaskCardVO doneCard = new TaskCardVO();
        TaskCardVO closeCard = new TaskCardVO();
        List<Task> newTaskList = new ArrayList<>();
        List<Task> todoTaskList = new ArrayList<>();
        List<Task> doneTaskList = new ArrayList<>();
        List<Task> closeTaskList = new ArrayList<>();
        newCard.setTitle("新创建 New");
        newCard.setTasks(newTaskList);
        todoCard.setTitle("进行中 Todo");
        todoCard.setTasks(todoTaskList);
        doneCard.setTitle("已完成 Done");
        doneCard.setTasks(doneTaskList);
        closeCard.setTitle("已关闭 Close");
        closeCard.setTasks(closeTaskList);
        tasks.forEach((task -> {
            switch (task.getTaskStatus()) {
                case "new":newTaskList.add(task);break;
                case "todo":todoTaskList.add(task);break;
                case "done":doneTaskList.add(task);break;
                case "close":closeTaskList.add(task);break;
            }
        }));
        return Arrays.asList(newCard, todoCard, doneCard, closeCard);
    }

    private String generateStatusOperationLog(String taskStatus, String taskName){
        if(StrUtil.isEmpty(taskStatus))return "";
        LoginUser loginUser = SecurityUtils.getLoginUser();
        String currentUserName = loginUser == null ? "" : loginUser.getUser().getUserName();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String now = formatter.format(LocalDateTime.now()) + " ";
        String status = "";
        switch (taskStatus){
            case "new": status = "新创建";break;
            case "todo": status = "进行中";break;
            case "done": status = "已完成";break;
            case "close": status = "已关闭";break;
            default:break;
        }
        return now + currentUserName + " 将任务: "+ taskName +" 的状态更改为 " + status;
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