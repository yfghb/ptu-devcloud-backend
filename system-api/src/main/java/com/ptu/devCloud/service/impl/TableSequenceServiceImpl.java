package com.ptu.devCloud.service.impl;

import com.ptu.devCloud.entity.TableSequence;
import com.ptu.devCloud.mapper.TableSequenceMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.ptu.devCloud.service.TableSequenceService;
import javax.annotation.Resource;

/**
 * TableSequenceServiceImpl
 * @author yang fan
 * @since 2023-09-30 16:39:35
 */
@Service
public class TableSequenceServiceImpl extends ServiceImpl<TableSequenceMapper, TableSequence> implements TableSequenceService{

    @Resource
    private TableSequenceMapper tableSequenceMapper;


	
}