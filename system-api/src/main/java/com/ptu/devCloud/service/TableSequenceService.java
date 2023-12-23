package com.ptu.devCloud.service;

import com.ptu.devCloud.entity.TableSequence;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


/**
 * TableSequenceService
 * @author yang fan
 * @since 2023-09-30 16:37:06
 */
public interface TableSequenceService extends IService<TableSequence> {

    /**
     * 以序列名生成单个id
     * @author Yang Fan
     * @since 2023/10/1 15:18
     * @param seqName 序列名
     * @return id 或者 null
     */
    Long generateId(String seqName);

    /**
     * 以序列名批量生成id
     * @author Yang Fan
     * @since 2023/12/23 12:16
     * @param seqName 序列名
     * @return List<Long>
     */
    List<Long> generateIdBatch(String seqName, Integer size);
}