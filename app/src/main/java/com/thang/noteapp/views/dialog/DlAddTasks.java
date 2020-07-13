package com.thang.noteapp.views.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.thang.noteapp.R;
import com.thang.noteapp.common.evenbus.CloseDialogEvent;
import com.thang.noteapp.common.evenbus.EventBusAction;
import com.thang.noteapp.common.evenbus.FillWordEvent;
import com.thang.noteapp.common.evenbus.RemoveWordsEvent;
import com.thang.noteapp.controller.WordsAdapter;
import com.thang.noteapp.net.FireBaseManager;
import com.thang.noteapp.net.response.ChildTaskResponse;
import com.thang.noteapp.net.response.TasksResponse;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class DlAddTasks extends Dialog {

    private EditText edtTitle;
    private Spinner spPrioritize;
    private ImageView imgAddWord;
    private RecyclerView rvWords;
    private EditText edtDetail;
    private Button btnSave;
    private Button btnClear;

    public DlAddTasks(Activity a) {
        super(a);
    }

    private List<ChildTaskResponse> tasks = new ArrayList<>();
    private String array_spinner[];
    private WordsAdapter adapter;
    private int i = 0;
    private FireBaseManager fireBaseManager = new FireBaseManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_add_tasks);

        this.init();
        this.setUpSpinner();
        this.click();
        this.setUpListWord();
    }

    private void init() {
        edtTitle = findViewById(R.id.edt_title);
        spPrioritize = findViewById(R.id.sp_prioritize);
        imgAddWord = findViewById(R.id.img_add_word);
        rvWords = findViewById(R.id.rv_words);
        edtDetail = findViewById(R.id.edt_detail);
        btnSave = findViewById(R.id.btn_save);
        btnClear = findViewById(R.id.btn_clear);
    }

    private void setUpSpinner() {
        array_spinner = new String[5];
        array_spinner[0] = "1";
        array_spinner[1] = "2";
        array_spinner[2] = "3";
        array_spinner[3] = "4";
        array_spinner[4] = "5";

        ArrayAdapter adapter = new ArrayAdapter(getContext(),
                android.R.layout.simple_spinner_item, array_spinner);

        spPrioritize.setAdapter(adapter);

    }

    private void setUpListWord() {
        adapter = new WordsAdapter(getContext(), tasks);
        rvWords.setLayoutManager(new LinearLayoutManager(getContext()));
        rvWords.setHasFixedSize(true);
        rvWords.setAdapter(adapter);
        editList(0, 0);
    }

    private void click() {
        imgAddWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i++;
                editList(0, 0);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = edtTitle.getText().toString();
                String description = edtDetail.getText().toString();
                int prioritize = Integer.parseInt(spPrioritize.getSelectedItem().toString());
                TasksResponse tasksResponse = new TasksResponse(title,tasks,prioritize,description);
                fireBaseManager.insertTasks(getContext(), tasksResponse);
                Toast.makeText(getContext(), "Saved!!!!", Toast.LENGTH_SHORT).show();
                EventBus.getDefault().post(new CloseDialogEvent(EventBusAction.Tasks.CLOSE_DIALOG));
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new CloseDialogEvent(EventBusAction.Tasks.CLOSE_DIALOG));
            }
        });
    }

    private void editList(int type, int position) {
        if (type == 0) {
            tasks.add(new ChildTaskResponse(i, ""));
        } else {
            tasks.remove(position);
        }
        adapter.update(tasks);
    }

    @Subscribe
    public void handleUpdateListWordsRemote(RemoveWordsEvent event) {
        if (event.action.equals(EventBusAction.Tasks.REMOVE_WORDS)) {
            editList(1, event.position);
        }
    }

    @Subscribe
    public void handleUpdateListWords(FillWordEvent event) {
        if (event.action.equals(EventBusAction.Tasks.FILL_WORDS)) {
            for (int i = 0; i < tasks.size(); i++) {
                if (tasks.get(i).getId() == event.id) {
                    tasks.get(i).setName(event.data);
                }
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
