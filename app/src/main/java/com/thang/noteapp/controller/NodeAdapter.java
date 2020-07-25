package com.thang.noteapp.controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thang.noteapp.R;
import com.thang.noteapp.net.response.NodeResponse;

import java.util.List;

public class NodeAdapter extends RecyclerView.Adapter<NodeAdapter.ViewHolder> {
    Context context;
    List<NodeResponse> item;

    public NodeAdapter(Context context, List<NodeResponse> item) {
        this.context = context;
        this.item = item;
    }

    public void update(List<NodeResponse> item) {
        this.item = item;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_note, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvNode.setText(item.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        if (item == null) {
            return 0;
        }
        return item.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNode;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNode = itemView.findViewById(R.id.tv_node);
        }
    }
}
