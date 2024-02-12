package com.ptu.devCloud.service.impl;

import com.ptu.devCloud.annotation.SeqName;
import com.ptu.devCloud.constants.TableSequenceConstants;
import com.ptu.devCloud.entity.DictItem;
import com.ptu.devCloud.mapper.DictItemMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.ptu.devCloud.service.DictItemService;
import javax.annotation.Resource;
import java.util.Collection;

/**
 * DictItemServiceImpl
 * @author yang fan
 * @since 2024-01-10 12:17:51
 */
@Service
public class DictItemServiceImpl extends ServiceImpl<DictItemMapper, DictItem> implements DictItemService{

    @Resource
    private DictItemMapper dictItemMapper;
    


    @Override
    @SeqName(value = TableSequenceConstants.DictItem)
    public boolean saveBatch(Collection<DictItem> entityList) {
        return super.saveBatch(entityList);
    }


	
}