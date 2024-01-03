package com.ptu.devCloud.entity.vo;

import com.ptu.devCloud.entity.Role;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * 添加角色接口的VO对象
 * @author Yang Fan
 * @since 2023/12/22 16:24
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RoleVO extends Role implements Serializable {
    /**
     * 前端列表key值 (id)
     */
    private String key;
    /**
     * 权限id列表
     */
    private List<String> permissionIdList;
}
