package com.ptu.devCloud.entity.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * RolePageVO
 * @author Yang Fan
 * @since 2024/1/5 15:20
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RolePageVO extends PageVO implements Serializable {
    /**
     * 角色名称
     */
    private String roleName;
    /**
     * 是否有效
     */
    private Boolean status;
}
