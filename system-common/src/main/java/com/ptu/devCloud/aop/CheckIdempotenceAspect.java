package com.ptu.devCloud.aop;


import com.ptu.devCloud.entity.CommonResult;
import com.ptu.devCloud.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


/**
 * 幂等性校验切面
 * @author Yang Fan
 * @since 2024/1/22 13:19
 */
@Slf4j
@Aspect
@Component
public class CheckIdempotenceAspect {

    @Resource
    private RedisUtils redisUtils;

    @Pointcut("@annotation(com.ptu.devCloud.annotation.CheckIdempotence)")
    public void idempotencePointCut(){}

    @Around("idempotencePointCut()")
    public Object idempotence(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取本次请求的request
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if(servletRequestAttributes != null){
            HttpServletRequest request = servletRequestAttributes.getRequest();
            String idempotenceToken = request.getHeader("idempotence-token");
            try {
                // 删除idempotenceToken
                Boolean del = redisUtils.delWithReturn(idempotenceToken);
                if(!del){
                    return CommonResult.error("系统繁忙，请不要重复点击");
                }
            }catch (Throwable e){
                log.error("出现了无法自行处理的异常，幂等性校验将跳过！msg="+e.getMessage());
            }
        }else {
            log.warn("方法名: {} 获取到的HttpServletRequest为null! 幂等性校验将跳过!", joinPoint.getSignature().getDeclaringTypeName());
        }
        return joinPoint.proceed();

    }
}
