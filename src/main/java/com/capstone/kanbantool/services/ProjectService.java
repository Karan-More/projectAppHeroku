package com.capstone.kanbantool.services;

import com.capstone.kanbantool.domain.Backlog;
import com.capstone.kanbantool.domain.Project;
import com.capstone.kanbantool.domain.ProjectMember;
import com.capstone.kanbantool.domain.User;
import com.capstone.kanbantool.exception.ProjectIdException;
import com.capstone.kanbantool.exception.ProjectNotFoundException;
import com.capstone.kanbantool.repositories.BacklogRepository;
import com.capstone.kanbantool.repositories.ProjectMemberRepository;
import com.capstone.kanbantool.repositories.ProjectRepository;
import com.capstone.kanbantool.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectMemberRepository projectMemberRepository;

    public Project saveOrUpdateProject(Project project, String username){


        if(project.getId() != null){
            Project existingProject = projectRepository.findByProjectIdentifier(project.getProjectIdentifier());

            if(existingProject != null && (!existingProject.getProjectLeader().equals(username))){
                throw new ProjectNotFoundException("Project not found in your account");
            }else if(existingProject == null){
                    throw new ProjectNotFoundException("Project with ID: '"+project.getProjectIdentifier()+"' cannot be updated because it doesn't exist");
            }

        }




        try{

            User user = userRepository.findByUsername(username);

            String projectIdentifier = project.getProjectIdentifier().toUpperCase();

            project.setUser(user);

            project.setProjectLeader(user.getUsername());

            project.setProjectIdentifier(projectIdentifier);

            if(project.getId() == null){
                // every time project is created, new backlog has been created for that project
                Backlog backlog = new Backlog();
                project.setBacklog(backlog);
                backlog.setProject(project);
                backlog.setProjectIdentifier(projectIdentifier);
            }

            if(project.getId() != null){
                project.setBacklog(backlogRepository.findByProjectIdentifier(projectIdentifier));
            }

            project.setStartDate(project.getStartDate());
            return  projectRepository.save(project);

        }catch (Exception e){
            throw new ProjectIdException("Project Id '" + project.getProjectIdentifier().toUpperCase() + "' already exists" );
        }

    }

    public Project findProjectById(String projectIdentifier, String username){

        Project project = projectRepository.findByProjectIdentifier(projectIdentifier.toUpperCase());
        if(project == null){
            throw new ProjectIdException("Project does not exist" );
        }

        if(!project.getProjectLeader().equals(username)){
            throw new ProjectNotFoundException("Project not found in your account");
        }
        return project;
    }

    public Iterable<Project> findAllProjects(String username){

        return projectRepository.findAllByProjectLeader(username);
    }

    public void deleteProjectById(String projectIdentifier, String username){


        projectRepository.delete(findProjectById(projectIdentifier, username));
    }

    public List<Project> findAllProjectForProjectMember(String username){

        Iterable<ProjectMember> projectMembers = new ArrayList<ProjectMember>();
        projectMembers = projectMemberRepository.findAllByUsername(username);

        List<Project> projects = new ArrayList<Project>();

        for(ProjectMember projectMember: projectMembers){
            projects.add(projectRepository.findByProjectIdentifier(projectMember.getProjectIdentifier()));
        }

        return projects;
    }
}
