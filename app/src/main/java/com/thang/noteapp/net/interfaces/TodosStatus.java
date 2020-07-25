package com.thang.noteapp.net.interfaces;

import com.thang.noteapp.net.response.TodoResponse;

import java.util.List;

public interface TodosStatus {
    void getData(List<TodoResponse> item);
}
