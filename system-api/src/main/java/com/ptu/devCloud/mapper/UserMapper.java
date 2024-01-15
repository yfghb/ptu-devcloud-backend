package com.ptu.devCloud.mapper;

import java.util.List;

import com.ptu.devCloud.entity.vo.UserPageVO;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ptu.devCloud.annotation.SeqName;
import com.ptu.devCloud.constants.TableSequenceConstants;
import com.ptu.devCloud.entity.User;
import org.apache.ibatis.annotations.Param;

/**
 * UserMapper
 * @author yang fan
 * @since 2023-11-09 10:12:44
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

	/**
     * 查询所有记录
     * @author yang fan
     * @since 2023-11-09 10:12:44
     * @return 返回集合，没有返回空List
     */
	List<User> listAll();


	/**
     * 根据主键查询
     * @author yang fan
     * @since 2023-11-09 10:12:44
     * @param id 主键
     * @return 返回记录，没有返回null
     */
	User getById(Long id);
	
	/**
     * 新增，插入所有字段
     * @author yang fan
     * @since 2023-11-09 10:12:44
     * @param user 新增的记录
     * @return 返回影响行数
     */
    @SeqName(value = TableSequenceConstants.User)
	int insert(User user);
	
	/**
     * 新增，忽略null字段
     * @author yang fan
     * @since 2023-11-09 10:12:44
     * @param user 新增的记录
     * @return 返回影响行数
     */
    @SeqName(value = TableSequenceConstants.User)
	int insertIgnoreNull(User user);
	
	/**
     * 修改，修改所有字段
     * @author yang fan
     * @since 2023-11-09 10:12:44
     * @param user 修改的记录
     * @return 返回影响行数
     */
	int update(User user);
	
	/**
     * 修改，忽略null字段
     * @author yang fan
     * @since 2023-11-09 10:12:44
     * @param user 修改的记录
     * @return 返回影响行数
     */
	int updateIgnoreNull(User user);

	/**
	 * 以账号查询用户
	 * @author Yang Fan
	 * @since 2023/11/9 14:45
	 * @param loginAccount 登录账号
	 * @return User
	 */
	User selectByAccount(@Param(value = "loginAccount") String loginAccount);


	/**
	 * 条件查询用户列表
	 * @author Yang Fan
	 * @since 2023/12/27 15:10
	 * @param params UserPageVO(查询条件)
	 * @return List<User>
	 */
	List<User> selectListByQueryParams(@Param("params") UserPageVO params);

	/**
	 * 改变用户状态
	 * @author Yang Fan
	 * @since 2024/1/3 19:37
	 * @param userIds 用户id列表
	 * @param status 状态
	 * @return int
	 */
	int updateStatusByIdList(@Param("userIds") List<Long> userIds, @Param("status") String status);

	/**
	 * 以userId查询权限表达式列表
	 * @author Yang Fan
	 * @since 2024/1/15 10:30
	 * @param userId Long 用户id
	 * @return List<String> 权限表达式列表
	 */
	List<String> selectPermissionStrByUserId(@Param("userId") Long userId);
	
}