package com.thang.noteapp.common.evenbus;

public class FillWordEvent {
    public String action;
    public int id;
    public String data;

    public FillWordEvent(String action, int id, String data) {
        this.action = action;
        this.id = id;
        this.data = data;
    }
}
