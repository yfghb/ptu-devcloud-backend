package com.ptu.devCloud.service;

import com.ptu.devCloud.entity.Notification;
import com.baomidou.mybatisplus.extension.service.IService;


/**
 * NotificationService
 * @author yang fan
 * @since 2024-02-13 15:29:36
 */
public interface NotificationService extends IService<Notification> {

	/**
	 * 添加通知
	 * @author Yang Fan
	 * @since 2024/2/13 15:55
	 * @param notification Notification
	 */
	void addNotification(Notification notification);
}