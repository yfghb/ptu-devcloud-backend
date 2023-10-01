package com.ptu.devCloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.ptu.devCloud.entity.TableSequence;
import com.ptu.devCloud.mapper.TableSequenceMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.ptu.devCloud.service.TableSequenceService;
import org.springframework.transaction.annotation.Transactional;

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


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long generateId(String seqName) {
        Long id = null;
        if (StringUtils.checkValNotNull(seqName) && !seqName.equals("SEQ_TABLE_SEQUENCE")) {
            LambdaQueryWrapper<TableSequence> lqw = new LambdaQueryWrapper<>();
            lqw.eq(TableSequence::getSequenceName, seqName);
            // 根据序列名查询
            TableSequence tableSequence = tableSequenceMapper.selectOne(lqw);
            if(tableSequence != null) {
                id = tableSequence.getTableId();
                // 更新对应表主键id
                tableSequenceMapper.changeId(seqName);
            }
        }
        return id;
    }
}