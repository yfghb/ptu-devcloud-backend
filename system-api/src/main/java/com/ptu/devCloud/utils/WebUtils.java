package com.ptu.devCloud.utils;

import com.alibaba.fastjson.JSON;
import com.ptu.devCloud.constants.HttpCodeConstants;
import com.ptu.devCloud.entity.CommonResult;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class WebUtils {

    /**
     * 渲染错误信息
     * @author Yang Fan
     * @since 2023/12/15 9:59
     * @param response HttpServletResponse
     * @param message 错误信息
     * @param httpCode HttpCodeConstants
     */
    public static void returnFalse(HttpServletResponse response, String message, int httpCode) {
        CommonResult<String> result = CommonResult.error(message);
        result.setCode(httpCode);
        response.setStatus(200);
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        try {
            response.getWriter().print(JSON.toJSONString(result));
        }
        catch (IOException e) {
            log.error("WebUtils returnFalse方法异常: "+e.getMessage());
        }
    }
}
