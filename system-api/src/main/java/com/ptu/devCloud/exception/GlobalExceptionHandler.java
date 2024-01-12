package com.ptu.devCloud.exception;

import cn.hutool.core.lang.UUID;
import com.ptu.devCloud.constants.HttpCodeConstants;
import com.ptu.devCloud.entity.CommonResult;
import com.ptu.devCloud.utils.WebUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 全局异常处理
 * @author Yang Fan
 * @since 2023/12/22 19:44
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler implements AuthenticationEntryPoint, AccessDeniedHandler {

    @ExceptionHandler(value = Exception.class)
    public CommonResult<String> exceptionHandler(Exception e) {
        String uuid = printErr(e, "error");
        return CommonResult.error("系统异常, 异常号: " + uuid + e.getMessage());
    }

    @ExceptionHandler(value = JobException.class)
    public CommonResult<String> jobExceptionHandler(JobException e) {
        printErr(e, "warn");
        return CommonResult.error(e.getMessage());
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) {
        log.warn("AccessDenied! "+accessDeniedException.getMessage());
        CommonResult<String> result = CommonResult.error("您没有当前操作的权限", HttpCodeConstants.ACCESS_DENIED);
        WebUtils.render(response, HttpCodeConstants.SUCCESS, result);
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) {
        log.warn(authException.getMessage());
        CommonResult<String> result = null;
        if(authException instanceof BadCredentialsException){
            result = CommonResult.error("用户名或密码错误", HttpCodeConstants.NEED_LOGIN);
        }else if(authException instanceof InsufficientAuthenticationException){
            result = CommonResult.error("拒绝访问, 原因: 权限不足, 或登录认证过期", HttpCodeConstants.ACCESS_DENIED);
        }
        WebUtils.render(response, HttpCodeConstants.SUCCESS, result);
    }

    private String printErr(Exception e, String type) {
        String uuid = UUID.fastUUID().toString(true);
        String basicMsg = "--------------------" + (type.equals("error") ? "系统异常" : "业务异常") + "--------------------" +
                "\n异常号: " + uuid + "\n基本信息: " + e.toString() + "\n栈信息:";
        switch (type) {
            case "error":
                log.error(basicMsg);
                break;
            case "warn":
            default:
                log.warn(basicMsg);
        }
        e.printStackTrace();
        return uuid;
    }
}
