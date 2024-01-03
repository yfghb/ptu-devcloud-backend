package com.ptu.devCloud.config;


import com.ptu.devCloud.filter.TokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

/**
 * @author Yang Fan
 * @since 2023/5/27 15:02
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Resource
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Resource
    private AccessDeniedHandler accessDeniedHandler;

    @Resource
    private TokenFilter tokenFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            //关闭csrf
            .csrf().disable()
            //不通过Session获取SecurityContext
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
            // 对于登录,注册 允许匿名访问
            .antMatchers("/UserController/login").anonymous()
            .antMatchers("/UserController/add").anonymous()
            // 其余请求需要认证才能访问
            .anyRequest().authenticated()
            .and()
            //关闭默认的注销功能
            .logout().disable()
            //配置异常处理器
            .exceptionHandling()
            .authenticationEntryPoint(authenticationEntryPoint)
            .accessDeniedHandler(accessDeniedHandler)
            .and()
            .addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class)
            //允许跨域
            .cors();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}