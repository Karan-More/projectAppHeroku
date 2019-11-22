package com.capstone.kanbantool.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@Entity
public class ProjectTaskTimer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@JsonFormat(pattern = "yyyy-mm-dd")
    private String timeInToDo;

    //@JsonFormat(pattern = "yyyy-mm-dd")
    private String timeInProgress;

   // @JsonFormat(pattern = "yyyy-mm-dd")
    private String timeInDone;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="projectTask_id", nullable = false)
    @JsonIgnore
    private ProjectTask projectTask;

    //@JsonFormat(pattern = "yyyy-mm-dd")
    public Date updatedAt;

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public ProjectTaskTimer() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTimeInToDo() {
        return timeInToDo;
    }

    public void setTimeInToDo(String timeInToDo) {
        this.timeInToDo = timeInToDo;
    }

    public String getTimeInProgress() {
        return timeInProgress;
    }

    public void setTimeInProgress(String timeInProgress) {
        this.timeInProgress = timeInProgress;
    }

    public String getTimeInDone() {
        return timeInDone;
    }

    public void setTimeInDone(String timeInDone) {
        this.timeInDone = timeInDone;
    }

    public ProjectTask getProjectTask() {
        return projectTask;
    }

    public void setProjectTask(ProjectTask projectTask) {
        this.projectTask = projectTask;
    }
}
