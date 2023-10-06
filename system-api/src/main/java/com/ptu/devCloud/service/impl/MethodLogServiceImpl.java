package com.ptu.devCloud.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ptu.devCloud.entity.MethodLog;
import com.ptu.devCloud.entity.vo.MethodLogPageVO;
import com.ptu.devCloud.mapper.MethodLogMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.ptu.devCloud.service.MethodLogService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * MethodLogServiceImpl
 * @author yang fan
 * @since 2023-09-30 16:39:35
 */
@Service
public class MethodLogServiceImpl extends ServiceImpl<MethodLogMapper, MethodLog> implements MethodLogService{

    @Resource
    private MethodLogMapper methodLogMapper;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertIgnoreNull(MethodLog methodLog) {
        methodLogMapper.insertIgnoreNull(methodLog);
    }

    @Override
    public PageInfo<MethodLog> getPage(MethodLogPageVO pageVO) {
        if(pageVO == null || pageVO.getCurrent() == null || pageVO.getPageSize() == null) {
            return new PageInfo<>();
        }
        PageHelper.startPage(pageVO.getCurrent(), pageVO.getPageSize());
        List<MethodLog> list = methodLogMapper.listAll();
        return new PageInfo<>(list);
    }
}