package com.ptu.devCloud.entity;


import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.EqualsAndHashCode;

/**
 * 用户表 实体类
 * @author yang fan
 * @since 2023-11-09 10:12:44
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "user")
public class User extends BaseEntity implements Serializable {
	/** 用户状态: 启用(1/true), 禁用(0/false) */
	@TableField(value = "status")
	private Boolean status;
    
	/** 登录账号 */
	@TableField(value = "login_account")
	private String loginAccount;
    
	/** 登录密码 */
	@TableField(value = "login_password")
	private String loginPassword;
    
	/** 邮箱 */
	@TableField(value = "email")
	private String email;
    
	/** 手机号 */
	@TableField(value = "phone_number")
	private String phoneNumber;
    
	/** 用户名 */
	@TableField(value = "user_name")
	private String userName;
    
	/** 头像 */
	@TableField(value = "avatar")
	private String avatar;
    

	
}