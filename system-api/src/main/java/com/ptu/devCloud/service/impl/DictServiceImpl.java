package com.ptu.devCloud.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ptu.devCloud.annotation.SeqName;
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
        List<DictItem> updateDictItemList = new ArrayList<>();
        Dict dict = dictVO.getDict();
        boolean isAdd = false;
        if(dict.getId() == null){
            isAdd = true;
            dict.setId(tableSequenceService.generateId(TableSequenceConstants.Dict));
        }
        for(DictItem item: dictVO.getItemList()){
            // 如果是新增则设置id为空
            if(item.getNewItem() != null && item.getNewItem()){
                item.setId(null);
                insertDictItemList.add(item);
                item.setDictId(dict.getId());
            }else {
                updateDictItemList.add(item);
            }
        }
        boolean finalIsAdd = isAdd;
        transaction.execute(action -> {
            try {
                if(finalIsAdd)dictMapper.insert(dict);
                else dictMapper.updateIgnoreNull(dict);
                if(CollUtil.isNotEmpty(insertDictItemList)){
                    dictItemService.saveBatch(insertDictItemList);
                }
                if(CollUtil.isNotEmpty(updateDictItemList)){
                    dictItemService.updateBatchById(updateDictItemList);
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
    public DictVO getVoById(Long id) {
        if(id == null){
            throw new JobException("id不能为空");
        }
        Dict dict = dictMapper.getById(id);
        List<DictItem> itemList = dictItemService.lambdaQuery().eq(DictItem::getDictId, id).list();
        DictVO vo = new DictVO();
        vo.setDict(dict);
        vo.setItemList(itemList);
        return vo;
    }

    @Override
    public void changeStatus(StatusVO statusVO) {
        if(CollUtil.isEmpty(statusVO.getDictIds()) || statusVO.getStatus() == null)return;
        List<Long> list = new ArrayList<>();
        statusVO.getDictIds().forEach(id -> list.add(Long.valueOf(id)));
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
        for(Long dictId:ids){
            List<DictItem> list = dictItemService.lambdaQuery().eq(DictItem::getDictId, dictId).list();
            list.forEach(obj -> itemIds.add(obj.getId()));
        }
        transaction.execute(action -> {
            try {
                dictMapper.deleteBatchIds(ids);
                if(CollUtil.isNotEmpty(itemIds)){
                    dictItemService.removeByIds(itemIds);
                }
                return true;
            }catch (Exception e){
                action.setRollbackOnly();
                throw new JobException("失败！");
            }
        });
    }
}