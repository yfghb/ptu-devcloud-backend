package com.ptu.devCloud.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import com.ptu.devCloud.service.NotificationService;
import javax.annotation.Resource;

/**
 * NotificationController
 * @author yang fan
 * @since 2024-02-13 15:29:36
 */
@RestController
@RequestMapping("/NotificationController")
public class NotificationController {

    @Resource
    private NotificationService notificationService;


    
}