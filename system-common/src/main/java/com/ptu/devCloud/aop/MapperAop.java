package com.ptu.devCloud.aop;


import com.ptu.devCloud.entity.BaseEntity;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import java.util.Collection;
import java.util.Date;
import java.util.List;


/**
 * mapper切面，为insert，update方法填充公共字段
 * @author Yang Fan
 * @since 2023/10/1 14:15
 */
@Slf4j
@Aspect
@Component
public class MapperAop {

    @Pointcut("execution(* com.ptu.devCloud.mapper..*Mapper.insert*(..))")
    public void insertPointCut(){}

    @Pointcut("execution(* com.ptu.devCloud.mapper..*Mapper.update*(..))")
    public void updatePointCut(){}


    @Before("insertPointCut()")
    public void beforeInsert(JoinPoint joinPoint) {
        // 获取入参
        Object entity = joinPoint.getArgs()[0];
        if (entity == null) {
            return;
        }
        // 填充公共字段
        if (entity instanceof List) {
            List<Object> list = (List<Object>) entity;
            for(Object item : list) {
                generatePublicField(item);
            }
        }
        else {
            generatePublicField(entity);
        }

    }

    @Before("updatePointCut()")
    public void beforeUpdate(JoinPoint joinPoint) {
        Object entity = joinPoint.getArgs()[0];
        if (entity == null) {
            return;
        }
        if(entity instanceof List){
            Collection<Object> list = (Collection<Object>) entity;
            for(Object item : list) {
                updatePublicField(item);
            }
        }else {
            updatePublicField(entity);
        }

    }

    private void generatePublicField(Object obj) {
        if (obj instanceof BaseEntity) {
            try {
                BaseEntity baseEntity = (BaseEntity) obj;
                // todo 设置创建人
                baseEntity.setCreateBy(0L);
                baseEntity.setCreateDate(new Date());
            }
            catch (Exception e) {
                log.error("MapperAop: 填充公共字段异常！\n" + e);
            }
        }
    }

    private void updatePublicField(Object obj) {
        if (obj instanceof BaseEntity) {
            try{
                BaseEntity baseEntity = (BaseEntity) obj;
                baseEntity.setUpdateDate(new Date());
                // todo 设置修改人
                baseEntity.setUpdateBy(100L);
            }
            catch (Exception e) {
                log.error("MapperAop: 更新公共字段异常！\n" + e);
            }
        }
    }
}
