package com.ptu.devCloud.entity;


import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.EqualsAndHashCode;

/**
 * 用户和角色关联表 实体类
 * @author yang fan
 * @since 2023-11-09 10:12:44
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "user_role")
public class UserRole extends BaseEntity implements Serializable {
	/** 用户id */
	@TableField(value = "user_id")
	private Long userId;
    
	/** 角色id */
	@TableField(value = "role_id")
	private Long roleId;
    

	
}