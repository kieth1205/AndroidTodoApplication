package com.thang.noteapp.common.eventbus;

public class RemoveWordsEvent {
    public String action;
    public int position;

    public RemoveWordsEvent(String action, int position) {
        this.action = action;
        this.position = position;
    }
}
