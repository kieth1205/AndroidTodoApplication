package com.thang.noteapp.controller;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thang.noteapp.R;
import com.thang.noteapp.common.eventbus.EventBusAction;
import com.thang.noteapp.common.eventbus.FillWordEvent;
import com.thang.noteapp.common.eventbus.RemoveWordsEvent;
import com.thang.noteapp.net.response.ChildTaskResponse;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class WordsAdapter extends RecyclerView.Adapter<WordsAdapter.ViewHolder> {

    private Context context;
    private List<ChildTaskResponse> items;

    public WordsAdapter(Context context, List<ChildTaskResponse> items) {
        this.context = context;
        this.items = items;
    }

    public void update(List<ChildTaskResponse> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_words, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvStt.setText(String.valueOf(position + 1));
        holder.edtWord.setText(items.get(position).getName());

        holder.edtWord.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                EventBus.getDefault().post(new FillWordEvent(EventBusAction.Tasks.FILL_WORDS,items.get(position).getId(),String.valueOf(s)));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        holder.imgRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new RemoveWordsEvent(EventBusAction.Tasks.REMOVE_WORDS, position));
            }
        });
    }

    @Override
    public int getItemCount() {
        if (items == null){
            return 0;
        }
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvStt;
        private EditText edtWord;
        private ImageView imgRemove;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvStt = itemView.findViewById(R.id.tv_stt);
            edtWord = itemView.findViewById(R.id.edt_word);
            imgRemove = itemView.findViewById(R.id.img_remove);        }
    }
}
