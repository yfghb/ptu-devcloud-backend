package com.ptu.devCloud.entity.vo;


import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * UserPageVO
 * @author Yang Fan
 * @since 2023/12/27 15:15
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserPageVO extends PageVO implements Serializable {
    /** 状态 '1'/'0' 启用/禁用 */
    private String status;

    /** 登录账号 */
    private String loginAccount;

    /** 邮箱 */
    private String email;

    /** 手机号 */
    private String phoneNumber;

    /** 用户名 */
    private String userName;

}
