package com.ptu.devCloud.filter;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.ptu.devCloud.constants.HttpCodeConstants;
import com.ptu.devCloud.entity.LoginUser;
import com.ptu.devCloud.utils.JwtUtil;
import com.ptu.devCloud.utils.RedisUtils;
import com.ptu.devCloud.utils.SecurityUtils;
import com.ptu.devCloud.utils.WebUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 校验token的过滤器, 使用OncePerRequestFilter使一次请求只经过一次filter
 * @author Yang Fan
 * @since 2023/11/23 11:03
 */
@Component
public class TokenFilter extends OncePerRequestFilter {

    @Resource
    private RedisUtils redisUtils;

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
            WebUtils.returnFalse(response, "登录认证过期, 请重新登录", HttpCodeConstants.NEED_LOGIN);
        }
        else {
            // 在redis获取用户令牌信息
            String userRedisToken = (String) redisUtils.get(token);
            if(StrUtil.isEmpty(userRedisToken)){
                WebUtils.returnFalse(response, "登录认证过期, 请重新登录", HttpCodeConstants.NEED_LOGIN);
            } else {
                try{
                    Claims claims = JwtUtil.parse(userRedisToken);
                    LoginUser loginUser = JSON.parseObject(claims.getSubject(), LoginUser.class);
                    UsernamePasswordAuthenticationToken authenticationToken = new
                            UsernamePasswordAuthenticationToken(loginUser, null, null);
                    SecurityUtils.setAuthentication(authenticationToken);
                    filterChain.doFilter(request, response);
                }catch (ExpiredJwtException e) {
                    redisUtils.del(token);
                    WebUtils.returnFalse(response, "登录认证过期, 请重新登录", HttpCodeConstants.NEED_LOGIN);
                }
            }
        }
    }

}
