package com.thang.noteapp.controller;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.swipe.SwipeLayout;
import com.thang.noteapp.R;
import com.thang.noteapp.common.eventbus.EventBusAction;
import com.thang.noteapp.common.eventbus.SetTagEvent;
import com.thang.noteapp.common.eventbus.UpdateTodoEvent;
import com.thang.noteapp.net.FireBaseManager;
import com.thang.noteapp.net.response.TodoResponse;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class TodoApdater extends RecyclerView.Adapter<TodoApdater.ViewHolder> {
    private Context context;
    private List<TodoResponse> items;
    private String myFormat = "MM/dd/yyyy";
    private SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

    public TodoApdater(Context context, List<TodoResponse> items) {
        this.context = context;
        this.items = items;
    }

    public void update(List<TodoResponse> item) {
        this.items = item;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_todo, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.swipe.setShowMode(SwipeLayout.ShowMode.PullOut);
        holder.tvName.setText(items.get(position).getName());
        if (items.get(position).getDateStart() != null) {
            holder.tvDate.setText(sdf.format(items.get(position).getDateStart().getTime()));
        }
        if (items.get(position).getTag_status() != 0){
            holder.llTag.setVisibility(View.VISIBLE);
            holder.tvTag.setText(items.get(position).getTag_status());
        }

        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.swipe.close();
                EventBus.getDefault().post(new UpdateTodoEvent(EventBusAction.Todo.UPDATE_TODO, items.get(position)));
            }
        });

        holder.imgSetTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.swipe.close();
                EventBus.getDefault().post(new SetTagEvent(EventBusAction.Tag.SET_TAG,null, items.get(position)));
            }
        });
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.swipe.close();
                dialogDelete(context, items.get(position));
            }
        });
        holder.swipe.addSwipeListener(new SwipeLayout.SwipeListener() {
            @Override
            public void onStartOpen(SwipeLayout layout) {

            }

            @Override
            public void onOpen(SwipeLayout layout) {

            }

            @Override
            public void onStartClose(SwipeLayout layout) {

            }

            @Override
            public void onClose(SwipeLayout layout) {

            }

            @Override
            public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {

            }


            @Override
            public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {
                if (layout.getOpenStatus() == SwipeLayout.Status.Open) {
                    layout.close();
                }
            }
        });

    }

    private void dialogDelete(Context context, TodoResponse todoResponse){
        FireBaseManager fireBaseManager = new FireBaseManager();
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.key_title_delete);
        builder.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                fireBaseManager.deleteTodo(todoResponse,context);
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public int getItemCount() {

        if (items == null){
            return 0;
        }
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private SwipeLayout swipe;
        private LinearLayout bottomWraper;
        private ImageView imgEdit;
        private ImageView imgDelete;
        private TextView tvName;
        private TextView tvDate;
        private LinearLayout llTag;
        private TextView tvTag;
        private ImageView imgSetTag;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            swipe = itemView.findViewById(R.id.swipe);
            bottomWraper = itemView.findViewById(R.id.bottom_wraper);
            imgEdit = itemView.findViewById(R.id.img_edit);
            imgDelete = itemView.findViewById(R.id.img_delete);
            tvName = itemView.findViewById(R.id.tv_name);
            tvDate = itemView.findViewById(R.id.tv_date);
            llTag = itemView.findViewById(R.id.ll_tag);
            tvTag = itemView.findViewById(R.id.tv_tag);
            imgSetTag = itemView.findViewById(R.id.img_set_tag);
        }
    }
}
