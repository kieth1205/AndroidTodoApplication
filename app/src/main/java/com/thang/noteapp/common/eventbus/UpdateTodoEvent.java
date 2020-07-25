package com.thang.noteapp.common.eventbus;

import com.thang.noteapp.net.response.TodoResponse;

public class UpdateTodoEvent {
    public String action;
    public TodoResponse item;

    public UpdateTodoEvent(String action, TodoResponse item) {
        this.action = action;
        this.item = item;
    }
}
