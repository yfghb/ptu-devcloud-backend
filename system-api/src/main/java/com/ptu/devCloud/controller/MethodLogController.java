package com.ptu.devCloud.controller;

import com.ptu.devCloud.entity.CommonResult;
import com.ptu.devCloud.entity.MethodLog;
import com.ptu.devCloud.service.MethodLogService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;


/**
 * MethodLogController
 * @author yang fan
 * @since 2023-09-30 16:24:11
 */
@RestController
@RequestMapping("/methodLog")
public class MethodLogController {

    @Resource
    private MethodLogService methodLogService;

    @GetMapping("/list")
    public CommonResult<List<MethodLog>> getList() {
        return CommonResult.success(methodLogService.list());
    }

    
}