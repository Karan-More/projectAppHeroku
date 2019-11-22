package com.capstone.kanbantool.exception;

public class ProjectNotFoundExceptionResponce {

    private String ProjectNotFound;

    public ProjectNotFoundExceptionResponce(String projectNotFound) {
        ProjectNotFound = projectNotFound;
    }

    public String getProjectNotFound() {
        return ProjectNotFound;
    }

    public void setProjectNotFound(String projectNotFound) {
        ProjectNotFound = projectNotFound;
    }
}
