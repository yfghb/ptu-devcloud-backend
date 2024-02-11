package com.ptu.devCloud.entity;


import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "team")
public class Team extends BaseEntity implements Serializable {
	/** 团队名称 */
	@TableField(value = "team_name")
	private String teamName;
    
	/** 加入方式 */
	@TableField(value = "join_type")
	private String joinType;
    
	/** 管理员id列表，用逗号隔开 */
	@TableField(value = "admin_ids")
	private String adminIds;
    
	/** 超级管理员 */
	@TableField(value = "super_admin")
	private Integer superAdmin;
    

	
}