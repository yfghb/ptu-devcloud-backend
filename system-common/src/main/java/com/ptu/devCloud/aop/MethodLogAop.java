package com.ptu.devCloud.aop;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.ptu.devCloud.annotation.EnableMethodLog;
import com.ptu.devCloud.entity.MethodLog;
import com.ptu.devCloud.service.MethodLogService;
import com.ptu.devCloud.entity.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;

/**
 * 给 controller 包下的所有方法记录日志
 * @author Yang Fan
 * @since 2023/9/30 15:14
 */
@Slf4j
@Aspect
@Component
public class MethodLogAop {

    @Resource
    private MethodLogService methodLogService;

    // 扫描所有的controller包下的所有类的所有方法
    @Pointcut("@annotation(com.ptu.devCloud.annotation.EnableMethodLog)")
    public void controllerPointCut(){}

    @Around("controllerPointCut()")
    public Object methodLog(ProceedingJoinPoint joinPoint) {
        Object result = null;
        MethodLog methodLog = new MethodLog();

        // 获取本次请求的request
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            // 此次请求的url
            methodLog.setUrl(requestAttributes.getRequest().getRequestURL().toString());
        }
        // 方法全路径
        methodLog.setMethodPath(joinPoint.getSignature().getDeclaringTypeName());
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String methodName = signature.getMethod().getAnnotation(EnableMethodLog.class).name();
        // 方法名
        methodLog.setMethodName((StringUtils.checkValNull(methodName)) ? joinPoint.getSignature().getName() : methodName);
        // 请求入参
        methodLog.setParamsJson(JSON.toJSONString(joinPoint.getArgs()));
        long startTime = System.currentTimeMillis();
        try{
            result = joinPoint.proceed();
        }
        catch (Throwable e) {
            // 方法错误信息
            methodLog.setErrorMsg(e.toString());
            log.error(methodLog.getMethodName() + "\n" + methodLog.getMethodPath() + "\n" + e);
            // 方法异常标志
            methodLog.setPassFlag(false);
            result = CommonResult.error(e.toString());
        }
        finally {
            long endTime = System.currentTimeMillis();
            // 方法耗时（秒）（保留两位小数）
            methodLog.setConsumeTime(String.format("%.2f", (double) (endTime - startTime) / 1000));
            // 方法出参
            methodLog.setResultJson(JSON.toJSONString(result));
            methodLogService.insertIgnoreNull(methodLog);
        }

        return result;
    }
}
