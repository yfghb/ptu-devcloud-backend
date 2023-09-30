package com.ptu.devCloud.service.impl;

import com.ptu.devCloud.entity.MethodLog;
import com.ptu.devCloud.mapper.MethodLogMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.ptu.devCloud.service.MethodLogService;
import javax.annotation.Resource;

/**
 * MethodLogServiceImpl
 * @author yang fan
 * @since 2023-09-30 16:39:35
 */
@Service
public class MethodLogServiceImpl extends ServiceImpl<MethodLogMapper, MethodLog> implements MethodLogService{

    @Resource
    private MethodLogMapper methodLogMapper;


	
}