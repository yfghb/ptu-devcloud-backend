package com.ptu.devCloud.service.impl;

import com.ptu.devCloud.annotation.SeqName;
import com.ptu.devCloud.constants.TableSequenceConstants;
import com.ptu.devCloud.entity.Dict;
import com.ptu.devCloud.mapper.DictMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.ptu.devCloud.service.DictService;
import javax.annotation.Resource;
import java.util.Collection;

/**
 * DictServiceImpl
 * @author yang fan
 * @since 2024-01-10 12:17:51
 */
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService{

    @Resource
    private DictMapper dictMapper;
    
    @Override
    @SeqName(value = TableSequenceConstants.Dict)
    public boolean save(Dict entity) {
        return super.save(entity);
    }

    @Override
    @SeqName(value = TableSequenceConstants.Dict)
    public boolean saveBatch(Collection<Dict> entityList) {
        return super.saveBatch(entityList);
    }


	
}