package com.thang.noteapp.views.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.annotation.NonNull;

import com.thang.noteapp.R;
import com.thang.noteapp.common.eventbus.CloseDialogEvent;
import com.thang.noteapp.common.eventbus.EventBusAction;
import com.thang.noteapp.net.FireBaseManager;
import com.thang.noteapp.net.response.TagRespont;

import org.greenrobot.eventbus.EventBus;

public class DlSetTag extends Dialog {

    private CheckBox cbCheckWork;
    private CheckBox cbCheckFamily;
    private CheckBox cbCheckOther;
    private Button btnSave;
    private Button btnClear;
    private TagRespont item;
    private FireBaseManager fireBaseManager = new FireBaseManager();

    public DlSetTag(@NonNull Context context, TagRespont tagRespont) {
        super(context);
        this.item = tagRespont;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dl_set_tag);

        this.init();

    }

    private void init() {
        cbCheckWork = findViewById(R.id.cb_check_work);
        cbCheckFamily = findViewById(R.id.cb_check_family);
        cbCheckOther = findViewById(R.id.cb_check_other);
        btnSave = findViewById(R.id.btn_save);
        btnClear = findViewById(R.id.btn_clear);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (cbCheckWork.isChecked()){
                    item.setStatus(1);
                }

                if (cbCheckFamily.isChecked()){
                    item.setStatus(2);
                }

                if (cbCheckOther.isChecked()){
                    item.setStatus(3);
                }

                if (item.getStatus() != 0){
                    fireBaseManager.insertTag(getContext(), item);
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

        cbCheckWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cbCheckWork.setChecked(true);
                cbCheckFamily.setChecked(false);
                cbCheckOther.setChecked(false);
            }
        });

        cbCheckFamily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cbCheckWork.setChecked(false);
                cbCheckFamily.setChecked(true);
                cbCheckOther.setChecked(false);
            }
        });

        cbCheckOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cbCheckWork.setChecked(false);
                cbCheckFamily.setChecked(false);
                cbCheckOther.setChecked(true);
            }
        });
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
