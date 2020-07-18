package com.thang.noteapp.views.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.thang.noteapp.R;
import com.thang.noteapp.common.eventbus.CloseDialogEvent;
import com.thang.noteapp.common.eventbus.EventBusAction;
import com.thang.noteapp.controller.TasksAdapter;
import com.thang.noteapp.net.FireBaseManager;
import com.thang.noteapp.net.interfaces.TasksStatus;
import com.thang.noteapp.net.response.TasksResponse;
import com.thang.noteapp.views.dialog.DlAddTasks;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class TaskListFragment extends BaseFragment {

    @BindView(R.id.rv_list_task)
    RecyclerView rvListTask;
    @BindView(R.id.txt_no_data)
    TextView txtNoData;
    @BindView(R.id.fab_task_list)
    FloatingActionButton fabTaskList;

    private DlAddTasks dlAddTasks;
    private FireBaseManager fireBaseManager = new FireBaseManager();
    private List<TasksResponse> list = new ArrayList<>();
    private TasksAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_task_list;
    }

    @Override
    protected void initializeViews(View view, Bundle savedInstanceState) {
        fireBaseManager.getAddTasks(getContext());
        this.setUpData();
    }

    public void setUpData() {
        RecyclerView.LayoutManager manager = new GridLayoutManager(getContext(), 1);
        rvListTask.setHasFixedSize(true);
        rvListTask.setLayoutManager(manager);

        fireBaseManager.setTasks(new TasksStatus() {
            @Override
            public void getData(List<TasksResponse> item) {
                list = item;
                if (list != null) {
                    if (adapter == null) {
                        adapter = new TasksAdapter(getContext(), list);
                        rvListTask.setAdapter(adapter);
                    } else {
                        adapter.update(list);
                    }
                }
            }
        });
    }

    @OnClick(R.id.fab_task_list)
    public void onViewClicked() {
        dlAddTasks = new DlAddTasks(getActivity());
        dlAddTasks.show();
    }

    @Subscribe
    public void handleCloseDialog(CloseDialogEvent event) {
        if (event.action.equals(EventBusAction.Tasks.CLOSE_DIALOG)) {
            dlAddTasks.dismiss();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}