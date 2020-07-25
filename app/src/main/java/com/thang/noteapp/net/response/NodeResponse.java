package com.thang.noteapp.net.response;

public class NodeResponse {

    private String id;
    private String content;

    public NodeResponse(String content) {
        this.content = content;
    }

    public NodeResponse() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
