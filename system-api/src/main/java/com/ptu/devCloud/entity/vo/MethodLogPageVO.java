package com.ptu.devCloud.entity.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;


/**
 * MethodLogPageVO
 * @author Yang Fan
 * @since 2023/10/06 15:13
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MethodLogPageVO extends PageVO implements Serializable {

    /**
     * 接口名
     */
    private String methodName;
    /**
     * 接口路径
     */
    private String methodPath;
    /**
     * url
     */
    private String url;
    /**
     * 是否成功 '1'/'0' 成功/失败
     */
    private String passFlag;

}
