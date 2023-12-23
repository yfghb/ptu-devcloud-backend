package com.ptu.devCloud.entity;

import com.ptu.devCloud.constants.CommonConstants;
import com.ptu.devCloud.constants.HttpCodeConstants;
import lombok.Data;
import java.util.HashMap;
import java.util.Map;

/**
 * 统一返回结果类
 * @author Yang Fan
 * @since 2023/10/1 14:15
 */
@Data
public class CommonResult<T> {

    // http状态码
    private Integer code;

    // 错误信息
    private String msg;

    // 数据
    private T data;

    // 动态数据
    private Map<String, Object> map = new HashMap<>();

    public static <T> CommonResult<T> success(T object) {
        CommonResult<T> commonResult = new CommonResult<>();
        commonResult.data = object;
        commonResult.msg = object instanceof String ? (String) object : CommonConstants.COMMON_SUCCESS_CHINESE;
        commonResult.code = HttpCodeConstants.SUCCESS;
        return commonResult;
    }

    public static <T> CommonResult<T> successNoMsg(T object) {
        CommonResult<T> commonResult = new CommonResult<>();
        commonResult.data = object;
        commonResult.msg = null;
        commonResult.code = HttpCodeConstants.SUCCESS;
        return commonResult;
    }

    public static <T> CommonResult<T> error(String msg) {
        CommonResult<T> commonResult = new CommonResult<>();
        commonResult.msg = msg;
        commonResult.code = HttpCodeConstants.SYSTEM_ERROR;
        return commonResult;
    }

    public static <T> CommonResult<T> error(String msg, int httpCode) {
        CommonResult<T> commonResult = new CommonResult<>();
        commonResult.msg = msg;
        commonResult.code = httpCode;
        return commonResult;
    }

    public CommonResult<T> add(String key, Object value) {
        this.map.put(key, value);
        return this;
    }

}