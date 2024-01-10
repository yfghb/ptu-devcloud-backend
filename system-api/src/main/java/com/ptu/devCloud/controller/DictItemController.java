package com.ptu.devCloud.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ptu.devCloud.service.DictItemService;
import com.ptu.devCloud.entity.CommonResult;
import com.ptu.devCloud.annotation.EnableMethodLog;
import javax.annotation.Resource;

/**
 * DictItemController
 * @author yang fan
 * @since 2024-01-10 12:17:51
 */
@RestController
@RequestMapping("/DictItemController")
public class DictItemController {

    @Resource
    private DictItemService dictItemService;


    
}