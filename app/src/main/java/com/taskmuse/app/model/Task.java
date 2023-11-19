package com.taskmuse.app.model;

public class Task {
    private String id;
    private String taskDescription;
    private String taskName;
    private String assignee;
    private String priority;

    // Default constructor required for calls to DataSnapshot.getValue(Task.class)
    public Task() {
    }

    public Task(String taskDescription, String taskName, String assignee, String priority) {
        this.taskDescription = taskDescription;
        this.taskName = taskName;
        this.assignee = assignee;
        this.priority = priority;
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

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
}

