package com.capstone.kanbantool.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email(message = "Username needs to be an email")
    @NotBlank(message = "Username is required")
    @Column(unique = true)
    private String username;

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotBlank(message = "Password is required")
    private String password;

    @Transient
    private String confirmPassword;

    private Date createdAt;

    private Date updatedAt;

    @PrePersist
    protected void onCreate(){
        this.createdAt = new Date();
    }

    @PreUpdate
    protected void onUpdate(){
        this.updatedAt = new Date();
    }

    @OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER, mappedBy = "user", orphanRemoval = true)
    private List<Project> projectList = new ArrayList<>();


    public List<Project> getProjectList() {
        return projectList;
    }

    public void setProjectList(List<Project> projectList) {
        this.projectList = projectList;
    }

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    // UserDetails interface methods


    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }


    //ProjectTask assignee

    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "user", orphanRemoval = true)
    private List<ProjectTask> projectTaskList = new ArrayList<>();

    public List<ProjectTask> getProjectTaskList() {
        return projectTaskList;
    }

    public void setProjectTaskList(List<ProjectTask> projectTaskList) {
        this.projectTaskList = projectTaskList;
    }


    //user for ProjectTaskHistory

    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "user", orphanRemoval = true)
    private List<ProjectTaskHistory> projectTaskHistories = new ArrayList<>();

    // user for UserTaskTimer

    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "user", orphanRemoval = true)
    private List<UserTaskTimer> userTaskTimers = new ArrayList<>();


    public List<TaskChecklist> getTaskChecklists() {
        return taskChecklists;
    }

    public void setTaskChecklists(List<TaskChecklist> taskChecklists) {
        this.taskChecklists = taskChecklists;
    }

    // user(ChecklistAssignee) for TaskChecklist
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "user", orphanRemoval = true)
    private List<TaskChecklist> taskChecklists = new ArrayList<>();

    public List<ProjectTaskHistory> getProjectTaskHistories() {
        return projectTaskHistories;
    }

    public void setProjectTaskHistories(List<ProjectTaskHistory> projectTaskHistories) {
        this.projectTaskHistories = projectTaskHistories;
    }

    public List<UserTaskTimer> getUserTaskTimers() {
        return userTaskTimers;
    }

    public void setUserTaskTimers(List<UserTaskTimer> userTaskTimers) {
        this.userTaskTimers = userTaskTimers;
    }
}
