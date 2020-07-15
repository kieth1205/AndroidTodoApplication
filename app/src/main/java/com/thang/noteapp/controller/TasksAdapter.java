package com.thang.noteapp.controller;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.thang.noteapp.R;
import com.thang.noteapp.net.response.TasksResponse;
import com.thang.noteapp.views.activity.TaskActivity;

import java.util.List;

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.ViewHolder> {

    private Context context;
    private List<TasksResponse> item;

    public TasksAdapter(Context context, List<TasksResponse> item) {
        this.context = context;
        this.item = item;
    }

    public void update(List<TasksResponse> item) {
        this.item = item;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_task_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.tvTitleTasks.setText(item.get(position).getTitle());
            holder.tvDescriptionTasks.setText(item.get(position).getDescription());
            holder.tvProgressTasks.setText(String.valueOf(item.get(position).getProgress()));
            holder.rtbTasks.setRating(item.get(position).getPrioritize());
            holder.cvTasks.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(new Intent(context, TaskActivity.class));
                }
            });
    }

    @Override
    public int getItemCount() {
        if (item == null) {
            return 0;
        }
        return item.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitleTasks;
        private TextView tvDescriptionTasks;
        private RatingBar rtbTasks;
        private TextView tvProgressTasks;
        private CardView cvTasks;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitleTasks = itemView.findViewById(R.id.tv_title_tasks);
            tvDescriptionTasks = itemView.findViewById(R.id.tv_description_tasks);
            rtbTasks = itemView.findViewById(R.id.rtb_tasks);
            tvProgressTasks = itemView.findViewById(R.id.tv_progress_tasks);
            cvTasks = itemView.findViewById(R.id.cv_tasks);

        }
    }
}
