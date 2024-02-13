package com.ptu.devCloud.entity;


import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "notification")
public class Notification extends BaseEntity implements Serializable {
	/** 创建人名称 */
	@TableField(value = "create_name")
	private String createName;

	/** 消息内容 */
	@TableField(value = "message")
	private String message;

	/** 接收人id */
	@TableField(value = "receive_user")
	private Long receiveUser;

	/** 接收人名称 */
	@TableField(value = "receive_username")
	private String receiveUsername;

	/** 是否未读 */
	@TableField(value = "unread")
	private Boolean unread;

	/** 是否仅通知(无需触发回调) */
	@TableField(value = "notify_only")
	private Boolean notifyOnly;

	/** 回调参数 */
	@TableField(value = "params_json")
	private String paramsJson;


}