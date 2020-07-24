package com.thang.noteapp.views.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.thang.noteapp.R;
import com.thang.noteapp.common.Constants;
import com.thang.noteapp.common.eventbus.CloseDialogEvent;
import com.thang.noteapp.common.eventbus.EventBusAction;
import com.thang.noteapp.common.eventbus.SetTagEvent;
import com.thang.noteapp.common.eventbus.UpdateTodoEvent;
import com.thang.noteapp.controller.TodoApdater;
import com.thang.noteapp.net.FireBaseManager;
import com.thang.noteapp.net.interfaces.TodosStatus;
import com.thang.noteapp.net.response.TagRespont;
import com.thang.noteapp.net.response.TodoResponse;
import com.thang.noteapp.views.dialog.DlAddTodo;
import com.thang.noteapp.views.dialog.DlSetTag;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class TodoListFragment extends BaseFragment {

    @BindView(R.id.rv_list_todo)
    RecyclerView rvListTodo;
    @BindView(R.id.fab_todo_list)
    FloatingActionButton fabTodoList;
    private FireBaseManager fireBaseManager = new FireBaseManager();
    private TodoApdater adapter;
    private List<TodoResponse> items = new ArrayList<>();
    private DlAddTodo dlAddTodo;
    private DlSetTag dlSetTag;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_todo_list;
    }

    @Override
    protected void initializeViews(View view, Bundle savedInstanceState) {
        fireBaseManager.getAllTodo(getContext());
        this.setup();
    }

    private void setup() {
        RecyclerView.LayoutManager manager = new GridLayoutManager(getContext(), 1);
        rvListTodo.setHasFixedSize(true);
        rvListTodo.setLayoutManager(manager);

        fireBaseManager.setTodo(new TodosStatus() {
            @Override
            public void getData(List<TodoResponse> item) {
                items = item;
                if (items != null) {
                    if (adapter == null) {
                        adapter = new TodoApdater(getContext(), items);
                        rvListTodo.setAdapter(adapter);
                    } else {
                        adapter.update(items);
                    }
                }
            }
        });
    }

    @OnClick(R.id.fab_todo_list)
    public void onViewClicked() {
        dlAddTodo = new DlAddTodo(getActivity(), null);
        dlAddTodo.show();
    }

    @Subscribe
    public void handleCloseDialog(CloseDialogEvent event) {
        if (event.action.equals(EventBusAction.Tasks.CLOSE_DIALOG)) {
            if (dlAddTodo.isShowing()){
                dlAddTodo.dismiss();
            } if (dlSetTag.isShowing()){
                dlSetTag.dismiss();
            }
        }
    }

    @Subscribe
    public void handleUpdateTodo(UpdateTodoEvent event) {
        if (event.action.equals(EventBusAction.Todo.UPDATE_TODO)) {
            dlAddTodo = new DlAddTodo(getActivity(), event.item);
            dlAddTodo.show();
        }
    }

    @Subscribe
    public void handleSetTag(SetTagEvent event) {
        if (event.action.equals(EventBusAction.Tag.SET_TAG)) {
            TagRespont tagRespont = new TagRespont(Constants.FireBase.TODO_LIST,event.todo.getId(),null,event.todo.getName());
            dlSetTag = new DlSetTag(getActivity(), tagRespont);
            dlSetTag.show();
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