package com.thang.noteapp.views.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.thang.noteapp.R;
import com.thang.noteapp.controller.NodeAdapter;
import com.thang.noteapp.net.FireBaseManager;
import com.thang.noteapp.net.interfaces.NodeStatus;
import com.thang.noteapp.net.response.NodeResponse;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class NoteListFragment extends BaseFragment {

    @BindView(R.id.rv_list_task)
    RecyclerView rvListTask;
    @BindView(R.id.fab_note_list)
    FloatingActionButton fabNoteList;

    private List<NodeResponse> items = new ArrayList<>();
    private NodeAdapter adapter;
    private FireBaseManager fireBaseManager = new FireBaseManager();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_note_list;
    }

    @Override
    protected void initializeViews(View view, Bundle savedInstanceState) {
        fireBaseManager.getAllNode(getContext());
        this.setup();
    }

    private void setup() {
        rvListTask.setHasFixedSize(true);
        rvListTask.setLayoutManager(new LinearLayoutManager(getContext()));
        fireBaseManager.setNote(new NodeStatus() {
            @Override
            public void getData(List<NodeResponse> item) {
                items = item;
                if (items != null) {
                    if (adapter == null) {
                        adapter = new NodeAdapter(getContext(), items);
                        rvListTask.setAdapter(adapter);
                    } else {
                        adapter.update(items);
                    }
                }
            }
        });
    }

    @OnClick(R.id.fab_note_list)
    public void onViewClicked() {
        NodeResponse nodeResponse = new NodeResponse("");
        String key = fireBaseManager.insertNode(getContext(),)
    }
}