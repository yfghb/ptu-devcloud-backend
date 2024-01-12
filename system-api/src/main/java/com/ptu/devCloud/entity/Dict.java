package com.ptu.devCloud.entity;

import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "dict")
public class Dict extends BaseEntity implements Serializable {

	/** 字典名称 */
	@TableField(value = "dict_name")
	private String dictName;
    
	/** 字典编码 */
	@TableField(value = "dict_code")
	private String dictCode;

	/** 状态: 启用/禁用 '1'/'0' */
	@TableField(value = "status")
	private String status;

}