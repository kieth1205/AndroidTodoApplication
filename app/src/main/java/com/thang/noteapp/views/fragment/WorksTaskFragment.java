package com.thang.noteapp.views.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.thang.noteapp.R;
import com.thang.noteapp.common.Constants;
import com.thang.noteapp.common.eventbus.CloseDialogEvent;
import com.thang.noteapp.common.eventbus.DataTask;
import com.thang.noteapp.common.eventbus.EventBusAction;
import com.thang.noteapp.common.eventbus.SetTagEvent;
import com.thang.noteapp.controller.WordsDetailAdapter;
import com.thang.noteapp.net.response.TagRespont;
import com.thang.noteapp.net.response.TasksResponse;
import com.thang.noteapp.views.dialog.DlSetTag;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

public class WorksTaskFragment extends BaseFragment {

    @BindView(R.id.rv_words)
    RecyclerView rvWords;
    @BindView(R.id.bg_save)
    Button bgSave;

    private TasksResponse item;
    private WordsDetailAdapter adapter;
    private DlSetTag dlSetTag;

    public static WorksTaskFragment newInstance() {
        WorksTaskFragment fragment = new WorksTaskFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_works_task;
    }

    @Override
    protected void initializeViews(View view, Bundle savedInstanceState) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN_ORDERED, sticky = true)
    public void handleDataTask(DataTask event) {
        if (event.action.equals(EventBusAction.Tasks.DATA_TASK)) {
            item = event.data;

            adapter = new WordsDetailAdapter(getContext(), item.getChildrenTasks(), item.getId());
            rvWords.setHasFixedSize(true);
            rvWords.setLayoutManager(new LinearLayoutManager(getContext()));
            rvWords.setAdapter(adapter);
        }
    }

    @Subscribe
    public void handleSetTag(SetTagEvent event) {
        if (event.action.equals(EventBusAction.Tag.SET_TAG)) {
            TagRespont tagRespont = new TagRespont(Constants.FireBase.TODO_LIST, String.valueOf(event.task.getId()), null, event.task.getName());
            dlSetTag = new DlSetTag(getActivity(), tagRespont);
            dlSetTag.show();
        }
    }

    @Subscribe
    public void handleCloseDialog(CloseDialogEvent event) {
        if (event.action.equals(EventBusAction.Tasks.CLOSE_DIALOG)) {
            dlSetTag.dismiss();

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

    @OnClick(R.id.bg_save)
    public void onViewClicked() {
    }
}