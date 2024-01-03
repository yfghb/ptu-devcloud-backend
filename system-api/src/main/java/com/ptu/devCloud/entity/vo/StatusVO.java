package com.ptu.devCloud.entity.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * StatusVO
 * @author Yang Fan
 * @since 2024/1/3 19:43
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class StatusVO extends IdsVO implements Serializable {
    /**
     * 状态
     */
    private Boolean status;
}
