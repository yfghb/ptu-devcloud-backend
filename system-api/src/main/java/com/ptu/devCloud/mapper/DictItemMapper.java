package com.ptu.devCloud.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ptu.devCloud.annotation.SeqName;
import com.ptu.devCloud.constants.TableSequenceConstants;
import com.ptu.devCloud.entity.DictItem;

/**
 * DictItemMapper
 * @author yang fan
 * @since 2024-01-10 12:17:51
 */
@Mapper
public interface DictItemMapper extends BaseMapper<DictItem> {

	/**
     * 根据主键查询
     * @author yang fan
     * @since 2024-01-10 12:17:51
     * @param id 主键
     * @return 返回记录，没有返回null
     */
	DictItem getById(Long id);
	
	/**
     * 新增，插入所有字段
     * @author yang fan
     * @since 2024-01-10 12:17:51
     * @param dictItem 新增的记录
     * @return 返回影响行数
     */
    @SeqName(value = TableSequenceConstants.DictItem)
	int insert(DictItem dictItem);
	
	/**
     * 新增，忽略null字段
     * @author yang fan
     * @since 2024-01-10 12:17:51
     * @param dictItem 新增的记录
     * @return 返回影响行数
     */
    @SeqName(value = TableSequenceConstants.DictItem)
	int insertIgnoreNull(DictItem dictItem);
	
	/**
     * 修改，修改所有字段
     * @author yang fan
     * @since 2024-01-10 12:17:51
     * @param dictItem 修改的记录
     * @return 返回影响行数
     */
	int update(DictItem dictItem);
	
	/**
     * 修改，忽略null字段
     * @author yang fan
     * @since 2024-01-10 12:17:51
     * @param dictItem 修改的记录
     * @return 返回影响行数
     */
	int updateIgnoreNull(DictItem dictItem);
	
	
}