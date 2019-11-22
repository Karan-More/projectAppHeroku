package com.capstone.kanbantool.web;

import com.capstone.kanbantool.domain.UserTaskTimer;
import com.capstone.kanbantool.repositories.UserTaskTimerRepository;
import com.capstone.kanbantool.services.UserTaskTimerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/userTaskTimer")
@CrossOrigin
public class UserTaskTimerController {

    @Autowired
    private UserTaskTimerService userTaskTimerService;

    @GetMapping("/{projectSequence}")
    public Iterable<UserTaskTimer> findAllTaskTimerByProjectSequence(@PathVariable String projectSequence, Principal  principal){
            return userTaskTimerService.findAllUserTimerForTaskByProjectSequence(projectSequence, principal.getName());
    }
}
