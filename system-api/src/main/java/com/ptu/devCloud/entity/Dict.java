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
    
	/** 字典key */
	@TableField(value = "dict_value")
	private String dictValue;
    
	/** 状态: 启用(1/true), 禁用(0/false) */
	@TableField(value = "status")
	private Boolean status;

}