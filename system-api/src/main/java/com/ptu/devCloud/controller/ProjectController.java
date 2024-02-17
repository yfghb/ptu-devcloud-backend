package com.ptu.devCloud.controller;

import com.ptu.devCloud.entity.CommonResult;
import com.ptu.devCloud.entity.Project;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import com.ptu.devCloud.service.ProjectService;
import javax.annotation.Resource;

/**
 * ProjectController
 * @author yang fan
 * @since 2024-02-17 16:24:23
 */
@RestController
@RequestMapping("/ProjectController")
public class ProjectController {

    @Resource
    private ProjectService projectService;

    /**
     * 新增项目
     * @author Yang Fan
     * @since 2024/2/17 16:34
     * @author Yang Fan
     * @since 2024/2/17 16:33
     * @param project Project
     */
    @PostMapping("/add")
    @PreAuthorize("@permissionServiceImpl.hasPermission('ignorePermission')")
    public CommonResult<String> add(@RequestBody Project project){
        projectService.add(project);
        return CommonResult.success("成功");
    }
    
}