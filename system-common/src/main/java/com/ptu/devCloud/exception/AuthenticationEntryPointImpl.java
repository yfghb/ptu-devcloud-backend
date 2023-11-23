package com.ptu.devCloud.exception;

import com.alibaba.fastjson.JSON;
import com.ptu.devCloud.constants.HttpCodeConstants;
import com.ptu.devCloud.entity.CommonResult;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Yang Fan
 * @since 2023/11/22 17:15
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        authException.printStackTrace();

        CommonResult<String> result = null;
        if(authException instanceof BadCredentialsException){
            result = CommonResult.error("用户名或密码错误");
            result.setCode(HttpCodeConstants.NEED_LOGIN);
        }else if(authException instanceof InsufficientAuthenticationException){
            result = CommonResult.error("拒绝访问, 原因: 权限不足, 或登录认证过期");
            result.setCode(HttpCodeConstants.ACCESS_DENIED);
        }
        response.setStatus(HttpCodeConstants.SUCCESS);
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.getWriter().print(JSON.toJSONString(result));
    }
}
