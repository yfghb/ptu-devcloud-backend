package com.ptu.devCloud.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import com.ptu.devCloud.service.TeamService;
import javax.annotation.Resource;

/**
 * TeamController
 * @author yang fan
 * @since 2024-02-11 13:19:26
 */
@RestController
@RequestMapping("/TeamController")
public class TeamController {

    @Resource
    private TeamService teamService;


    
}