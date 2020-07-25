package com.thang.noteapp.net.response;

import java.util.Date;

public class TodoResponse {

    private String id;
    private String name;
    private Date dateStart;
    private int status = 0;
    private int tag_status = 0;

    public TodoResponse() {
    }

    public TodoResponse(String name, Date dateStart) {
        this.name = name;
        this.dateStart = dateStart;
    }

    public TodoResponse(String name, Date dateStart, int status, int tag_status) {
        this.name = name;
        this.dateStart = dateStart;
        this.status = status;
        this.tag_status = tag_status;
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

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getTag_status() {
        return tag_status;
    }

    public void setTag_status(int tag_status) {
        this.tag_status = tag_status;
    }
}
