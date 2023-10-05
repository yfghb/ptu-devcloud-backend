package com.ptu.devCloud.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 跨越请求过滤器
 * @author Yang Fan
 * @since 2023/10/5 15:00
 */
@WebFilter(filterName = "CORSFilter", urlPatterns = "/*")
@Component
@Slf4j
public class CORSFilter implements Filter {

    @Value("${devCloud.crossOrigin}")
    private String crossOrigin;

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse) res;
        String originHeader = request.getHeader("Origin");
        // 检查请求来源是否在白名单内
        if(checkCrossOriginOK(originHeader)) {
            response.setHeader("Access-Control-Allow-Origin", originHeader);
        }
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Origin, No-Cache, X-Requested-With, If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires, Content-Type, X-E4M-With,userId,token,authorization");
        response.setHeader("Access-Control-Allow-Credentials", "true");


        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig arg0) {

    }

    private boolean checkCrossOriginOK(String originHeader) {
        String[] whiteList = crossOrigin.split(",");
        for(String url:whiteList){
            if(url.equals(originHeader)) {
                return true;
            }
        }
        return false;
    }

}
