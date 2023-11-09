package com.ptu.devCloud.entity;


import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.EqualsAndHashCode;

/**
 * 权限表 实体类
 * @author yang fan
 * @since 2023-11-09 10:12:44
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "permission")
public class Permission extends BaseEntity implements Serializable {

	/** 权限名称 */
	@TableField(value = "permission_name")
	private String permissionName;
    
	/** 父ID */
	@TableField(value = "parent_id")
	private Long parentId;
    
	/** 显示顺序 */
	@TableField(value = "order_num")
	private Integer orderNum;
    
	/** 路由地址 */
	@TableField(value = "path")
	private String path;
    
	/** 组件路径 */
	@TableField(value = "component")
	private String component;
    
	/** 权限类型（M菜单 F按钮） */
	@TableField(value = "permission_type")
	private String permissionType;
    
	/** 显示状态:显示(1/true), 隐藏(0/false) */
	@TableField(value = "visible")
	private Boolean visible;
    
	/** 权限状态:启用(1/true), 禁用(0/false) */
	@TableField(value = "status")
	private Boolean status;
    
	/** 权限表达式 */
	@TableField(value = "perms")
	private String perms;
    
	/** 菜单图标 */
	@TableField(value = "icon")
	private String icon;
    
	/** 备注 */
	@TableField(value = "remark")
	private String remark;

}