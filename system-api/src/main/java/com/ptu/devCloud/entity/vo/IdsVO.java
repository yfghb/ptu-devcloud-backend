package com.ptu.devCloud.entity.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * id列表VO对象
 * @author Yang Fan
 * @since 2024/1/3 18:02
 */
@Data
public class IdsVO implements Serializable {
    /**
     * 用户id列表
     */
    private List<String> userIds;
    /**
     * 角色id列表
     */
    private List<String> roleIds;
}
