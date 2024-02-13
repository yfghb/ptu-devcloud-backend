package com.ptu.devCloud.controller;

import com.ptu.devCloud.entity.CommonResult;
import com.ptu.devCloud.entity.Notification;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    /**
     * 添加通知
     * @author Yang Fan
     * @since 2024/2/13 15:55
     * @param notification Notification
     * @return CommonResult<String>
     */
    @PostMapping("/add")
    public CommonResult<String> add(@RequestBody Notification notification){
        notificationService.addNotification(notification);
        return CommonResult.successNoMsg("成功");
    }
    
}