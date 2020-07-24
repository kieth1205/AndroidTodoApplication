package com.thang.noteapp.net;

import android.annotation.SuppressLint;
import android.content.Context;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.thang.noteapp.common.Constants;
import com.thang.noteapp.common.utils.FireBaseUtils;
import com.thang.noteapp.net.interfaces.TagsStatus;
import com.thang.noteapp.net.interfaces.TasksStatus;
import com.thang.noteapp.net.interfaces.TodosStatus;
import com.thang.noteapp.net.response.ChildTaskResponse;
import com.thang.noteapp.net.response.TagRespont;
import com.thang.noteapp.net.response.TasksResponse;
import com.thang.noteapp.net.response.TodoResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FireBaseManager {

    private FireBaseUtils utils = new FireBaseUtils();
    private List<TasksResponse> itemTasks = new ArrayList<>();
    private List<TodoResponse> itemTodos = new ArrayList<>();
    private List<TagRespont> itemTag = new ArrayList<>();
    private TasksStatus tasksStatus;
    private TodosStatus todosStatus;
    private TagsStatus tagsStatus;

    public void setTasks(TasksStatus tasksStatus) {
        this.tasksStatus = tasksStatus;
    }

    public void setTodo(TodosStatus todosStatus) {
        this.todosStatus = todosStatus;
    }

    public void setTags(TagsStatus tagsStatus){
        this.tagsStatus = tagsStatus;
    }

    public void insertTasks(Context context, TasksResponse task) {
        DatabaseReference mTasks = this.getTasksRefernce(context);
        String key = mTasks.push().getKey();
        task.setId(key);
        utils.insert(task.getId(), mTasks, task);
    }

    public void updateTask(TasksResponse task, Context context) {
        DatabaseReference mTasks = this.getTasksRefernce(context);
        assert task != null;
        utils.update(task.getId(), mTasks, task);
    }

    public void deleteTask(TasksResponse task, Context context) {
        DatabaseReference mTasks = this.getTasksRefernce(context);
        assert task != null;
        utils.delete(task.getId(), mTasks);
    }

    public void getAddTasks(Context context) {
        DatabaseReference mTasks = this.getTasksRefernce(context);
        mTasks.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                itemTasks.clear();
                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    TasksResponse tasksResponse = childDataSnapshot.getValue(TasksResponse.class);
                    itemTasks.add(tasksResponse);
                }
                Collections.sort(itemTasks, new Comparator<TasksResponse>() {
                    @Override
                    public int compare(TasksResponse o1, TasksResponse o2) {
                        return String.valueOf(o2.getPrioritize()).compareTo(String.valueOf(o1.getPrioritize()));
                    }
                });
                tasksStatus.getData(itemTasks);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(Constants.TAG, "onCancelled: " + databaseError);
            }
        });
    }

    public void updateWord(Context context, ChildTaskResponse response, String key_extent) {
        DatabaseReference mWork = this.getTasksRefernce(context).child(key_extent).child("childrenTasks").child(String.valueOf(response.getId()));
        mWork.setValue(response);
    }

    public void deleteWork(Context context, ChildTaskResponse response, String key_extent) {
        DatabaseReference mWork = this.getTasksRefernce(context).child(key_extent).child("childrenTasks").child(String.valueOf(response.getId()));
        mWork.removeValue();
    }

    public void insertTodo(Context context, TodoResponse todoResponse) {
        DatabaseReference mTodos = this.getTodosRefernce(context);
        String key = mTodos.push().getKey();
        todoResponse.setId(key);
        utils.insert(todoResponse.getId(), mTodos, todoResponse);
    }

    public void updateTodo(TodoResponse todo, Context context) {
        DatabaseReference mTodos = this.getTodosRefernce(context);
        assert todo != null;
        utils.update(todo.getId(), mTodos, todo);
    }

    public void deleteTodo(TodoResponse todo, Context context) {
        DatabaseReference mTodos = this.getTodosRefernce(context);
        assert todo != null;
        utils.delete(todo.getId(), mTodos);
    }

    public void getAllTodo(Context context) {
        DatabaseReference mTodos = this.getTodosRefernce(context);
        mTodos.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                itemTodos.clear();
                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    TodoResponse todoResponse = childDataSnapshot.getValue(TodoResponse.class);
                    itemTodos.add(todoResponse);
                }
//                Collections.sort(itemTodos, new Comparator<TodoResponse>() {
//                    @Override
//                    public int compare(TodoResponse o1, TodoResponse o2) {
//                        return String.valueOf(o2.getDateStart()).compareTo(String.valueOf(o1.getDateStart()));
//                    }
//                });
                todosStatus.getData(itemTodos);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(Constants.TAG, "onCancelled: " + databaseError);
            }
        });
    }

    public void insertTag(Context context, TagRespont tagRespont) {
        DatabaseReference mTag = this.getTagRefernce(context);
        String key = mTag.push().getKey();
        tagRespont.setId(key);
        utils.insert(tagRespont.getId(), mTag, tagRespont);
    }

    public void updateTag(TagRespont tagRespont, Context context) {
        DatabaseReference mTag = this.getTagRefernce(context);
        assert tagRespont != null;
        utils.update(tagRespont.getId(), mTag, tagRespont);
    }

    public void deleteTag(TagRespont tagRespont, Context context) {
        DatabaseReference mTag = this.getTagRefernce(context);
        assert tagRespont != null;
        utils.delete(tagRespont.getId(), mTag);
    }

    public void getAllTag(Context context) {
        DatabaseReference mTag = this.getTagRefernce(context);
        mTag.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                itemTag.clear();
                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    TagRespont tagRespont = childDataSnapshot.getValue(TagRespont.class);
                    itemTag.add(tagRespont);
                }
                tagsStatus.getData(itemTag);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(Constants.TAG, "onCancelled: " + databaseError);
            }
        });
    }

    private String getAndroidId(Context context) {
        @SuppressLint("HardwareIds") String android_id = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        return android_id;
    }

    private DatabaseReference getTasksRefernce(Context context) {
        String androidId = this.getAndroidId(context);
        DatabaseReference mTasks = FirebaseDatabase.getInstance().getReference(androidId).child(Constants.FireBase.TASK_LIST);
        return mTasks;
    }

    private DatabaseReference getTodosRefernce(Context context) {
        String androidId = this.getAndroidId(context);
        DatabaseReference mTasks = FirebaseDatabase.getInstance().getReference(androidId).child(Constants.FireBase.TODO_LIST);
        return mTasks;
    }

    private DatabaseReference getTagRefernce(Context context) {
        String androidId = this.getAndroidId(context);
        DatabaseReference mTag = FirebaseDatabase.getInstance().getReference(androidId).child(Constants.FireBase.TAG);
        return mTag;
    }
}
