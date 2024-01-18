package com.ptu.devCloud.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ptu.devCloud.annotation.SeqName;
import com.ptu.devCloud.constants.CommonConstants;
import com.ptu.devCloud.constants.TableSequenceConstants;
import com.ptu.devCloud.entity.Dict;
import com.ptu.devCloud.entity.DictItem;
import com.ptu.devCloud.entity.vo.DictPageVO;
import com.ptu.devCloud.entity.vo.DictVO;
import com.ptu.devCloud.entity.vo.IdsVO;
import com.ptu.devCloud.entity.vo.StatusVO;
import com.ptu.devCloud.exception.JobException;
import com.ptu.devCloud.mapper.DictMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ptu.devCloud.service.DictItemService;
import com.ptu.devCloud.service.TableSequenceService;
import com.ptu.devCloud.utils.RedisUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import com.ptu.devCloud.service.DictService;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * DictServiceImpl
 * @author yang fan
 * @since 2024-01-10 12:17:51
 */
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService{

    @Resource
    private DictMapper dictMapper;

    @Resource
    private DictItemService dictItemService;

    @Resource
    private TransactionTemplate transaction;

    @Resource
    private TableSequenceService tableSequenceService;

    @Resource
    private RedisUtils redisUtils;

    @Override
    @SeqName(value = TableSequenceConstants.Dict)
    public boolean save(Dict entity) {
        return super.save(entity);
    }

    @Override
    @SeqName(value = TableSequenceConstants.Dict)
    public boolean saveBatch(Collection<Dict> entityList) {
        return super.saveBatch(entityList);
    }


    @Override
    public void saveDictAndItem(DictVO dictVO) {
        if (dictVO == null || dictVO.getDict() == null)return;
        List<DictItem> insertDictItemList = new ArrayList<>();
        Dict dict = dictVO.getDict();
        boolean isAdd = false;
        if(dict.getId() == null){
            isAdd = true;
            dict.setId(tableSequenceService.generateId(TableSequenceConstants.Dict));
        }
        List<Long> deleteDictItemIds = new ArrayList<>();
        if(!isAdd) {
            // 如果不是新增，则收集旧字典对象的id
            List<DictItem> list = dictItemService.lambdaQuery().eq(DictItem::getDictId, dict.getId()).list();
            list.forEach(obj -> deleteDictItemIds.add(obj.getId()));
        }
        for(DictItem item: dictVO.getItemList()){
            item.setId(null);
            item.setDictId(dict.getId());
            insertDictItemList.add(item);
        }
        boolean finalIsAdd = isAdd;
        transaction.execute(action -> {
            try {
                if(finalIsAdd)dictMapper.insert(dict);
                else {
                    dictMapper.updateIgnoreNull(dict);
                    // 如果不是新增，则删除旧字典对象
                    if(CollUtil.isNotEmpty(deleteDictItemIds)){
                        dictItemService.removeByIds(deleteDictItemIds);
                    }
                }
                if(CollUtil.isNotEmpty(insertDictItemList)){
                    dictItemService.saveBatch(insertDictItemList);
                }
                // 如果不是新增
                if(!finalIsAdd){
                    DictVO vo = new DictVO();
                    vo.setDict(dict);
                    vo.setItemList(insertDictItemList);
                    // 把（过滤有禁用选项的数据字典）缓存删除
                    redisUtils.del(generateDictRedisKey(dict.getDictCode(), true));
                    // 更新（不需要过滤禁用的数据字典）缓存
                    redisUtils.set(generateDictRedisKey(dict.getDictCode(), false), vo, -1);
                }
                return true;
            }catch (DuplicateKeyException e){
                action.setRollbackOnly();
                throw new JobException("字典编码重复！");
            } catch (Exception e){
                action.setRollbackOnly();
                throw new JobException("保存数据字典失败！");
            }
        });
    }

    @Override
    public PageInfo<Dict> page(DictPageVO pageVO) {
        if(pageVO == null || pageVO.getCurrent() == null || pageVO.getPageSize() == null) {
            return new PageInfo<>();
        }
        PageHelper.startPage(pageVO.getCurrent(), pageVO.getPageSize());
        List<Dict> list = dictMapper.selectListByQueryParams(pageVO);
        return new PageInfo<>(list);
    }

    @Override
    public DictVO getByDictCode(String dictCode, Boolean enableFiltering) {
        if(StrUtil.isEmpty(dictCode)){
            throw new JobException("dictCode不能为空");
        }
        String key = generateDictRedisKey(dictCode, enableFiltering);
        Object redisData = redisUtils.get(key);
        // 如果存在缓存则之间返回
        if(redisData!=null){
            return (DictVO) redisData;
        }
        DictVO vo = new DictVO();
        LambdaQueryWrapper<Dict> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Dict::getDictCode, dictCode);
        if(enableFiltering)lqw.eq(Dict::getStatus, "1");
        // 查询数据字典
        Dict dict = dictMapper.selectOne(lqw);
        if(dict == null) return vo;
        LambdaQueryWrapper<DictItem> itemLqw = new LambdaQueryWrapper<>();
        if(enableFiltering)itemLqw.eq(DictItem::getStatus, "1");
        itemLqw.eq(DictItem::getDictId, dict.getId());
        itemLqw.orderByAsc(DictItem::getOrderNum);
        //查询字典对象
        List<DictItem> itemList = dictItemService.list(itemLqw);
        vo.setDict(dict);
        vo.setItemList(itemList);
        // 设置永久缓存dict数据
        redisUtils.set(key, vo, -1);
        return vo;
    }

    @Override
    public void changeStatus(StatusVO statusVO) {
        if(CollUtil.isEmpty(statusVO.getDictIds()) || StrUtil.isEmpty(statusVO.getStatus()))return;
        List<Long> list = new ArrayList<>();
        for(String idStr:statusVO.getDictIds()) {
            Long id = Long.valueOf(idStr);
            list.add(id);
            // 如果禁用则删除缓存
            if("0".equals(statusVO.getStatus())){
                Dict dict = dictMapper.getById(id);
                redisUtils.del(generateDictRedisKey(dict.getDictCode(), true),
                        generateDictRedisKey(dict.getDictCode(), false));
            }
        }
        dictMapper.updateStatusByIdList(list,statusVO.getStatus());
    }

    @Override
    public void deleteByIds(IdsVO idsVO) {
        if(idsVO == null || CollUtil.isEmpty(idsVO.getDictIds())){
            throw new JobException("id不能为空");
        }
        List<Long> ids = new ArrayList<>();
        idsVO.getDictIds().forEach(dictId -> ids.add(Long.valueOf(dictId)));
        List<Long> itemIds = new ArrayList<>();
        List<String> redisKeys = new ArrayList<>();
        for(Long dictId:ids){
            Dict dict = dictMapper.getById(dictId);
            List<DictItem> list = dictItemService.lambdaQuery().eq(DictItem::getDictId, dictId).list();
            list.forEach(obj -> itemIds.add(obj.getId()));
            // 收集待删除的redisKey
            redisKeys.add(generateDictRedisKey(dict.getDictCode(), true));
            redisKeys.add(generateDictRedisKey(dict.getDictCode(), false));
        }
        transaction.execute(action -> {
            try {
                // 删除主表
                dictMapper.deleteBatchIds(ids);
                if(CollUtil.isNotEmpty(itemIds)){
                    // 删除子表
                    dictItemService.removeByIds(itemIds);
                }
                // 删除缓存
                redisKeys.forEach(key -> redisUtils.del(key));
                return true;
            }catch (Exception e){
                action.setRollbackOnly();
                throw new JobException("失败！");
            }
        });
    }

    private String generateDictRedisKey(String dictCode,Boolean enableFiltering){
        return CommonConstants.DICT_REDIS_KEY_PREFIX + (dictCode + enableFiltering).hashCode();
    }
}