package com.capstone.kanbantool.web;

import com.capstone.kanbantool.domain.Project;
import com.capstone.kanbantool.domain.ProjectMember;
import com.capstone.kanbantool.domain.User;
import com.capstone.kanbantool.services.ProjectMemberService;
import com.capstone.kanbantool.services.ProjectService;
import com.capstone.kanbantool.services.UserService;
import com.capstone.kanbantool.services.ValidationErrorServiceMap;
import com.capstone.kanbantool.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.naming.Binding;
import javax.validation.Valid;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/project")
@CrossOrigin
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;

    @Autowired
    private ValidationErrorServiceMap validationErrorServiceMap;


    @Autowired
    private UserValidator userValidator;

    @Autowired
    private ProjectMemberService projectMemberService;

    @PostMapping("")
    public ResponseEntity<?> createNewProject(@Valid @RequestBody Project project, BindingResult bindingResult, Principal principal){

        ResponseEntity<?> errorMap = validationErrorServiceMap.ValidationServiceMap(bindingResult);

        if(errorMap!= null){
            return errorMap;
        }

        Project project1 = projectService.saveOrUpdateProject(project, principal.getName());
        return new ResponseEntity<Project>(project, HttpStatus.CREATED);
    }

    @GetMapping("/{projectIdentifier}")
    public ResponseEntity<?> getProjectByIdentifier(@PathVariable String projectIdentifier, Principal principal){

        Project project = projectService.findProjectById(projectIdentifier, principal.getName());
        return new ResponseEntity<Project>(project, HttpStatus.OK);
    }

    @GetMapping("/all")
    public Iterable<Project> getAllProjects(Principal principal){
        return projectService.findAllProjects(principal.getName());
    }

    @DeleteMapping("/{projectIdentifier}")
    public ResponseEntity<?> deleteProject(@PathVariable String projectIdentifier, Principal principal){
        projectService.deleteProjectById(projectIdentifier, principal.getName());

        return new ResponseEntity<String>("Project '"+projectIdentifier+"' deleted", HttpStatus.OK);
    }

    @PostMapping("/member/{projectIdentifier}")
    public ResponseEntity<?> addMemberToProject(@Valid @RequestBody User newMember, BindingResult bindingResult,@PathVariable String projectIdentifier, Principal principal){

        userValidator.validate(newMember, bindingResult);

        ResponseEntity<?> errorMaps = validationErrorServiceMap.ValidationServiceMap(bindingResult);

        if(errorMaps != null){
            return errorMaps;
        }

        ProjectMember projectMember = new ProjectMember();

        projectMember.setProjectIdentifier(projectIdentifier);
        projectMember.setUsername(newMember.getUsername());

        projectMemberService.saveProjectMember(newMember,projectMember,principal.getName());
        return new ResponseEntity<User>(newMember, HttpStatus.CREATED);
    }

    @GetMapping("/allMember/{projectIdentifier}")
    public Iterable<ProjectMember> getAllProjectMember(@PathVariable String projectIdentifier, Principal principal){
            return projectMemberService.findAllProjectMemberByProjectIdentifier(projectIdentifier);
    }

    @GetMapping("/other")
    public Iterable<Project> getAllProjectsForProjectMember(Principal principal){
        return projectService.findAllProjectForProjectMember(principal.getName());
    }





}
