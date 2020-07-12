package com.thang.noteapp.net.interfaces;

import com.thang.noteapp.net.response.TasksResponse;

import java.util.List;

public interface TasksStatus {
    void getData(List<TasksResponse> item);
}
