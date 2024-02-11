package com.ptu.devCloud.entity;

import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "user_team")
public class UserTeam extends BaseEntity implements Serializable {
	/** 用户id */
	@TableField(value = "user_id")
	private Long userId;
    
	/** 团队id */
	@TableField(value = "team_id")
	private Long teamId;

}