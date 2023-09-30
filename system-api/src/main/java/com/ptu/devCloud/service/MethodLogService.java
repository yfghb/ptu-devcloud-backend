package com.ptu.devCloud.service;

import com.ptu.devCloud.entity.MethodLog;
import com.baomidou.mybatisplus.extension.service.IService;


/**
 * MethodLogService
 * @author yang fan
 * @since 2023-09-30 16:37:06
 */
public interface MethodLogService extends IService<MethodLog> {

	/**
	 * 插入新的记录，忽略null字段
	 * @author Yang Fan
	 * @since 2023/9/30 17:34
	 * @param methodLog methodLog实体
	 */
	void insertIgnoreNull(MethodLog methodLog);
}