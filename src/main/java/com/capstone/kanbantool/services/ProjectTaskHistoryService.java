package com.capstone.kanbantool.services;

import com.capstone.kanbantool.domain.ProjectTaskHistory;
import com.capstone.kanbantool.repositories.ProjectTaskHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectTaskHistoryService {

    @Autowired
    private ProjectTaskHistoryRepository projectTaskHistoryRepository;


    public Iterable<ProjectTaskHistory> findAllByProjectTaskId(Long projectTask_id,String username){
          return projectTaskHistoryRepository.findAllByProjectTaskForHistory_idOrderByIdDesc(projectTask_id);
    }
}
