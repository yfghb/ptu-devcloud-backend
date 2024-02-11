package com.ptu.devCloud.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import com.ptu.devCloud.service.UserTeamService;
import javax.annotation.Resource;

/**
 * UserTeamController
 * @author yang fan
 * @since 2024-02-11 13:19:26
 */
@RestController
@RequestMapping("/UserTeamController")
public class UserTeamController {

    @Resource
    private UserTeamService userTeamService;


    
}