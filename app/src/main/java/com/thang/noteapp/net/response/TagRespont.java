package com.thang.noteapp.net.response;

public class TagRespont {
    private String id;
    private String source;
    private int status;
    private String childId;
    private String childOrChildId;
    private String childName;

    public TagRespont() {
    }

    public TagRespont(String source, String childId, String childOrChildId, String childName) {
        this.source = source;
        this.childId = childId;
        this.childOrChildId = childOrChildId;
        this.childName = childName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getChildId() {
        return childId;
    }

    public void setChildId(String childId) {
        this.childId = childId;
    }

    public String getChildName() {
        return childName;
    }

    public void setChildName(String childName) {
        this.childName = childName;
    }
}
