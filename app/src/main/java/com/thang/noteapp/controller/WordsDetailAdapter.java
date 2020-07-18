package com.thang.noteapp.controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.swipe.SwipeLayout;
import com.thang.noteapp.R;
import com.thang.noteapp.net.response.ChildTaskResponse;

import java.util.List;

public class WordsDetailAdapter extends RecyclerView.Adapter<WordsDetailAdapter.ViewHolder> {

    Context context;
    List<ChildTaskResponse> item;

    public WordsDetailAdapter(Context context, List<ChildTaskResponse> item) {
        this.context = context;
        this.item = item;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_work,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ChildTaskResponse mData = item.get(position);
        holder.tvName.setText(mData.getName());
        holder.sbWord.setProgress(mData.getProgress());
        holder.tvNumber.setText(String.valueOf(mData.getProgress()));
        holder.sbWord.setOnSeekBarChangeListener(customSeekBarListener);
        holder.ckWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
        private SwipeLayout swipe;
        private LinearLayout bottomWraper;
        private ImageView Edit;
        private ImageView Delete;
        private CardView cvTasks;
        private TextView tvName;
        private SeekBar sbWord;
        private CheckBox ckWord;
        private TextView tvNumber;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            swipe = itemView.findViewById(R.id.swipe);
            bottomWraper = itemView.findViewById(R.id.bottom_wraper);
            Edit = itemView.findViewById(R.id.Edit);
            Delete = itemView.findViewById(R.id.Delete);
            cvTasks = itemView.findViewById(R.id.cv_tasks);
            tvName = itemView.findViewById(R.id.tv_name);
            sbWord = itemView.findViewById(R.id.sb_word);
            ckWord = itemView.findViewById(R.id.ck_word);
            tvNumber = itemView.findViewById(R.id.tv_number);
        }
    }

    private SeekBar.OnSeekBarChangeListener customSeekBarListener = new SeekBar.OnSeekBarChangeListener() {

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            upDateTextBox((TextView) seekBar.getTag(), progress);
        }
    };


    private void upDateTextBox(TextView tv, int progress){
        tv.setText(String.valueOf(progress));
    }
}
