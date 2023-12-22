package com.ptu.devCloud.entity;


import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.EqualsAndHashCode;

/**
 * 表序列 实体类
 * @author yang fan
 * @since 2023-09-30 16:15:23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "table_sequence")
public class TableSequence extends BaseEntity implements Serializable {
	/** SEQ_表名大写 */
	@TableField(value = "sequence_name")
	private String sequenceName;
    
	/** 表主键 */
	@TableField(value = "table_id")
	private Long tableId;
    
	/** 自增步长 */
	@TableField(value = "step")
	private Integer step;

	/** 乐观锁 */
	@TableField(value = "version")
	private String version;
    

	
}