package com.ptu.devCloud.utils;

import com.alibaba.fastjson.JSON;
import com.ptu.devCloud.constants.HttpCodeConstants;
import com.ptu.devCloud.entity.CommonResult;
import com.ptu.devCloud.exception.JobException;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class WebUtils {

    /**
     * 渲染错误信息(HttpServletResponse status=200, CommonResult code=httpCode参数)
     * @author Yang Fan
     * @since 2023/12/15 9:59
     * @param response HttpServletResponse
     * @param message 错误信息
     * @param httpCode HttpCodeConstants
     */
    public static void returnFalse(HttpServletResponse response, String message, int httpCode) {
        CommonResult<String> result = CommonResult.error(message, httpCode);
        response.setStatus(HttpCodeConstants.SUCCESS);
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        try {
            response.getWriter().print(JSON.toJSONString(result));
        }
        catch (IOException e) {
            throw new JobException("WebUtils returnFalse方法异常: "+e.getMessage());
        }
    }

    /**
     * 渲染response数据(HttpServletResponse status=httpCode参数)
     * @author Yang Fan
     * @since 2023/12/22 20:17
     * @param response HttpServletResponse
     * @param httpCode httpCode
     * @param result CommonResult<?>
     */
    public static void render(HttpServletResponse response, int httpCode, CommonResult<?> result) {
        response.setStatus(httpCode);
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        try{
            response.getWriter().print(JSON.toJSONString(result));
        }catch (Exception e){
            throw new JobException("WebUtils render方法异常: "+e.getMessage());
        }

    }
}
