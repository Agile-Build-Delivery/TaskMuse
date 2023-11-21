package com.taskmuse.app.utils;

public class Task {
    private String taskName;
    private String assignee;
    private String description;
    private String priority;
    private String projectName;

    // Empty constructor for Firebase
    public Task() {
    }

    public Task(String taskName, String assignee, String description, String priority, String projectName) {
        this.taskName = taskName;
        this.assignee = assignee;
        this.description = description;
        this.priority = priority;
        this.projectName = projectName;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
}
