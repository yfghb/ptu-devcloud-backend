package com.ptu.devCloud.entity.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * LinkTaskVO
 * @author Yang Fan
 * @since 2024/2/1 13:27
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class LinkTaskVO extends IdsVO implements Serializable {
    /**
     * 任务编号
     */
    private String serialNumber;
}
