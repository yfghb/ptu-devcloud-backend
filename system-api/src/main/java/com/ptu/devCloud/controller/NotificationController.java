package com.ptu.devCloud.controller;

import com.github.pagehelper.PageInfo;
import com.ptu.devCloud.entity.CommonResult;
import com.ptu.devCloud.entity.Notification;
import com.ptu.devCloud.entity.vo.NotificationPageVO;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("@permissionServiceImpl.hasPermission('ignorePermission')")
    public CommonResult<String> add(@RequestBody Notification notification){
        notificationService.addNotification(notification);
        return CommonResult.successNoMsg("成功");
    }

    /**
     * 分页查询
     * @author Yang Fan
     * @since 2024/2/14 14:46
     * @param pageVO NotificationPageVO
     * @return CommonResult<PageInfo<Notification>>
     */
    @PostMapping("/page")
    @PreAuthorize("@permissionServiceImpl.hasPermission('ignorePermission')")
    public CommonResult<PageInfo<Notification>> page(@RequestBody NotificationPageVO pageVO){
        return CommonResult.successNoMsg(notificationService.page(pageVO));
    }

    /**
     * 以id修改
     * @author Yang Fan
     * @since 2024/2/14 16:27
     * @param notification Notification
     * @return CommonResult<String>
     */
    @PostMapping("/update")
    @PreAuthorize("@permissionServiceImpl.hasPermission('ignorePermission')")
    public CommonResult<String> updateById(@RequestBody Notification notification){
        if(notification!=null) notificationService.updateById(notification);
        return CommonResult.successNoMsg("成功");
    }
    
}