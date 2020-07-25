package com.thang.noteapp.net.interfaces;

import com.thang.noteapp.net.response.NodeResponse;

import java.util.List;

public interface NodeStatus {
    void getData(List<NodeResponse> item);
}
