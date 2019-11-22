package com.capstone.kanbantool.services;

import com.capstone.kanbantool.domain.UserTaskTimer;
import com.capstone.kanbantool.repositories.UserTaskTimerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserTaskTimerService {

    @Autowired
    private UserTaskTimerRepository userTaskTimerRepository;

    public Iterable<UserTaskTimer> findAllUserTimerForTaskByProjectSequence(String projectSequence, String username){
        return userTaskTimerRepository.findAllByProjectTaskSequence(projectSequence);
    }
}
