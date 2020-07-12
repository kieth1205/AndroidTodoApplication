package com.thang.noteapp.views.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.thang.noteapp.R;
import com.thang.noteapp.common.evenbus.CloseDialogEvent;
import com.thang.noteapp.common.evenbus.EventBusAction;
import com.thang.noteapp.views.dialog.DlAddTasks;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
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

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_task_list;
    }

    @Override
    protected void initializeViews(View view, Bundle savedInstanceState) {

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