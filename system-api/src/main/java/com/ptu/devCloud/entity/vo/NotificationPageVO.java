package com.ptu.devCloud.entity.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class NotificationPageVO extends PageVO implements Serializable {

    /**
     * 查询类型：receive/send/all
     */
    private String searchType;
    /**
     * 是否未读
     */
    private Boolean unread;
    /**
     * 当前用户的id
     */
    private Long currentUserId;
}
