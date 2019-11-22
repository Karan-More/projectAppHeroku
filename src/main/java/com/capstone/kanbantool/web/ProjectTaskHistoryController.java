package com.capstone.kanbantool.web;

import com.capstone.kanbantool.domain.ProjectTaskHistory;
import com.capstone.kanbantool.services.ProjectTaskHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/projectHistory")
@CrossOrigin
public class ProjectTaskHistoryController {

    @Autowired
    private ProjectTaskHistoryService projectTaskHistoryService;

    @GetMapping("/{projectTask_id}")
    public Iterable<ProjectTaskHistory> getAllTaskHistory(@PathVariable Long projectTask_id, Principal principal){
        return projectTaskHistoryService.findAllByProjectTaskId(projectTask_id, principal.getName());
    }
}
