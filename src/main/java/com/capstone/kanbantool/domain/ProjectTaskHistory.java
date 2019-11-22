package com.capstone.kanbantool.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class ProjectTaskHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "projectTask_id", nullable = false)
    @JsonIgnore
    private ProjectTask projectTaskForHistory;

    private String attributeName;

    private String oldValue;

    private String updatedValue;

    //@JsonFormat(pattern = "yyyy-mm-dd")
    public Date createdAt;

    @PrePersist
    protected void onCreate(){
        this.createdAt = new Date();
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    // to get username who has changed ProjectTask details

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User user;

    private String username;


    public ProjectTaskHistory() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProjectTask getProjectTaskForHistory() {
        return projectTaskForHistory;
    }

    public void setProjectTaskForHistory(ProjectTask projectTaskForHistory) {
        this.projectTaskForHistory = projectTaskForHistory;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public String getOldValue() {
        return oldValue;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    public String getUpdatedValue() {
        return updatedValue;
    }

    public void setUpdatedValue(String updatedValue) {
        this.updatedValue = updatedValue;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
