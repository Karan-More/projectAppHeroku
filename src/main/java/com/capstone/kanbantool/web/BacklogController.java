package com.capstone.kanbantool.web;


import com.capstone.kanbantool.domain.Project;
import com.capstone.kanbantool.domain.ProjectTask;
import com.capstone.kanbantool.services.ProjectTaskService;
import com.capstone.kanbantool.services.ValidationErrorServiceMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.text.ParseException;


@RestController
@RequestMapping("/api/backlog")
@CrossOrigin
public class BacklogController {

    @Autowired
    private ProjectTaskService projectTaskService;

    @Autowired
    private  ValidationErrorServiceMap validationErrorServiceMap;

    @PostMapping("/{backlog_id}")
    public ResponseEntity<?> addProjectTaskToBacklog(@Valid @RequestBody ProjectTask projectTask,
                                                     BindingResult result, @PathVariable String backlog_id, Principal principal){

        ResponseEntity<?> errorMap = validationErrorServiceMap.ValidationServiceMap(result);

        if(errorMap != null) return errorMap;

        ProjectTask projectTask1 = projectTaskService.addProjectTask(backlog_id, projectTask, principal.getName());

        return new ResponseEntity<ProjectTask>(projectTask1, HttpStatus.CREATED);
    }

    @GetMapping("/{backlog_id}")
    public Iterable<ProjectTask> getProjectBacklog(@PathVariable String backlog_id, Principal principal){
            return projectTaskService.findBacklogById(backlog_id, principal.getName());
    }

    @GetMapping("/{backlog_id}/{projectTask_id}")
    public ResponseEntity<?> getProjectTask(@PathVariable String backlog_id, @PathVariable String projectTask_id, Principal principal){

        ProjectTask projectTask = projectTaskService.findProjectTaskByProjectSequence(backlog_id, projectTask_id, principal.getName());

        return new ResponseEntity<ProjectTask>(projectTask, HttpStatus.OK);

    }

    @GetMapping("/{backlog_id}/{projectTask_id}/{assignee}")
    public ResponseEntity<?> getProjectTaskForTaskAssignee(@PathVariable String backlog_id, @PathVariable String projectTask_id,@PathVariable String assignee,Principal principal){

        ProjectTask projectTask = projectTaskService.findProjectTaskByProjectAssignee(backlog_id, projectTask_id, principal.getName());

        return new ResponseEntity<ProjectTask>(projectTask, HttpStatus.OK);

    }

    @PatchMapping("/{backlog_id}/{projectTask_id}")
    public ResponseEntity<?> updateProjectTask(@Valid @RequestBody ProjectTask projectTask, BindingResult bindingResult,
                                               @PathVariable String backlog_id, @PathVariable String projectTask_id, Principal principal) throws ParseException {

        ResponseEntity<?> errorMap = validationErrorServiceMap.ValidationServiceMap(bindingResult);

        if(errorMap != null) return errorMap;

        ProjectTask updatedTask = projectTaskService.updateByProjectSequence(projectTask, backlog_id, projectTask_id, principal.getName());

        return new ResponseEntity<ProjectTask>(updatedTask, HttpStatus.OK);

    }

    @DeleteMapping("/{backlog_id}/{projectTask_id}")
    public ResponseEntity<?> deleteProjectTask(@PathVariable String backlog_id,@PathVariable String projectTask_id,Principal principal){

        projectTaskService.deleteProjectTaskByProjectSequence(backlog_id,projectTask_id, principal.getName());

            return new ResponseEntity<String>("Project Task "+projectTask_id+" was deleted Successfully",HttpStatus.OK);
    }

    // get all task based on task assignee
    @GetMapping("/taskAssignee/{backlog_id}")
    public Iterable<ProjectTask> getProjectBacklogForTaskAssignee(@PathVariable String backlog_id, Principal principal){
        return projectTaskService.findAllProjectTaskForTaskAssigneeForProject(principal.getName(),backlog_id);
    }



}
