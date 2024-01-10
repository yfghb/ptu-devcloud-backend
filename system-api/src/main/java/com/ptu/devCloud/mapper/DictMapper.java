package com.ptu.devCloud.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ptu.devCloud.annotation.SeqName;
import com.ptu.devCloud.constants.TableSequenceConstants;
import com.ptu.devCloud.entity.Dict;

/**
 * DictMapper
 * @author yang fan
 * @since 2024-01-10 12:17:51
 */
@Mapper
public interface DictMapper extends BaseMapper<Dict> {

	/**
     * 根据主键查询
     * @author yang fan
     * @since 2024-01-10 12:17:51
     * @param id 主键
     * @return 返回记录，没有返回null
     */
	Dict getById(Long id);
	
	/**
     * 新增，插入所有字段
     * @author yang fan
     * @since 2024-01-10 12:17:51
     * @param dict 新增的记录
     * @return 返回影响行数
     */
    @SeqName(value = TableSequenceConstants.Dict)
	int insert(Dict dict);
	
	/**
     * 新增，忽略null字段
     * @author yang fan
     * @since 2024-01-10 12:17:51
     * @param dict 新增的记录
     * @return 返回影响行数
     */
    @SeqName(value = TableSequenceConstants.Dict)
	int insertIgnoreNull(Dict dict);
	
	/**
     * 修改，修改所有字段
     * @author yang fan
     * @since 2024-01-10 12:17:51
     * @param dict 修改的记录
     * @return 返回影响行数
     */
	int update(Dict dict);
	
	/**
     * 修改，忽略null字段
     * @author yang fan
     * @since 2024-01-10 12:17:51
     * @param dict 修改的记录
     * @return 返回影响行数
     */
	int updateIgnoreNull(Dict dict);
	
	
}