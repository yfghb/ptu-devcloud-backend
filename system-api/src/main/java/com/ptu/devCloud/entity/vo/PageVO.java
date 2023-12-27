package com.ptu.devCloud.entity.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;


/**
 * 通用pageVO
 * @author Yang Fan
 * @since 2023/12/27 15:13
 */
@Data
public class PageVO implements Serializable {
    /** 当前页 */
    private Integer current;

    /** 一页显示条数 */
    private Integer pageSize;

    /** 创建时间-左区间 */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date startDate;

    /** 创建时间-右区间 */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date endDate;
}
