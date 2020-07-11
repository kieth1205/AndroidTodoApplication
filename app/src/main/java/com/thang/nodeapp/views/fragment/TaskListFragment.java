package com.thang.nodeapp.views.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.thang.nodeapp.R;

import butterknife.BindView;
import butterknife.OnClick;

public class TaskListFragment extends BaseFragment {


    @BindView(R.id.rv_list_task)
    RecyclerView rvListTask;
    @BindView(R.id.txt_no_data)
    TextView txtNoData;
    @BindView(R.id.fab_task_list)
    FloatingActionButton fabTaskList;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_task_list;
    }

    @Override
    protected void initializeViews(View view, Bundle savedInstanceState) {

    }

    @OnClick(R.id.fab_task_list)
    public void onViewClicked() {
    }
}