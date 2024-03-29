package com.ptu.devCloud.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ptu.devCloud.entity.Notification;
import com.ptu.devCloud.entity.vo.NotificationPageVO;
import com.ptu.devCloud.mapper.NotificationMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.ptu.devCloud.service.NotificationService;
import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

import com.ptu.devCloud.annotation.SeqName;
import com.ptu.devCloud.constants.TableSequenceConstants;

/**
 * NotificationServiceImpl
 * @author yang fan
 * @since 2024-02-13 15:29:36
 */
@Service
public class NotificationServiceImpl extends ServiceImpl<NotificationMapper, Notification> implements NotificationService{

    @Resource
    private NotificationMapper notificationMapper;

    @Override
    @SeqName(value = TableSequenceConstants.Notification)
    public boolean saveBatch(Collection<Notification> entityList) {
        return super.saveBatch(entityList);
    }


    @Override
    public void addNotification(Notification notification) {
        if(notification != null){
            notificationMapper.insertIgnoreNull(notification);
        }
    }

    @Override
    public PageInfo<Notification> page(NotificationPageVO pageVO) {
        if(pageVO == null || pageVO.getCurrent() == null || pageVO.getPageSize() == null) {
            return new PageInfo<>();
        }
        PageHelper.startPage(pageVO.getCurrent(), pageVO.getPageSize());
        List<Notification> list = notificationMapper.selectListByQueryParams(pageVO);
        return new PageInfo<>(list);
    }
}