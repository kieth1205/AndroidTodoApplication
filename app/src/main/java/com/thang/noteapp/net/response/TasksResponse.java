package com.thang.noteapp.net.response;

import java.util.List;

public class TasksResponse {

    private String id;
    private String title;
    private List<ChildTaskResponse> childrenTasks;
    private int progress = 0;
    private int prioritize = 5;
    private String description;

    public TasksResponse() {
    }

    public TasksResponse(String title, List<ChildTaskResponse> childrenTasks, int prioritize, String description) {
        this.title = title;
        this.childrenTasks = childrenTasks;
        this.prioritize = prioritize;
        this.description = description;
    }

    public TasksResponse(String title, List<ChildTaskResponse> childrenTasks, int progress, int prioritize, String description) {
        this.title = title;
        this.childrenTasks = childrenTasks;
        this.progress = progress;
        this.prioritize = prioritize;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ChildTaskResponse> getChildrenTasks() {
        return childrenTasks;
    }

    public void setChildrenTasks(List<ChildTaskResponse> childrenTasks) {
        this.childrenTasks = childrenTasks;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public int getPrioritize() {
        return prioritize;
    }

    public void setPrioritize(int prioritize) {
        this.prioritize = prioritize;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
