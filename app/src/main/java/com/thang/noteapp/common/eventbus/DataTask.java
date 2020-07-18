package com.thang.noteapp.common.eventbus;

import com.thang.noteapp.net.response.TasksResponse;

public class DataTask {
    public String action;
    public TasksResponse data;

    public DataTask(String action, TasksResponse data) {
        this.action = action;
        this.data = data;
    }
}
