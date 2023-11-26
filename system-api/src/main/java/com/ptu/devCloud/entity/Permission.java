package com.ptu.devCloud.entity;


import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.List;
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
	@TableField(value = "name")
	private String name;
    
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
    
	/** 权限类型（M菜单 B按钮） */
	@TableField(value = "permission_type")
	private String permissionType;
    
	/** 显示状态:显示(1/true), 隐藏(0/false) */
	@TableField(value = "render_menu")
	private Boolean renderMenu;
    
	/** 权限状态:启用(1/true), 禁用(0/false) */
	@TableField(value = "status")
	private Boolean status;
    
	/** 权限表达式 */
	@TableField(value = "permission")
	private String permission;
    
	/** 菜单图标 */
	@TableField(value = "icon")
	private String icon;
    
	/** 备注 */
	@TableField(value = "remark")
	private String remark;

	/** 打开新页面/在当前页面打开 */
	@TableField(value = "target")
	private String target;

	/** 标题 */
	@TableField(value = "title")
	private String title;

	/** 父组件名称 */
	@TableField(value = "parent")
	public String parent;

	/** 子权限 */
	@TableField(exist = false)
	private List<Permission> children;

	public Permission setChildren(List<Permission> list){
		this.children = list;
		return this;
	}

}