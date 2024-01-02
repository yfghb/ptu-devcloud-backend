package com.ptu.devCloud;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Slf4j
@SpringBootApplication
@EnableTransactionManagement
@EnableAspectJAutoProxy(exposeProxy=true,proxyTargetClass=true)
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        log.info("启动成功!!!");
    }
}
