package com.ptu.devCloud.controller;

import com.ptu.devCloud.service.DictItemService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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