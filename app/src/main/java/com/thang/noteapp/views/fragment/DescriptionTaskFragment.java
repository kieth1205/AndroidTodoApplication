package com.thang.noteapp.views.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.thang.noteapp.R;
import com.thang.noteapp.common.eventbus.DataTask;
import com.thang.noteapp.common.eventbus.EventBusAction;
import com.thang.noteapp.net.response.TasksResponse;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;

public class DescriptionTaskFragment extends BaseFragment {

    @BindView(R.id.tv_description)
    TextView tvDescription;
    private TasksResponse item;

    public static DescriptionTaskFragment newInstance() {
        DescriptionTaskFragment fragment = new DescriptionTaskFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_description_task;
    }

    @Override
    protected void initializeViews(View view, Bundle savedInstanceState) {
    }

    @Subscribe(threadMode = ThreadMode.MAIN_ORDERED, sticky = true)
    public void handleDataTask(DataTask event) {
        if (event.action.equals(EventBusAction.Tasks.DATA_TASK)) {
            item = event.data;

            tvDescription.setText(item.getDescription());
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