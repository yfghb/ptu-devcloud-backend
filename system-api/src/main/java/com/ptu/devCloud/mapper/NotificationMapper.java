package com.ptu.devCloud.mapper;


import com.ptu.devCloud.entity.vo.NotificationPageVO;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ptu.devCloud.annotation.SeqName;
import com.ptu.devCloud.constants.TableSequenceConstants;
import com.ptu.devCloud.entity.Notification;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * NotificationMapper
 * @author yang fan
 * @since 2024-02-13 15:29:36
 */
@Mapper
public interface NotificationMapper extends BaseMapper<Notification> {

	/**
     * 根据主键查询
     * @author yang fan
     * @since 2024-02-13 15:29:36
     * @param id 主键
     * @return 返回记录，没有返回null
     */
	Notification getById(Long id);
	
	/**
     * 新增，插入所有字段
     * @author yang fan
     * @since 2024-02-13 15:29:36
     * @param notification 新增的记录
     * @return 返回影响行数
     */
    @SeqName(value = TableSequenceConstants.Notification)
	int insert(Notification notification);
	
	/**
     * 新增，忽略null字段
     * @author yang fan
     * @since 2024-02-13 15:29:36
     * @param notification 新增的记录
     * @return 返回影响行数
     */
    @SeqName(value = TableSequenceConstants.Notification)
	int insertIgnoreNull(Notification notification);
	
	/**
     * 修改，修改所有字段
     * @author yang fan
     * @since 2024-02-13 15:29:36
     * @param notification 修改的记录
     * @return 返回影响行数
     */
	int update(Notification notification);
	
	/**
     * 修改，忽略null字段
     * @author yang fan
     * @since 2024-02-13 15:29:36
     * @param notification 修改的记录
     * @return 返回影响行数
     */
	int updateIgnoreNull(Notification notification);

	/**
	 * 条件查询
	 * @author Yang Fan
	 * @since 2024/2/14 14:45
	 * @param params NotificationPageVO
	 * @return List<Notification>
	 */
	List<Notification> selectListByQueryParams(@Param("params") NotificationPageVO params);
	
}