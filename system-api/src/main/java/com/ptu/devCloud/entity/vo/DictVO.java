package com.ptu.devCloud.entity.vo;

import com.ptu.devCloud.entity.Dict;
import com.ptu.devCloud.entity.DictItem;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * DictVO
 * @author Yang Fan
 * @since 2024/1/11 9:31
 */
@Data
public class DictVO implements Serializable {

    private Dict dict;

    private List<DictItem> itemList;

}
