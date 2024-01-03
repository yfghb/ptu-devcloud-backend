package com.ptu.devCloud.aop;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.ptu.devCloud.annotation.SeqName;
import com.ptu.devCloud.entity.BaseEntity;
import com.ptu.devCloud.exception.JobException;
import com.ptu.devCloud.service.TableSequenceService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;


/**
 * mapper, service切面，为insert，update方法填充公共字段
 * @author Yang Fan
 * @since 2023/10/1 14:15
 */
@Slf4j
@Aspect
@Component
public class InsertOrUpdateAspect {

    @Pointcut("@annotation(com.ptu.devCloud.annotation.SeqName)")
    public void insertPointCut(){}

    @Pointcut("execution(* com.ptu.devCloud.mapper..*Mapper.update*(..))" +
            "|| execution(* com.baomidou.mybatisplus.extension.service.IService.update*(..))")
    public void updatePointCut(){}

    @Resource
    private TableSequenceService tableSequenceService;

    @Before("insertPointCut()")
    public void beforeInsert(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        // 获得序列名
        String seqName = signature.getMethod().getAnnotation(SeqName.class).value();
        if (StrUtil.isEmpty(seqName)) {
            log.warn(joinPoint.getSignature().getDeclaringTypeName() + "\n序列名为空，无法生成主键！");
        }
        else {
            // 获取入参, 为参数表每一个可能是baseEntity实例的对象生成主键
            Object[] args = joinPoint.getArgs();
            for (Object entity:args){
                if (entity instanceof Collection) {
                    Collection<?> list = (Collection<?>) entity;
                    generatePublicFieldBatch(list, seqName);
                }
                else {
                    generatePublicField(entity, seqName);
                }
            }
        }
    }

    @Before("updatePointCut()")
    public void beforeUpdate(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        for(Object entity:args){
            if(entity instanceof Collection){
                Collection<?> list = (Collection<?>) entity;
                for(Object item : list) {
                    updatePublicField(item);
                }
            }else {
                updatePublicField(entity);
            }
        }
    }

    private void generatePublicField(Object obj, String seqName) {
        if (obj instanceof BaseEntity) {
            BaseEntity baseEntity = (BaseEntity) obj;
            if(baseEntity.getId() == null){
                baseEntity.setId(tableSequenceService.generateId(seqName));
            }
            // todo 设置创建人
            if(baseEntity.getCreateBy() == null) {
                baseEntity.setCreateBy(0L);
            }
            baseEntity.setCreateDate(new Date());
        }
    }

    /**
     * 使用此方法避免for循环生成主键速度过慢的问题
     * 10000条数据插入: 2.3s左右
     */
    private void generatePublicFieldBatch(Collection<?> collection, String seqName){
        if(CollUtil.isEmpty(collection))return;
        List<BaseEntity> list = new ArrayList<>();
        for(Object obj:collection){
            if(obj instanceof BaseEntity){
                BaseEntity baseEntity = (BaseEntity) obj;
                if(baseEntity.getCreateBy() == null) {
                    // todo 设置创建人
                    baseEntity.setCreateBy(0L);
                }
                baseEntity.setCreateDate(new Date());
                list.add(baseEntity);
            }
        }
        List<Long> idList = tableSequenceService.generateIdBatch(seqName, list.size());
        for(int i=0;i<idList.size();i++){
            list.get(i).setId(idList.get(i));
        }
    }

    private void updatePublicField(Object obj) {
        if (obj instanceof BaseEntity) {
            try{
                BaseEntity baseEntity = (BaseEntity) obj;
                baseEntity.setUpdateDate(new Date());
                // todo 设置修改人
                if (baseEntity.getUpdateBy() == null) {
                    baseEntity.setUpdateBy(100L);
                }
            }
            catch (Exception e) {
                throw new JobException("InsertOrUpdateAspect: 更新公共字段异常！\n" + e.getMessage());
            }
        }
    }
}
