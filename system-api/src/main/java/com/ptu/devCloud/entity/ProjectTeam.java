package com.ptu.devCloud.entity;

import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "project_team")
public class ProjectTeam extends BaseEntity implements Serializable {
	/** 项目id */
	@TableField(value = "project_id")
	private Long projectId;
    
	/** 团队id */
	@TableField(value = "team_id")
	private Long teamId;
    

	
}