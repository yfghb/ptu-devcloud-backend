package com.ptu.devCloud.exception;

import com.alibaba.fastjson.JSON;
import com.ptu.devCloud.constants.HttpCodeConstants;
import com.ptu.devCloud.entity.CommonResult;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       org.springframework.security.access.AccessDeniedException accessDeniedException) throws IOException {
        accessDeniedException.printStackTrace();
        CommonResult<String> result = CommonResult.error("您没有当前操作的权限");
        result.setCode(HttpCodeConstants.ACCESS_DENIED);
        response.setStatus(HttpCodeConstants.SUCCESS);
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.getWriter().print(JSON.toJSONString(result));
    }
}
