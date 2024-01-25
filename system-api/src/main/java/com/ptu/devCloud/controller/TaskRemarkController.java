package com.ptu.devCloud.controller;

import com.ptu.devCloud.service.TaskRemarkService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;

/**
 * TaskRemarkController
 * @author yang fan
 * @since 2024-01-25 09:27:01
 */
@RestController
@RequestMapping("/TaskRemarkController")
public class TaskRemarkController {

    @Resource
    private TaskRemarkService taskRemarkService;


    
}