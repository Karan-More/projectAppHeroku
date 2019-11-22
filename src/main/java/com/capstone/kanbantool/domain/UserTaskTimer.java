package com.capstone.kanbantool.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@Entity
public class UserTaskTimer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ProjectTask <-----> UserTaskTimer
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "projectTask_id", nullable = false)
    @JsonIgnore
    public ProjectTask projectTaskForUserTimer;

    public String projectTaskSequence;

    public String getProjectTaskSequence() {
        return projectTaskSequence;
    }

    public void setProjectTaskSequence(String projectTaskSequence) {
        this.projectTaskSequence = projectTaskSequence;
    }

    private Long totalMinutesSpent;

    public Long getTotalMinutesSpent() {
        return totalMinutesSpent;
    }

    public void setTotalMinutesSpent(Long totalMinutesSpent) {
        this.totalMinutesSpent = totalMinutesSpent;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User user;

    public UserTaskTimer() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProjectTask getProjectTaskForUserTimer() {
        return projectTaskForUserTimer;
    }

    public void setProjectTaskForUserTimer(ProjectTask projectTaskForUserTimer) {
        this.projectTaskForUserTimer = projectTaskForUserTimer;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    //@JsonFormat(pattern = "yyyy-mm-dd")
    @Column(updatable = false)
    public Date createdAt;

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @PrePersist
    protected void onCreate(){

        this.createdAt = new Date();
    }

    //@JsonFormat(pattern = "yyyy-mm-dd")
    public Date updatedAt;

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
