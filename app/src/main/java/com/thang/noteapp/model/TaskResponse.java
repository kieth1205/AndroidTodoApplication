package com.thang.noteapp.model;

import java.util.List;

public class TaskResponse {

    private String id;
    private String title;
    private List<ChildrenTaskResponse> childrenTasks;
    private int progress;

    public TaskResponse() {
    }

    public TaskResponse(String id, String title, List<ChildrenTaskResponse> childrenTasks, int progress) {
        this.id = id;
        this.title = title;
        this.childrenTasks = childrenTasks;
        this.progress = progress;
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

    public List<ChildrenTaskResponse> getChildrenTasks() {
        return childrenTasks;
    }

    public void setChildrenTasks(List<ChildrenTaskResponse> childrenTasks) {
        this.childrenTasks = childrenTasks;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }
}
