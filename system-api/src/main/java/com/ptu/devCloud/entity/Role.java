package com.ptu.devCloud.entity;


import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.EqualsAndHashCode;

/**
 * 角色表 实体类
 * @author yang fan
 * @since 2023-11-09 10:12:44
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "role")
public class Role extends BaseEntity implements Serializable {

	/** 角色名称 */
	@TableField(value = "role_name")
	private String roleName;
    
	/** 显示顺序 */
	@TableField(value = "order_num")
	private Integer orderNum;
    
	/** 角色状态:启用(1/true), 禁用(0/false) */
	@TableField(value = "status")
	private Boolean status;
    

}