package com.ptu.devCloud.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import com.ptu.devCloud.service.DictService;
import javax.annotation.Resource;

/**
 * DictController
 * @author yang fan
 * @since 2024-01-10 12:17:51
 */
@RestController
@RequestMapping("/DictController")
public class DictController {

    @Resource
    private DictService dictService;


    
}