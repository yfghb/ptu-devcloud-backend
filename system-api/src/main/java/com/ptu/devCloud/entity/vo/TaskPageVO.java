package com.ptu.devCloud.entity.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * TaskPageVO
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TaskPageVO extends PageVO implements Serializable {
    /** 任务名称 */
    private String taskName;

    /** 任务类型-多选 */
    private List<String> taskTypeList;

    /** 任务状态-多选 */
    private List<String> taskStatusList;

    /** 任务编号 */
    private String serialNumber;

    /** 当前操作人（单个用户） */
    private Long currentOperator;

    /** 参与者（单个用户） */
    private String participant;

    /** 创建人（单个用户） */
    private Long createBy;

    /** 创建人（多选） */
    private List<String> createUsers;

    /** 当前操作人（多选） */
    private List<String> currentOperators;

    /** 所属团队（单个） */
    private Long teamId;

    /** 所属项目（多选） */
    private List<String> projectIds;



}
