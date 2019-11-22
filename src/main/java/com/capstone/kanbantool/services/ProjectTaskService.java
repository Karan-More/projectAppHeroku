package com.capstone.kanbantool.services;

import com.capstone.kanbantool.domain.*;
import com.capstone.kanbantool.exception.ProjectNotFoundException;
import com.capstone.kanbantool.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ProjectTaskService {



    @Autowired
    private ProjectTaskRepository projectTaskRepository;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectTaskHistoryRepository projectTaskHistoryRepository;

    @Autowired
    private ProjectTaskTimerRepository projectTaskTimerRepository;

    @Autowired
    private UserTaskTimerRepository userTaskTimerRepository;

    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask, String username){
        // all project task added to a specific project
        // we need backlog object to refer project task
        // set backlog to project task

        // exception for project not found

        // projectSequence is a projectIdentifier and id of the task within the project ex. ID01-1

        // update backlog sequence

        //Initial priority when priority is null
        //Initial status when status is null


        Backlog backlog = projectService.findProjectById(projectIdentifier, username).getBacklog(); //backlogRepository.findByProjectIdentifier(projectIdentifier);


            projectTask.setBacklog(backlog);

            Integer BacklogSequence = backlog.getPTSequence();

            BacklogSequence++;

            backlog.setPTSequence(BacklogSequence);

            projectTask.setProjectSequence(projectIdentifier+"-"+BacklogSequence);

            projectTask.setProjectIdentifier(projectIdentifier);

            projectTask.setCreatedAt(new Date());

            UserTaskTimer userTaskTimer = new UserTaskTimer();
            if(projectTask.getId() == null){
                ProjectTaskTimer projectTaskTimer = new ProjectTaskTimer();
                projectTask.setProjectTaskTimer(projectTaskTimer);

                projectTaskTimer.setProjectTask(projectTask);
                projectTaskTimer.setTimeInDone("0");
                projectTaskTimer.setTimeInProgress("0");
                projectTaskTimer.setTimeInToDo("0");
                projectTaskTimer.setUpdatedAt(new Date());


                List<UserTaskTimer>  userTaskTimerList = new ArrayList<>();
                userTaskTimer.setProjectTaskForUserTimer(projectTask);
                userTaskTimer.setTotalMinutesSpent(Long.parseLong("0"));
                userTaskTimer.setUser(userRepository.findByUsername(projectTask.getProjectTaskAssignee()));
                userTaskTimer.setProjectTaskSequence(projectTask.getProjectSequence());
                userTaskTimerList.add(userTaskTimer);
                projectTask.setUserTaskTimers(userTaskTimerList);


            }

            if(projectTask.getPriority() == null || projectTask.getPriority().equals(String.valueOf(0))){
                projectTask.setPriority(String.valueOf(3));
            }

            if(projectTask.getProjectTaskAssignee() == null || projectTask.getProjectTaskAssignee() == ""){
                projectTask.setProjectTaskAssignee("NOT_ASSIGNED");
            }else{
                User user = userRepository.findByUsername(projectTask.getProjectTaskAssignee());
                projectTask.setUser(user);
                projectTask.setProjectTaskAssignee(user.getUsername());
            }

            if(projectTask.getStatus() == "" || projectTask.getStatus() == null){
                projectTask.setStatus("TO_DO");
            }

            ProjectTask savedProjectTask = new ProjectTask();
            savedProjectTask = projectTaskRepository.save(projectTask);
            if(savedProjectTask != null){
                userTaskTimerRepository.save(userTaskTimer);
                return savedProjectTask;
            }else{
                return null;
            }

    }

    public Iterable<ProjectTask> findBacklogById(String id, String username) {

            projectService.findProjectById(id, username);
            return projectTaskRepository.findByProjectIdentifierOrderByPriority(id);
    }

    public ProjectTask findProjectTaskByProjectSequence(String backlog_id,String projectTask_id, String username){
        // get username of project owner

        projectService.findProjectById(backlog_id, username);

        ProjectTask projectTask = projectTaskRepository.findByProjectSequence(projectTask_id);

        if(projectTask == null){
            throw new ProjectNotFoundException("Project Task with Id: '"+projectTask_id+"' does not exist");
        }

        if(!projectTask.getProjectIdentifier().equals(backlog_id)){

            throw new ProjectNotFoundException("Project Task with Id: '"+projectTask_id+"' does not exist in Project: '"+backlog_id+"'");

        }


        return projectTask;
    }
    public ProjectTask findProjectTaskByProjectAssignee(String backlog_id,String projectTask_id, String username){
            ProjectTask projectTask = projectTaskRepository.findByProjectTaskAssigneeAndProjectSequence(username,projectTask_id);

            return projectTask;
    }


    public ProjectTask updateByProjectSequence(ProjectTask updatedProjectTask,String backlog_id, String projectTask_id,String username){

        ProjectTask projectTask = findProjectTaskByProjectAssignee(backlog_id,projectTask_id, username);



        Field[] fields = updatedProjectTask.getClass().getDeclaredFields();

        for (Field field: fields){
            String fieldValue = "";
            String fieldName = "";
            String oldfieldValue = "";
            Date newDate = null;
            Date oldDate = null;


            long timeDifference = 0;

            try{
                if(field.get(updatedProjectTask) != null){
                    fieldValue = field.get(updatedProjectTask).toString();
                }

            }catch (IllegalAccessException e){
                e.printStackTrace();
            }

            if(fieldValue != null && !fieldValue.equals("") && fieldValue.length() > 8 && fieldValue.substring(0,8).equals("UPDATED+")){

                fieldName = field.getName();

                User user = userRepository.findByUsername(username);


                // get oldField Value and field value
                if(fieldName.equals("dueDateUpdate")){

                        oldDate = projectTask.getDueDate();
                        newDate = updatedProjectTask.getDueDate();
                }else{
                    try{
                        oldfieldValue = field.get(projectTask).toString();
                    }catch (IllegalAccessException e){
                        e.printStackTrace();
                    }

                    fieldValue = fieldValue.replace("UPDATED+","");
                }




                ProjectTaskHistory projectTaskHistory = new ProjectTaskHistory();
                // if user changes status
                if(fieldName.equals("status")){

                    Date currentDateTime = new Date();
                    Optional<ProjectTaskTimer> projectTaskTimer = projectTaskTimerRepository.findById(projectTask.getId());

                    updatedProjectTask.setPreviousStatus(oldfieldValue);
                    updatedProjectTask.setStatus(fieldValue);
                    String finalOldfieldValue = oldfieldValue;
                    projectTaskTimer.ifPresent(projectTaskTimer1 -> {

                        long milliseconds = 0;
                        long presentValueFromDB = 0;
                            switch (finalOldfieldValue){


                                case "TO_DO":
                                    milliseconds  = (new Date().getTime() - projectTaskTimer1.getUpdatedAt().getTime());
                                    presentValueFromDB = Long.parseLong(projectTaskTimer1.getTimeInToDo())*60000;
                                    projectTaskTimer1.setTimeInToDo(String.valueOf((milliseconds+presentValueFromDB)/60000));
                                    projectTaskTimer1.setUpdatedAt(new Date());
                                    projectTaskTimerRepository.save(projectTaskTimer1);
                                    break;
                                case "IN_PROGRESS":
                                    milliseconds = (new Date().getTime() - projectTaskTimer1.getUpdatedAt().getTime());
                                    presentValueFromDB = Long.parseLong(projectTaskTimer1.getTimeInProgress())*60000;
                                    projectTaskTimer1.setTimeInProgress(String.valueOf((milliseconds+presentValueFromDB)/60000));
                                    projectTaskTimer1.setUpdatedAt(new Date());
                                    projectTaskTimerRepository.save(projectTaskTimer1);
                                        break;
                                case "DONE":
                                    milliseconds = (new Date().getTime() - projectTaskTimer1.getUpdatedAt().getTime());
                                    presentValueFromDB = Long.parseLong(projectTaskTimer1.getTimeInDone())*60000;
                                    projectTaskTimer1.setTimeInDone(String.valueOf((milliseconds+presentValueFromDB)/60000));
                                    projectTaskTimer1.setUpdatedAt(new Date());
                                    projectTaskTimerRepository.save(projectTaskTimer1);
                                    break;

                            }
                    });
                }

                // if user changes TaskAssignee
                if(fieldName.equals("projectTaskAssignee")){
                    Date currentDateTime = new Date();

                    List<UserTaskTimer> userTaskTimerList = new ArrayList<UserTaskTimer>();

                    UserTaskTimer userTaskTimer = userTaskTimerRepository.findTop1ByProjectTaskSequenceOrderByIdDesc(projectTask.getProjectSequence());

                    //for(UserTaskTimer userTaskTimer: userTaskTimerList){

                        if(userTaskTimer.getCreatedAt() != null && userTaskTimer.getTotalMinutesSpent() == 0){
                            userTaskTimer.setTotalMinutesSpent((currentDateTime.getTime() - userTaskTimer.getCreatedAt().getTime())/(60 * 1000) % 60);
                            userTaskTimerRepository.save(userTaskTimer);
                            UserTaskTimer userTaskTimerNewAssigneeEntry = new UserTaskTimer();

                            userTaskTimerNewAssigneeEntry.setProjectTaskSequence(projectTask.getProjectSequence());
                            userTaskTimerNewAssigneeEntry.setTotalMinutesSpent((long)0);
                            userTaskTimerNewAssigneeEntry.setUser(userRepository.findByUsername(updatedProjectTask.getProjectTaskAssignee().replace("UPDATED+","")));
                            userTaskTimerNewAssigneeEntry.setProjectTaskForUserTimer(projectTask);
                            userTaskTimerRepository.save(userTaskTimerNewAssigneeEntry);

                        }

                   // }

                }

                // if user changes due date
                if(fieldName.equals("dueDateUpdate")){
                    projectTaskHistory.setAttributeName("dueDate");
                    projectTaskHistory.setOldValue(oldDate.toString());
                    projectTaskHistory.setUpdatedValue(newDate.toString());
                    }else {
                    projectTaskHistory.setAttributeName(fieldName);
                    projectTaskHistory.setOldValue(oldfieldValue);
                    projectTaskHistory.setUpdatedValue(fieldValue);

                }

                projectTaskHistory.setUser(user);
                projectTaskHistory.setUsername(user.getUsername());
                projectTaskHistory.setProjectTaskForHistory(projectTask);
                projectTaskHistoryRepository.save(projectTaskHistory);

            }


        }

        updatedProjectTask.setSummary(updatedProjectTask.getSummary().replace("UPDATED+",""));
        updatedProjectTask.setAcceptanceCriteria(updatedProjectTask.getAcceptanceCriteria().replace("UPDATED+",""));
        updatedProjectTask.setPriority(updatedProjectTask.getPriority().replace("UPDATED+",""));
        updatedProjectTask.setCreatedAt(projectTask.getCreatedAt());
        updatedProjectTask.setUpdatedAt(new Date());

        projectTask = updatedProjectTask;

        if(projectTask.getProjectTaskAssignee() == null || projectTask.getProjectTaskAssignee() == ""){
            projectTask.setProjectTaskAssignee("NOT_ASSIGNED");
        }else{
            String taskAssignee =  updatedProjectTask.getProjectTaskAssignee().replace("UPDATED+","");
            User user = userRepository.findByUsername(taskAssignee);
            projectTask.setUser(user);
            projectTask.setProjectTaskAssignee(user.getUsername());
        }

        return projectTaskRepository.save(projectTask);
    }

    public void deleteProjectTaskByProjectSequence(String backlog_id, String projectTask_id, String username){

        ProjectTask projectTask = findProjectTaskByProjectSequence(backlog_id,projectTask_id, username);

        projectTaskRepository.delete(projectTask);
    }

    // get all task for Task assignee for specific project.
    public Iterable<ProjectTask> findAllProjectTaskForTaskAssigneeForProject(String username,String projectIdentifier){
        return projectTaskRepository.findAllByProjectTaskAssigneeAndProjectIdentifier(username,projectIdentifier);
    }
}
