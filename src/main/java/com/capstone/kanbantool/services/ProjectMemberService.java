package com.capstone.kanbantool.services;

import com.capstone.kanbantool.domain.Project;
import com.capstone.kanbantool.domain.ProjectMember;
import com.capstone.kanbantool.domain.User;
import com.capstone.kanbantool.exception.ProjectIdException;
import com.capstone.kanbantool.exception.ProjectNotFoundException;
import com.capstone.kanbantool.exception.UsernameExistsInDatabaseException;
import com.capstone.kanbantool.repositories.ProjectMemberRepository;
import com.capstone.kanbantool.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectMemberService {

    @Autowired
    private ProjectMemberRepository projectMemberRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserService userService;

    public ProjectMember saveProjectMember(User newMember, ProjectMember projectMember, String username){

        Project project = projectRepository.findByProjectIdentifier(projectMember.getProjectIdentifier().toUpperCase());

        if(project == null){
            throw new ProjectIdException("Project does not exist" );
        }

        if(!project.getProjectLeader().equals(username)){
            throw new ProjectNotFoundException("Project not found in your account");
        }


            projectMember.setProjectIdentifier(projectMember.getProjectIdentifier());
            projectMember.setUsername(projectMember.getUsername());

            // check if project_identifier already exist or not in database for given username

            boolean flag = false;

            Iterable<ProjectMember> projectMemberIterable = findAllProjectIdentifierForProjectMember(projectMember.getUsername());


            for(ProjectMember pm: projectMemberIterable){
                    if(pm.getProjectIdentifier().equals(projectMember.getProjectIdentifier())){
                        flag = true;
                        break;
                }
            }


            if(flag == false){
                    try{
                        userService.saveUser(newMember);
                    }catch(Exception ex){
                        // no error message to return so left blank
                    }finally {
                        return projectMemberRepository.save(projectMember);
                    }
            }else{
                throw new UsernameExistsInDatabaseException("Username '"+projectMember.getUsername()+"' already registered!!");
            }




    }

    public Iterable<ProjectMember> findAllProjectIdentifierForProjectMember(String username){
       return projectMemberRepository.findAllByUsername(username);
    }

    public Iterable<ProjectMember> findAllProjectMemberByProjectIdentifier(String projectIdentifier){
        return projectMemberRepository.findAllByProjectIdentifier(projectIdentifier);
    }

}
