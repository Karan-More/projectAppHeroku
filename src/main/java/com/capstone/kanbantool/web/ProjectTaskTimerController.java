package com.capstone.kanbantool.web;

import com.capstone.kanbantool.domain.ProjectTask;
import com.capstone.kanbantool.domain.ProjectTaskTimer;
import com.capstone.kanbantool.services.ProjectTaskTimerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/projectTaskTimer")
@CrossOrigin
public class ProjectTaskTimerController {

    @Autowired
    private ProjectTaskTimerService projectTaskTimerService;

    @PostMapping("")
    public int updateTimeForTasks(){
        return projectTaskTimerService.updateTaskTimer();
    }

    @GetMapping("/{projectIdentifier}")
    public Iterable<ProjectTaskTimer> findProjectTaskTimer(@PathVariable String projectIdentifier, Principal principal){
        return projectTaskTimerService.findProjectTaskTimerByProjectIdentifier(projectIdentifier, principal.getName());
    }

    @GetMapping("/pt/{projectIdentifier}")
    public Iterable<ProjectTask> findProjectTask(@PathVariable String projectIdentifier, Principal principal){
        return projectTaskTimerService.findProjectTaskByProjectIdentifier(projectIdentifier,principal.getName());
    }
}
