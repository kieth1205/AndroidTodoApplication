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
import com.thang.noteapp.net.interfaces.TasksStatus;
import com.thang.noteapp.net.response.ChildTaskResponse;
import com.thang.noteapp.net.response.TasksResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FireBaseManager {

    private FireBaseUtils utils = new FireBaseUtils();
    private List<TasksResponse> itemTasks = new ArrayList<>();
    private TasksStatus tasksStatus;

    public void setTasks(TasksStatus tasksStatus) {
        this.tasksStatus = tasksStatus;
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
}
