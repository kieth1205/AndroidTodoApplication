package com.thang.noteapp.views.dialog;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.thang.noteapp.R;
import com.thang.noteapp.common.eventbus.CloseDialogEvent;
import com.thang.noteapp.common.eventbus.EventBusAction;
import com.thang.noteapp.net.FireBaseManager;
import com.thang.noteapp.net.response.TodoResponse;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DlAddTodo extends Dialog {

    private EditText edtName;
    private ImageView imgCalendar;
    private TextView edtStartDate;
    private Button btnSave;
    private Button btnClear;
    private String myFormat = "MM/dd/yyyy";
    private SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
    private FireBaseManager fireBaseManager = new FireBaseManager();
    private TodoResponse item;

    private Calendar myCalendar = Calendar.getInstance();
    private DatePickerDialog.OnDateSetListener date;

    public DlAddTodo(Context a, TodoResponse item) {
        super(a);
        this.item = item;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dl_add_todo);

        this.initDate();
        this.init();
    }

    private void updateDate() {
        edtStartDate.setText(sdf.format(myCalendar.getTime()));
    }

    private void initDate() {
        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDate();
            }

        };
    }

    private void init() {
        edtName = findViewById(R.id.edt_name);
        imgCalendar = findViewById(R.id.img_calendar);
        edtStartDate = findViewById(R.id.edt_start_date);
        btnSave = findViewById(R.id.btn_save);
        btnClear = findViewById(R.id.btn_clear);

        imgCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item != null) {
                    item.setName(edtName.getText().toString());
                    item.setDateStart(myCalendar.getTime());
                    fireBaseManager.updateTodo(item, getContext());
                } else {
                    Date date1 = myCalendar.getTime();
                    String name = edtName.getText().toString();
                    TodoResponse response = new TodoResponse(name, date1);
                    fireBaseManager.insertTodo(getContext(), response);
                }
                EventBus.getDefault().post(new CloseDialogEvent(EventBusAction.Tasks.CLOSE_DIALOG));
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new CloseDialogEvent(EventBusAction.Tasks.CLOSE_DIALOG));
            }
        });

        if (item != null) {
            edtName.setText(item.getName());
            edtStartDate.setText(sdf.format(item.getDateStart()));
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
//        if (!EventBus.getDefault().isRegistered(this)) {
//            EventBus.getDefault().register(this);
//        }
    }

    @Override
    protected void onStop() {
        super.onStop();
//        if (EventBus.getDefault().isRegistered(this)) {
//            EventBus.getDefault().unregister(this);
//        }
    }
}
