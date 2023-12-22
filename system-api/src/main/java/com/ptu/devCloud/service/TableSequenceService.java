package com.ptu.devCloud.service;

import com.ptu.devCloud.entity.TableSequence;
import com.baomidou.mybatisplus.extension.service.IService;


/**
 * TableSequenceService
 * @author yang fan
 * @since 2023-09-30 16:37:06
 */
public interface TableSequenceService extends IService<TableSequence> {

    /**
     * 以序列名生成id
     * @author Yang Fan
     * @since 2023/10/1 15:18
     * @param seqName 序列名
     * @return id 或者 null
     */
    Long generateId(String seqName);
}