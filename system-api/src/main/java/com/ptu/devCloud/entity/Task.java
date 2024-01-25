package com.ptu.devCloud.entity;

import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "task")
public class Task extends BaseEntity implements Serializable {
	/** 任务名称 */
	@TableField(value = "task_name")
	private String taskName;
    
	/** 任务类型 */
	@TableField(value = "task_type")
	private String taskType;
    
	/** 任务状态 */
	@TableField(value = "task_status")
	private String taskStatus;
    
	/** 任务描述 */
	@TableField(value = "remark")
	private String remark;
    
	/** 操作记录 */
	@TableField(value = "operation_log")
	private String operationLog;
    
	/** 任务编号 */
	@TableField(value = "serial_number")
	private String serialNumber;
    
	/** 当前操作人 */
	@TableField(value = "current_operator")
	private Long currentOperator;
    
	/** 参与者 */
	@TableField(value = "participant")
	private String participant;
    
	/** 关联任务单id列表 */
	@TableField(value = "correlation_ids")
	private String correlationIds;
    

	
}