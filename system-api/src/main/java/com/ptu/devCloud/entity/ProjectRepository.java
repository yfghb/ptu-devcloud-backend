package com.ptu.devCloud.entity;

import java.util.Date;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "project_repository")
public class ProjectRepository extends BaseEntity implements Serializable {
	/** 项目id */
	@TableField(value = "project_id")
	private Long projectId;
    
	/** 仓库名称 */
	@TableField(value = "repo_name")
	private String repoName;
    
	/** 仓库备注 */
	@TableField(value = "repo_remark")
	private String repoRemark;
    
	/** 仓库链接的类型 */
	@TableField(value = "repo_type")
	private String repoType;
    
	/** 仓库地址 */
	@TableField(value = "repo_url")
	private String repoUrl;
    

	
}