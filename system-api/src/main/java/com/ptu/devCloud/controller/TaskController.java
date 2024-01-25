package com.ptu.devCloud.controller;

import com.ptu.devCloud.service.TaskService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;

/**
 * TaskController
 * @author yang fan
 * @since 2024-01-25 09:27:01
 */
@RestController
@RequestMapping("/TaskController")
public class TaskController {

    @Resource
    private TaskService taskService;


    
}