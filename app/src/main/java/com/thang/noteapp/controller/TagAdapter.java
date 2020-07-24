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
import com.thang.noteapp.net.response.TagRespont;
import com.thang.noteapp.net.response.TodoResponse;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class TagAdapter extends RecyclerView.Adapter<TagAdapter.ViewHolder> {

    Context context;
    List<TagRespont> item;

    public TagAdapter(Context context, List<TagRespont> item) {
        this.context = context;
        this.item = item;
    }

    public void update(List<TagRespont> item){
        this.item = item;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tag, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvName.setText(item.get(position).getChildName());

        holder.swipe.setShowMode(SwipeLayout.ShowMode.PullOut);
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.swipe.close();
                dialogDelete(context, item.get(position));
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

    private void dialogDelete(Context context, TagRespont tag){
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
                fireBaseManager.deleteTag(tag,context);
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public int getItemCount() {
        if (item == null) {
            return 0;
        }
        return item.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private SwipeLayout swipe;
        private LinearLayout bottomWraper;
        private ImageView imgDelete;
        private TextView tvName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            swipe = itemView.findViewById(R.id.swipe);
            bottomWraper = itemView.findViewById(R.id.bottom_wraper);
            imgDelete = itemView.findViewById(R.id.img_delete);
            tvName = itemView.findViewById(R.id.tv_name);
        }
    }
}
