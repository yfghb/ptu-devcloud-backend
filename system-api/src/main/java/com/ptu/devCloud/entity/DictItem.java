package com.ptu.devCloud.entity;


import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "dict_item")
public class DictItem extends BaseEntity implements Serializable {

	/** 字典id */
	@TableField(value = "dict_id")
	private Long dictId;
    
	/** 名称 */
	@TableField(value = "item_name")
	private String itemName;
    
	/** 值 */
	@TableField(value = "item_value")
	private String itemValue;
    
	/** 状态 */
	@TableField(value = "status")
	private String status;

	/** 排序 */
	@TableField(value = "order_num")
	private Integer orderNum;

	/** 是否新增对象 */
	@TableField(exist = false)
	private Boolean newItem;

}