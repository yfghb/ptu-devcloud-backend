package com.ptu.devCloud.service.impl;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ptu.devCloud.entity.TableSequence;
import com.ptu.devCloud.exception.JobException;
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


    @Override
    public Long generateId(String seqName) {
        Long id = null;
        if (StrUtil.isNotEmpty(seqName)) {
            LambdaQueryWrapper<TableSequence> lqw = new LambdaQueryWrapper<>();
            lqw.eq(TableSequence::getSequenceName, seqName);
            // 根据序列名查询
            TableSequence tableSequence = tableSequenceMapper.selectOne(lqw);
            if(!tryGenerate(tableSequence, seqName)){
                // 重试一次
                tableSequence = tableSequenceMapper.selectOne(lqw);
                if(!tryGenerate(tableSequence, seqName)){
                    throw new JobException(seqName + "生成主键失败!");
                }
            }
            id = tableSequence.getTableId();
        }
        return id;
    }

    private boolean tryGenerate(TableSequence tableSequence, String seqName){
        if (tableSequence == null) return false;
        // 设置乐观锁
        String version = UUID.fastUUID().toString(true);
        tableSequence.setVersion(version);
        try{
            tableSequenceMapper.update(tableSequence);
            // 更新对应表主键id
            return tableSequenceMapper.changeId(seqName, version);
        }catch (Exception e){
            log.warn(seqName + "生成主键失败, 即将重试");
            return false;
        }
    }

}