package com.ptu.devCloud.service;


import com.github.pagehelper.PageInfo;
import com.ptu.devCloud.entity.Dict;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ptu.devCloud.entity.vo.DictPageVO;
import com.ptu.devCloud.entity.vo.DictVO;
import com.ptu.devCloud.entity.vo.IdsVO;
import com.ptu.devCloud.entity.vo.StatusVO;


/**
 * DictService
 * @author yang fan
 * @since 2024-01-10 12:17:51
 */
public interface DictService extends IService<Dict> {

    /**
     * 新增/更新数据字典
     * @author Yang Fan
     * @since 2024/1/11 9:54
     * @param dictVO DictVO
     */
    void saveDictAndItem(DictVO dictVO);

    /**
     * 数据字典分页查询
     * @author Yang Fan
     * @since 2024/1/11 10:13
     * @param pageVO DictPageVO
     * @return PageInfo<Dict>
     */
    PageInfo<Dict> page(DictPageVO pageVO);

    /**
     * 查询数据字典和字典对象
     * @author Yang Fan
     * @since 2024/1/11 10:23
     * @param id dictId
     * @return DictVO
     */
    DictVO getVoById(Long id);

    /**
     * 启用/禁用
     * @author Yang Fan
     * @since 2024/1/11 13:23
     * @param statusVO StatusVO
     */
    void changeStatus(StatusVO statusVO);

    /**
     * 删除数据字典
     * @author Yang Fan
     * @since 2024/1/11 13:56
     * @param idsVO dictIds
     */
    void deleteByIds(IdsVO idsVO);
	
}