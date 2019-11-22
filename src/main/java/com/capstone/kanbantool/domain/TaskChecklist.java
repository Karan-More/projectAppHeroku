package com.capstone.kanbantool.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class TaskChecklist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ProjectTask <-----> TaskChecklist
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "projectTask_id", nullable = false)
    @JsonIgnore
    private ProjectTask projectTask;

    private String checklistAssignee;

    private String checkListDescription;

    public String getCheckListDescription() {
        return checkListDescription;
    }

    public void setCheckListDescription(String checkListDescription) {
        this.checkListDescription = checkListDescription;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User user;

    public TaskChecklist() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProjectTask getProjectTask() {
        return projectTask;
    }

    public void setProjectTask(ProjectTask projectTask) {
        this.projectTask = projectTask;
    }

    public String getChecklistAssignee() {
        return checklistAssignee;
    }

    public void setChecklistAssignee(String checklistAssignee) {
        this.checklistAssignee = checklistAssignee;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
