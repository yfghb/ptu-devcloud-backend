package com.ptu.devCloud.entity;

import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "task_remark")
public class TaskRemark extends BaseEntity implements Serializable {
	/** 用户名称 */
	@TableField(value = "username")
	private String username;
    
	/** 备注内容 */
	@TableField(value = "remark")
	private String remark;
    
	/** 任务id */
	@TableField(value = "task_id")
	private Long taskId;
    

	
}