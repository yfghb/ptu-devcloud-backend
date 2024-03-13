package com.ptu.devCloud.entity;

import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "task_operation_log")
public class TaskOperationLog extends BaseEntity implements Serializable {
	/** 任务id */
	@TableField(value = "task_id")
	private Long taskId;
    
	/** 操作日志 */
	@TableField(value = "operation_log")
	private String operationLog;
    

	
}