package com.thang.noteapp.views.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.thang.noteapp.R;
import com.thang.noteapp.common.eventbus.DataTask;
import com.thang.noteapp.common.eventbus.EventBusAction;
import com.thang.noteapp.controller.ChildTaskAdapter;
import com.thang.noteapp.controller.WordsDetailAdapter;
import com.thang.noteapp.net.response.TasksResponse;
import com.thang.noteapp.views.fragment.DescriptionTaskFragment;
import com.thang.noteapp.views.fragment.WorksTaskFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TaskActivity extends BaseActivity {


    @BindView(R.id.nav_left_button)
    ImageView navLeftButton;
    @BindView(R.id.nav_title)
    TextView navTitle;
    @BindView(R.id.tab_contact)
    TabLayout tabContact;
    @BindView(R.id.vp_task)
    ViewPager vpTask;
    @BindView(R.id.nav_right_text)
    TextView navRightText;
    @BindView(R.id.nav_percent)
    TextView navPercent;
    @BindView(R.id.nav_right_progress)
    LinearLayout navRightProgress;

    private ChildTaskAdapter taskAdapter;
    private TasksResponse item;

    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_task;
    }

    @Override
    protected void initialize(@Nullable Bundle savedInstanceState) {
        this.setUpTabs();
    }

    private void setUpTabs() {
        List<Fragment> mListFragments = new ArrayList<>();
        mListFragments.add(WorksTaskFragment.newInstance());
        mListFragments.add(DescriptionTaskFragment.newInstance());
        this.taskAdapter = new ChildTaskAdapter(getSupportFragmentManager(), TaskActivity.this, mListFragments);
        this.vpTask.setAdapter(taskAdapter);
        this.tabContact.setupWithViewPager(vpTask);
    }

    private void setUp() {
//        Intent intent = getIntent();
//        item = intent.getParcelableExtra("task");
        navTitle.setText(item.getTitle());
        navRightProgress.setVisibility(View.VISIBLE);
        navPercent.setVisibility(View.VISIBLE);
        navRightText.setText(String.valueOf(item.getProgress()));

        EventBus.getDefault().post(new DataTask(EventBusAction.Tasks.DATA_TASK, item));
    }

    @OnClick(R.id.nav_left_button)
    public void onViewClicked() {
    }

    @Subscribe(threadMode = ThreadMode.MAIN_ORDERED, sticky = true)
    public void handleDataTask(DataTask event) {
        if (event.action.equals(EventBusAction.Tasks.DATA_TASK)) {
            item = event.data;
            this.setUp();
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
