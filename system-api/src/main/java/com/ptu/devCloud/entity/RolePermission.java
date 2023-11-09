package com.ptu.devCloud.entity;


import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.EqualsAndHashCode;

/**
 * 角色权限关联表 实体类
 * @author yang fan
 * @since 2023-11-09 10:12:44
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "role_permission")
public class RolePermission extends BaseEntity implements Serializable {

	/** 角色id */
	@TableField(value = "role_id")
	private Long roleId;
    
	/** 权限id */
	@TableField(value = "permission_id")
	private Long permissionId;

}