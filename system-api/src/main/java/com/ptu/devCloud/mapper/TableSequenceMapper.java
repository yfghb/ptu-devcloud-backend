package com.ptu.devCloud.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ptu.devCloud.entity.TableSequence;
import org.apache.ibatis.annotations.Param;

/**
 * TableSequenceMapper
 * @author yang fan
 * @since 2023-09-30 16:24:11
 */
@Mapper
public interface TableSequenceMapper extends BaseMapper<TableSequence> {

	/**
     * 查询所有记录
     * @author yang fan
     * @since 2023-09-30 16:24:11
     * @return 返回集合，没有返回空List
     */
	List<TableSequence> listAll();


	/**
     * 根据主键查询
     * @author yang fan
     * @since 2023-09-30 16:24:11
     * @param id 主键
     * @return 返回记录，没有返回null
     */
	TableSequence getById(Long id);
	
	/**
     * 新增，插入所有字段
     * @author yang fan
     * @since 2023-09-30 16:24:11
     * @param tableSequence 新增的记录
     * @return 返回影响行数
     */
	int insert(TableSequence tableSequence);
	
	/**
     * 新增，忽略null字段
     * @author yang fan
     * @since 2023-09-30 16:24:11
     * @param tableSequence 新增的记录
     * @return 返回影响行数
     */
	int insertIgnoreNull(TableSequence tableSequence);
	
	/**
     * 修改，修改所有字段
     * @author yang fan
     * @since 2023-09-30 16:24:11
     * @param tableSequence 修改的记录
     * @return 返回影响行数
     */
	int update(TableSequence tableSequence);
	
	/**
     * 修改，忽略null字段
     * @author yang fan
     * @since 2023-09-30 16:24:11
     * @param tableSequence 修改的记录
     * @return 返回影响行数
     */
	int updateIgnoreNull(TableSequence tableSequence);

	/**
	 * 更新表序列值（这个方法不会被MapperAop扫描到）
	 * @author Yang Fan
	 * @since 2023/10/1 15:07
	 * @param seqName 序列名称
	 */
	void changeId(@Param("seqName") String seqName);
	
}