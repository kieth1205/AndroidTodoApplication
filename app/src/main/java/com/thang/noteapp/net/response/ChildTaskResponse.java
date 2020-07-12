package com.thang.noteapp.net.response;

public class ChildTaskResponse {

    private int id;
    private String name;
    private int progress = 0;

    public ChildTaskResponse() {
    }

    public ChildTaskResponse(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }
}

