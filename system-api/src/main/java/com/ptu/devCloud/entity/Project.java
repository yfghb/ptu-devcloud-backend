package com.ptu.devCloud.entity;


import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "project")
public class Project extends BaseEntity implements Serializable {
	/** 项目名称 */
	@TableField(value = "project_name")
	private String projectName;
    
	/** 项目备注/描述 */
	@TableField(value = "project_remark")
	private String projectRemark;
    
	/** 项目当前版本 */
	@TableField(value = "current_version")
	private String currentVersion;
    
	/** gitea账号 */
	@TableField(value = "gitea_account")
	private String giteaAccount;
    
	/** gitea密码 */
	@TableField(value = "gitea_password")
	private String giteaPassword;
    
	/** gitea用户名 */
	@TableField(value = "gitea_username")
	private String giteaUsername;
    
	/** 历史成员 */
	@TableField(value = "history_member")
	private String historyMember;

	/** 新建项目时的团队id */
	@TableField(exist = false)
	private Long teamId;

	/** 创建人（名称） */
	@TableField(exist = false)
	private String createName;

	/** 新创建任务的数量 */
	@TableField(exist = false)
	private Integer newTaskCnt;

	/** 进行中任务的数量 */
	@TableField(exist = false)
	private Integer todoTaskCnt;

	/** 已完成任务的数量 */
	@TableField(exist = false)
	private Integer doneTaskCnt;

	/** 已关闭任务的数量 */
	@TableField(exist = false)
	private Integer closeTaskCnt;

	/** 本周新增任务的数量 */
	@TableField(exist = false)
	private Integer addTaskCnt;
}