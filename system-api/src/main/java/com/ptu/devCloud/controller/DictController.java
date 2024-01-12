package com.ptu.devCloud.controller;

import com.github.pagehelper.PageInfo;
import com.ptu.devCloud.entity.CommonResult;
import com.ptu.devCloud.entity.Dict;
import com.ptu.devCloud.entity.vo.DictPageVO;
import com.ptu.devCloud.entity.vo.DictVO;
import com.ptu.devCloud.entity.vo.IdsVO;
import com.ptu.devCloud.entity.vo.StatusVO;
import org.springframework.lang.Nullable;
import org.springframework.security.access.prepost.PreAuthorize;
import com.ptu.devCloud.service.DictService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * DictController
 * @author yang fan
 * @since 2024-01-10 12:17:51
 */
@RestController
@RequestMapping("/DictController")
public class DictController {

    @Resource
    private DictService dictService;

    /**
     * 新增/更新数据字典
     * @author Yang Fan
     * @since 2024/1/11 9:54
     * @param dictVO DictVO
     * @return CommonResult<String>
     */
    @PostMapping("/saveDictAndItem")
    @PreAuthorize("@permissionServiceImpl.hasPermission('ignorePermission')")
    public CommonResult<String> saveDictAndItem(@RequestBody DictVO dictVO){
        dictService.saveDictAndItem(dictVO);
        return CommonResult.successWithMsg(null,"保存成功");
    }

    /**
     * 数据字典分页查询
     * @author Yang Fan
     * @since 2024/1/11 10:13
     * @param pageVO DictPageVO
     * @return CommonResult<PageInfo<Dict>>
     */
    @PostMapping("/page")
    @PreAuthorize("@permissionServiceImpl.hasPermission('ignorePermission')")
    public CommonResult<PageInfo<Dict>> page(@RequestBody DictPageVO pageVO){
        return CommonResult.successNoMsg(dictService.page(pageVO));
    }

    /**
     * 查询数据字典和字典对象
     * @author Yang Fan
     * @since 2024/1/11 10:23
     * @param dictCode dictCode
     * @return CommonResult<DictVO>
     */
    @GetMapping("/getByDictCode")
    @PreAuthorize("@permissionServiceImpl.hasPermission('ignorePermission')")
    public CommonResult<DictVO> getDictAndItemByDictId(@RequestParam("dictCode")String dictCode,
                                                       @Nullable Boolean enableFiltering){
        if(enableFiltering == null) enableFiltering = false;
        return CommonResult.successNoMsg(dictService.getByDictCode(dictCode, enableFiltering));
    }

    /**
     * 启用/禁用
     * @author Yang Fan
     * @since 2024/1/11 13:23
     * @param statusVO StatusVO
     * @return CommonResult<String>
     */
    @PostMapping("/changeStatus")
    @PreAuthorize("@permissionServiceImpl.hasPermission('ignorePermission')")
    public CommonResult<String> changeStatus(@RequestBody StatusVO statusVO){
        dictService.changeStatus(statusVO);
        return CommonResult.success(null);
    }

    /**
     * 删除数据字典
     * @author Yang Fan
     * @since 2024/1/11 13:56
     * @param idsVO dictIds
     * @return CommonResult<String>
     */
    @DeleteMapping("/deleteByIds")
    @PreAuthorize("@permissionServiceImpl.hasPermission('ignorePermission')")
    public CommonResult<String> deleteByIds(@RequestBody IdsVO idsVO){
        dictService.deleteByIds(idsVO);
        return CommonResult.success(null);
    }
}