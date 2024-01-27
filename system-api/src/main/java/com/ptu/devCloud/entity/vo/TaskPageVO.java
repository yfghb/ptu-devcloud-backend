package com.ptu.devCloud.entity.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * TaskPageVO
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TaskPageVO extends PageVO implements Serializable {
    /** 任务名称 */
    private String taskName;

    /** 任务类型 */
    private String taskType;

    /** 任务状态 */
    private String taskStatus;

    /** 任务编号 */
    private String serialNumber;

    /** 创建人 */
    private Long createBy;
}
