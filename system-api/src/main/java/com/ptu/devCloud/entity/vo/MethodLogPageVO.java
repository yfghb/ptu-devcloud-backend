package com.ptu.devCloud.entity.vo;

import lombok.Data;

import java.io.Serializable;



@Data
public class MethodLogPageVO implements Serializable {
    /** 当前页 */
    private Integer current;

    /** 一页显示条数 */
    private Integer pageSize;

    private String startDate;

    private String endDate;

}
