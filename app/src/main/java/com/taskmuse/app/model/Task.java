package com.taskmuse.app.model;

public class Task {
    private String id;
    private String taskName;
    private String assignee;
    private String description;
    private String priority;
    private String projectName;

    // Default constructor required for calls to DataSnapshot.getValue(Task.class)
    public Task() {
    }

    public Task(String taskName, String assignee, String description, String priority, String projectName) {
        this.taskName = taskName;
        this.assignee = assignee;
        this.description = description;
        this.priority = priority;
        this.projectName = projectName;
    }

    // Getter and Setter methods for all fields, including 'id'

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

