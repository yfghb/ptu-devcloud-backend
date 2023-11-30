package com.ptu.devCloud.filter;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.ptu.devCloud.constants.HttpCodeConstants;
import com.ptu.devCloud.entity.CommonResult;
import com.ptu.devCloud.entity.LoginUser;
import com.ptu.devCloud.utils.JwtUtil;
import com.ptu.devCloud.utils.SecurityUtils;
import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 校验token的过滤器, 使用OncePerRequestFilter保证一次请求只经过一次filter
 * @author Yang Fan
 * @since 2023/11/23 11:03
 */
@Component
public class TokenFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");
        if(StrUtil.isEmpty(token)){
            String url = request.getRequestURL().toString();
            // 忽略登录和注册接口
            if(url.contains("/UserController/login") || url.contains("/UserController/add")){
                filterChain.doFilter(request, response);
                return;
            }
            CommonResult<String> result = CommonResult.error("登录认证过期");
            result.setCode(HttpCodeConstants.NEED_LOGIN);
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(JSON.toJSONString(result));
        }
        else {
            // TODO 校验token是否正确
            Claims claims = JwtUtil.parse(token);
            LoginUser loginUser = JSON.parseObject(claims.getSubject(), LoginUser.class);
            UsernamePasswordAuthenticationToken authenticationToken = new
                    UsernamePasswordAuthenticationToken(loginUser, null, null);
            if(SecurityUtils.getAuthentication() == null){
                SecurityUtils.setAuthentication(authenticationToken);
            }
            filterChain.doFilter(request, response);
        }
    }

}
