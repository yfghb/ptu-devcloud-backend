package com.ptu.devCloud.controller;

import com.github.pagehelper.PageInfo;
import com.ptu.devCloud.entity.CommonResult;
import com.ptu.devCloud.entity.MethodLog;
import com.ptu.devCloud.entity.vo.MethodLogPageVO;
import com.ptu.devCloud.service.MethodLogService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;



/**
 * MethodLogController
 * @author yang fan
 * @since 2023-09-30 16:24:11
 */
@RestController
@RequestMapping("/MethodLogController")
public class MethodLogController {

    @Resource
    private MethodLogService methodLogService;

    /**
     * 接口日志分页查询
     * @author Yang Fan
     * @since 2023/10/6 15:03
     * @param pageVO MethodLogPageVO
     * @return CommonResult<PageInfo<MethodLog>>
     */
    @PostMapping("/getPage")
    @PreAuthorize("@permissionServiceImpl.hasPermission('ignorePermission')")
    public CommonResult<PageInfo<MethodLog>> getPage(@RequestBody MethodLogPageVO pageVO) {
        return CommonResult.successNoMsg(methodLogService.getPage(pageVO));
    }

    
}