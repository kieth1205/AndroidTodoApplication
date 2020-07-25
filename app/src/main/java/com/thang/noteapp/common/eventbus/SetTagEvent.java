package com.thang.noteapp.common.eventbus;

import com.thang.noteapp.net.response.ChildTaskResponse;
import com.thang.noteapp.net.response.TodoResponse;

public class SetTagEvent {
    public String action;
    public ChildTaskResponse task;
    public TodoResponse todo;

    public SetTagEvent(String action, ChildTaskResponse task, TodoResponse todo) {
        this.action = action;
        this.task = task;
        this.todo = todo;
    }
}
