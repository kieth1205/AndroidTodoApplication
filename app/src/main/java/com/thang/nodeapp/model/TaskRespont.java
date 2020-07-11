package com.thang.nodeapp.model;

import java.util.List;

public class TaskRespont {

    private String id;
    private String title;
    private List<ChildrenTaskRespont> childrenTasks;
    private int progress;

    public TaskRespont() {
    }

    public TaskRespont(String id, String title, List<ChildrenTaskRespont> childrenTasks, int progress) {
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

    public List<ChildrenTaskRespont> getChildrenTasks() {
        return childrenTasks;
    }

    public void setChildrenTasks(List<ChildrenTaskRespont> childrenTasks) {
        this.childrenTasks = childrenTasks;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }
}
