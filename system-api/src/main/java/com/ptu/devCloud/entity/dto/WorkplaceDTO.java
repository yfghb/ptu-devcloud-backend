package com.ptu.devCloud.entity.dto;

import lombok.Data;

import java.util.List;

@Data
public class WorkplaceDTO {
    /** 项目数量 */
    private Integer projectCnt;

    /** 团队数量 */
    private Integer teamCnt;

    /** 完成过的任务数量 */
    private Integer taskFinishCnt;

    /** 待办需求单数量 */
    private Integer reqTaskCnt;

    /** 待办研发单数量 */
    private Integer devTaskCnt;

    /** 待办测试单数量 */
    private Integer testTaskCnt;

    /** 待办缺陷单数量 */
    private Integer bugTaskCnt;

    /** 新增任务列表，n行2列，taskList[i][0]：日期（mm-dd），taskList[i][0]：数量 */
    private List<List<String>> taskList;

    /** 已完成任务列表，n行2列，taskFinishList[i][0]：日期（mm-dd），taskFinishList[i][0]：数量 */
    private List<List<String>> taskFinishList;
}
