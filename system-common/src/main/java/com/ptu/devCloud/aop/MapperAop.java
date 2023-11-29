package com.ptu.devCloud.aop;

import cn.hutool.core.util.StrUtil;
import com.ptu.devCloud.annotation.SeqName;
import com.ptu.devCloud.entity.BaseEntity;
import com.ptu.devCloud.service.TableSequenceService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
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

    @Resource
    private TableSequenceService tableSequenceService;

    @Before("insertPointCut()")
    public void beforeInsert(JoinPoint joinPoint) {
        // 获取入参
        Object entity = joinPoint.getArgs()[0];
        if (entity == null) {
            return;
        }
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        // 获得序列名
        String seqName = signature.getMethod().getAnnotation(SeqName.class).value();
        if (StrUtil.isEmpty(seqName)) {
            log.error(joinPoint.getSignature().getDeclaringTypeName() + "\n找不到序列名，无法生成主键！");
        }
        else {
            if (entity instanceof List) {
                List<Object> list = (List<Object>) entity;
                for(Object item : list) {
                    // 填充公共字段
                    generatePublicField(item, seqName);
                }
            }
            else {
                generatePublicField(entity, seqName);
            }

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

    private void generatePublicField(Object obj, String seqName) {
        Long id = tableSequenceService.generateId(seqName);
        if (obj instanceof BaseEntity) {
            if (id != null) {
                try {
                    BaseEntity baseEntity = (BaseEntity) obj;
                    if(baseEntity.getId() == null){
                        baseEntity.setId(id);
                    }
                    // todo 设置创建人
                    baseEntity.setCreateBy(0L);
                    baseEntity.setCreateDate(new Date());
                }
                catch (Exception e) {
                    log.error("MapperAop: 填充公共字段异常！\n" + e);
                }
            }
            else if(seqName.equals("SEQ_TABLE_SEQUENCE")) {
                try {
                    BaseEntity baseEntity = (BaseEntity) obj;
                    baseEntity.setCreateBy(0L);
                    baseEntity.setCreateDate(new Date());
                }
                catch (Exception e) {
                    log.error("MapperAop: 填充公共字段异常！\n" + e);
                }
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
