package com.ptu.devCloud.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 给 controller 包下的所有方法记录日志
 * @author Yang Fan
 * @since 2023/9/30 15:14
 */
@Slf4j
@Aspect
@Component
public class MethodLogAop {

    // 扫描所有的controller包下的所有方法
    @Pointcut("execution(* com.ptu.devCloud.controller..*.*(..))")
    public void controllerPointCut(){}

    @Around("controllerPointCut()")
    public Object methodLog(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result;
        // TODO before..
        result = joinPoint.proceed();
        // TODO after..
        return result;
    }
}
