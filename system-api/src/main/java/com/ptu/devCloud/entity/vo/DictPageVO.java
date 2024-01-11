package com.ptu.devCloud.entity.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * DictPageVO
 * @author Yang Fan
 * @since 2024/1/11 10:03
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DictPageVO extends PageVO implements Serializable {

    /**
     * 字典名称
     */
    private String dictName;

    /**
     * 字典key
     */
    private String dictCode;

    /**
     * 启用/禁用
     */
    private Boolean status;
}
