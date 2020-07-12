package com.thang.noteapp.net;

import android.annotation.SuppressLint;
import android.content.Context;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.thang.noteapp.common.Constants;
import com.thang.noteapp.common.utils.FireBaseUtils;
import com.thang.noteapp.net.interfaces.TasksStatus;
import com.thang.noteapp.net.response.TasksResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FireBaseManager {

//    private String android_id = Settings.Secure.getString(context.getContentResolver(),
//            Settings.Secure.ANDROID_ID);

    private FireBaseUtils utils = new FireBaseUtils();
//    private DatabaseReference mTasks = FirebaseDatabase.getInstance().getReference(android_id).child(Constants.FireBase.TASK_LIST);

    private List<TasksResponse> itemTasks = new ArrayList<>();

    private TasksStatus tasksStatus;

    public void insertTasks(Context context ,TasksResponse task) {
        @SuppressLint("HardwareIds") String android_id = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        DatabaseReference mTasks = FirebaseDatabase.getInstance().getReference(android_id).child(Constants.FireBase.TASK_LIST);

        String key = mTasks.push().getKey();
        task.setId(key);
        utils.insert(task.getId(), mTasks, task);
    }

    public void updateTask(TasksResponse task, Context context) {
        @SuppressLint("HardwareIds") String android_id = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        DatabaseReference mTasks = FirebaseDatabase.getInstance().getReference(android_id).child(Constants.FireBase.TASK_LIST);
        assert task != null;
        utils.update(task.getId(), mTasks, task);
    }

    public void deleteTask(TasksResponse task, Context context) {
        @SuppressLint("HardwareIds") String android_id = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        DatabaseReference mTasks = FirebaseDatabase.getInstance().getReference(android_id).child(Constants.FireBase.TASK_LIST);
        assert task != null;
        utils.delete(task.getId(), mTasks);
    }

    public void getAddTasks(Context context) {
        @SuppressLint("HardwareIds") String android_id = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        DatabaseReference mTasks = FirebaseDatabase.getInstance().getReference(android_id).child(Constants.FireBase.TASK_LIST);

        mTasks.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                itemTasks.clear();
                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    TasksResponse productResponse = childDataSnapshot.getValue(TasksResponse.class);
                    itemTasks.add(productResponse);
                }
                tasksStatus.getData(itemTasks);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(Constants.TAG, "onCancelled: " + databaseError);
            }
        });
    }
}
