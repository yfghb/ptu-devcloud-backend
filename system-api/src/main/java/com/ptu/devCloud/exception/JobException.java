package com.ptu.devCloud.exception;

import com.ptu.devCloud.constants.HttpCodeConstants;
import java.io.Serializable;

/**
 * 自定义异常
 * @author Yang Fan
 * @since 2023/12/22 19:41
 */
public class JobException extends RuntimeException implements Serializable {

    private final String msg;
    private final int code;

    public JobException(int code, String message) {
        super(message);
        this.code = code;
        this.msg = message;
    }

    public JobException(String message) {
        super(message);
        this.code = HttpCodeConstants.SYSTEM_ERROR;
        this.msg = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return msg;
    }
}
