package com.capstone.kanbantool.services;

import com.capstone.kanbantool.domain.ProjectTask;
import com.capstone.kanbantool.domain.ProjectTaskTimer;
import com.capstone.kanbantool.repositories.ProjectTaskRepository;
import com.capstone.kanbantool.repositories.ProjectTaskTimerRepository;
import org.hibernate.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.text.SimpleDateFormat;

@Service
public class ProjectTaskTimerService {

    @Autowired
    private ProjectTaskTimerRepository projectTaskTimerRepository;

    @Autowired
    private ProjectTaskRepository projectTaskRepository;

    private static void accept(String timeInToDo) {
    }

    public int updateTaskTimer(){

        // get all task from projectTask table.
        // use previous_status , status and updated_At column.

        // if previous_status = null and status = "TO_DO" --> take difference of createdAt and current time

        // if previous_status = "TO_DO" and status = "TO_DO" --> take difference of createdAT and currentTime if updatedAT is null
        // else take difference of updatedAt and currentTime (if both status are equal)

        // if previous_status and status is not same --> take difference of updatedAt and currentTime

        List<ProjectTask> projectTaskList = projectTaskRepository.findAll();
        int index = 0;
        if(!projectTaskList.isEmpty()){

            for(ProjectTask projectTask : projectTaskList){

                String previous_status =  projectTask.getPreviousStatus();
                String current_status = projectTask.getStatus();

                Date createdAt =  projectTask.getCreatedAt();
                Date currentDateTime = new Date();

                Date updatedAt = projectTask.getUpdatedAt();
                long difference = 0;

                // getting taskTimer object
                Optional<ProjectTaskTimer> projectTaskTimer = projectTaskTimerRepository.findById(projectTask.getProjectTaskTimer().getId());



                if(previous_status == null || previous_status == "" && current_status != null || current_status != ""){

                   difference = (currentDateTime.getTime() - createdAt.getTime()) / (60*60*1000);
                   updateTaskTimer(current_status,difference,projectTaskTimer);

                }else if(!previous_status.equals(current_status) && projectTask.getUpdatedAt() != null){

                   difference = (currentDateTime.getTime() - updatedAt.getTime()) / (60*60*1000);
                    updateTaskTimer(current_status,difference,projectTaskTimer);

                }

                projectTaskTimer.ifPresent(projectTaskTimer1 -> {
                    projectTaskTimer1.setUpdatedAt(new Date());
                    projectTaskTimerRepository.save(projectTaskTimer1);

                });

                index++;
            }
        }


       return index;
    }

    private void updateTaskTimer(String currentStatus,long difference, Optional<ProjectTaskTimer> projectTaskTimer){

        switch(currentStatus){
            case "TO_DO":
                Long finalDifference = difference;

                projectTaskTimer.ifPresent(projectTaskTimer1 -> {
                    projectTaskTimer1.setTimeInToDo(String.valueOf(finalDifference));
                });
                break;
            case "IN_PROGRESS":
                finalDifference = difference;
                projectTaskTimer.ifPresent(projectTaskTimer1 -> {
                    projectTaskTimer1.setTimeInProgress(String.valueOf(Long.parseLong(projectTaskTimer1.getTimeInProgress()) + finalDifference));
                });
                break;
            case "DONE":
                finalDifference = difference;
                projectTaskTimer.ifPresent(projectTaskTimer1 -> {
                    projectTaskTimer1.setTimeInDone(String.valueOf(Long.parseLong(projectTaskTimer1.getTimeInDone()) + finalDifference));
                });
                break;
        }

    }

    public Iterable<ProjectTaskTimer> findProjectTaskTimerByProjectIdentifier(String projectIdentifier, String username){

       List<ProjectTask> projectTaskList = projectTaskRepository.findAllByProjectIdentifier(projectIdentifier);

       List<ProjectTaskTimer> projectTaskTimers = new ArrayList<ProjectTaskTimer>();

       for(ProjectTask projectTask: projectTaskList){
            projectTaskTimers.add( projectTaskTimerRepository.findByProjectTask_Id(projectTask.getId()));
       }

       return projectTaskTimers;
    }

    public Iterable<ProjectTask> findProjectTaskByProjectIdentifier(String projectIdentifier, String username){

        List<ProjectTask> projectTaskList = projectTaskRepository.findAllByProjectIdentifier(projectIdentifier);

        return projectTaskList;
    }


}
