package com.ptu.devCloud.entity.dto;

import com.ptu.devCloud.entity.Team;
import lombok.Data;

import java.util.List;

@Data
public class ChartDataDTO {
    /** 项目数量 */
    private Integer projectCnt;

    /** 团队数量 */
    private Integer teamCnt;

    /** 用户名 */
    private String username;

    /** 完成过的任务数量 */
    private Integer taskFinishCnt;

    /** 待办需求单数量 */
    private Integer reqTaskCnt;

    /** 已完成需求单数量 */
    private Integer reqFinishTaskCnt;

    /** 待办研发单数量 */
    private Integer devTaskCnt;

    /** 已完成需求单数量 */
    private Integer devFinishTaskCnt;

    /** 待办测试单数量 */
    private Integer testTaskCnt;

    /** 已完成需求单数量 */
    private Integer testFinishTaskCnt;

    /** 待办缺陷单数量 */
    private Integer bugTaskCnt;

    /** 已完成需求单数量 */
    private Integer bugFinishTaskCnt;

    /** 新增任务列表，n行2列，taskList[i][0]：日期（mm-dd），taskList[i][0]：数量 */
    private List<List<String>> taskList;

    /** 已完成任务列表，n行2列，taskFinishList[i][0]：日期（mm-dd），taskFinishList[i][0]：数量 */
    private List<List<String>> taskFinishList;

    /** 当前请求的登录用户加入的团队列表 */
    private List<Team> teamList;

    /** 当前请求的登录用户加入的团队 */
    private Team currentTeam;

    /** 上个月创建任务的数量 */
    private Integer lastMonthTaskCnt;

    /** 这个月创建任务的数量 */
    private Integer thisMonthTaskCnt;
}
