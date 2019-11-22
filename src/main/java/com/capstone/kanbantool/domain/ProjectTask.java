package com.capstone.kanbantool.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class ProjectTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(updatable = false, unique = true)
    public String projectSequence;

    @NotBlank(message = "Please include a project summary")
    public String summary;

    public String acceptanceCriteria;
    public String status;
    public String priority;

    public String previousStatus;

    public String getPreviousStatus() {
        return previousStatus;
    }

    public void setPreviousStatus(String previousStatus) {
        this.previousStatus = previousStatus;
    }

    public Backlog getBacklog() {
        return backlog;
    }

    public void setBacklog(Backlog backlog) {
        this.backlog = backlog;
    }

    // many to one relationship with backlog object
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "backlog_id", updatable = false, nullable = false)
    @JsonIgnore
    public Backlog backlog;

    // one to many relationship ProjectTask <---> ProjectTaskHistory
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "projectTaskForHistory", orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonIgnore
    public List<ProjectTaskHistory> projectTaskHistories = new ArrayList<>();


    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "projectTask")
    @JsonIgnore
    public ProjectTaskTimer projectTaskTimer;

    public ProjectTaskTimer getProjectTaskTimer() {
        return projectTaskTimer;
    }

    public void setProjectTaskTimer(ProjectTaskTimer projectTaskTimer) {
        this.projectTaskTimer = projectTaskTimer;
    }

    public ProjectTask() {
    }

    @Override
    public String toString() {
        return "ProjectTask{" +
                "id=" + id +
                ", projectSequence='" + projectSequence + '\'' +
                ", summary='" + summary + '\'' +
                ", acceptanceCriteria='" + acceptanceCriteria + '\'' +
                ", status='" + status + '\'' +
                ", priority=" + priority +
                ", backlog=" + backlog +
                ", projectIdentifier='" + projectIdentifier + '\'' +
                ", dueDate=" + dueDate +
                ", updatedAt=" + updatedAt +
                ", user=" + user +
                ", projectTaskAssignee='" + projectTaskAssignee + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProjectSequence() {
        return projectSequence;
    }

    public void setProjectSequence(String projectSequence) {
        this.projectSequence = projectSequence;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getAcceptanceCriteria() {
        return acceptanceCriteria;
    }

    public void setAcceptanceCriteria(String acceptanceCriteria) {
        this.acceptanceCriteria = acceptanceCriteria;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getProjectIdentifier() {
        return projectIdentifier;
    }

    public void setProjectIdentifier(String projectIdentifier) {
        this.projectIdentifier = projectIdentifier;
    }

    public Date getDueDate() {
        return dueDate;
    }


    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
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

    @Column(updatable = false)
    public String projectIdentifier;

    //@JsonFormat(pattern = "yyyy-MM-dd ")
    public Date dueDate;

    //@JsonFormat(pattern = "yyyy-mm-dd")
    public Date createdAt;

    //@JsonFormat(pattern = "yyyy-mm-dd")
    public Date updatedAt;

    //ProjectTask assignee

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    public User user;

    public String projectTaskAssignee;

    public User getUser() {
        return user;
    }

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

    public void setUser(User user) {
        this.user = user;
    }

    public String getProjectTaskAssignee() {
        return projectTaskAssignee;
    }

    public void setProjectTaskAssignee(String projectTaskAssignee) {
        this.projectTaskAssignee = projectTaskAssignee;
    }

    // for UserTaskTimer

    // one to many relationship ProjectTask <---> ProjectTaskHistory
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "projectTaskForUserTimer", orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonIgnore
    public List<UserTaskTimer> userTaskTimers = new ArrayList<>();

    // one to many relationship ProjectTask <----> TaskChecklist
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "projectTask", orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonIgnore
    public List<TaskChecklist> taskChecklists = new ArrayList<>();

    public List<TaskChecklist> getTaskChecklists() {
        return taskChecklists;
    }

    public void setTaskChecklists(List<TaskChecklist> taskChecklists) {
        this.taskChecklists = taskChecklists;
    }

    //Transient field for dueDate update
    @Transient
    public String dueDateUpdate;

    public String getDueDateUpdate() {
        return dueDateUpdate;
    }

    public void setDueDateUpdate(String dueDateUpdate) {
        this.dueDateUpdate = dueDateUpdate;
    }
}
