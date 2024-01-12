package com.ptu.devCloud.entity;


import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.EqualsAndHashCode;

/**
 * 方法日志 实体类
 * @author yang fan
 * @since 2023-09-30 16:15:23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "method_log")
public class MethodLog extends BaseEntity implements Serializable {
	/** 方法名 */
	@TableField(value = "method_name")
	private String methodName;
    
	/** 方法所在路径 */
	@TableField(value = "method_path")
	private String methodPath;
    
	/** 方法入参 */
	@TableField(value = "params_json")
	private String paramsJson;
    
	/** 方法出参 */
	@TableField(value = "result_json")
	private String resultJson;
    
	/** 方法运行耗时(单位：秒) */
	@TableField(value = "consume_time")
	private String consumeTime;
    
	/** 方法是否运行成功 */
	@TableField(value = "pass_flag")
	private String passFlag;
    
	/** 报错信息 */
	@TableField(value = "error_msg")
	private String errorMsg;

	/** 请求url */
	@TableField(value = "url")
	private String url;
    

	
}