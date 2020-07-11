package com.thang.nodeapp.model;

public class ChildrenTaskRespont {

    private String id;
    private String name;
    private String content;
    private int progress;

    public ChildrenTaskRespont() {
    }

    public ChildrenTaskRespont(String id, String name, String content, int progress) {
        this.id = id;
        this.name = name;
        this.content = content;
        this.progress = progress;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }
}

