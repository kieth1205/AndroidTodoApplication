package com.thang.noteapp.controller;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.swipe.SwipeLayout;
import com.thang.noteapp.R;
import com.thang.noteapp.net.FireBaseManager;
import com.thang.noteapp.net.response.ChildTaskResponse;

import java.util.List;

public class WordsDetailAdapter extends RecyclerView.Adapter<WordsDetailAdapter.ViewHolder> {

    Context context;
    List<ChildTaskResponse> item;
    FireBaseManager  fireBaseManager = new FireBaseManager();
    String key;

    public WordsDetailAdapter(Context context, List<ChildTaskResponse> item, String key) {
        this.context = context;
        this.item = item;
        this.key = key;
    }

    private void update(List<ChildTaskResponse> item){
        this.item = item;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_work, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ChildTaskResponse mData = item.get(position);
        holder.tvName.setText(mData.getName());
        holder.sbWord.setProgress(mData.getProgress());
        holder.tvNumber.setText(String.valueOf(mData.getProgress()));
        holder.ckWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        holder.sbWord.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                holder.tvNumber.setText(String.valueOf(seekBar.getProgress()));
                if (seekBar.getProgress() == 100) {
                    holder.ckWord.setChecked(true);
                } else {
                    holder.ckWord.setChecked(false);
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        holder.ckWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.ckWord.isChecked()) {
                    holder.sbWord.setProgress(100);
                    holder.tvNumber.setText(String.valueOf(100));
                } else {
                    holder.sbWord.setProgress(mData.getProgress());
                    holder.tvNumber.setText(String.valueOf(mData.getProgress()));
                }
            }
        });

        holder.swipe.setShowMode(SwipeLayout.ShowMode.PullOut);

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

        holder.Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateWork(holder.tvName, item, position);
                holder.swipe.close();
            }
        });

        holder.Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteWork(item,position);
                holder.swipe.close();
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

    private void updateWork(TextView name, List<ChildTaskResponse> item, int position) {
        final EditText nameTable;

        View viewDialog = LayoutInflater.from(context).inflate(R.layout.dl_update, null);
        nameTable = viewDialog.findViewById(R.id.edt_dl_update);

        final AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle(context.getString(R.string.key_update));
        alert.setView(viewDialog);
        alert.setCancelable(false);
        alert.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alert.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                String nameUpdate = nameTable.getText().toString();
                name.setText(nameUpdate);
                item.get(position).setName(nameUpdate);
                fireBaseManager.updateWord(context,item.get(position),key);
            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();
    }

    private void deleteWork(List<ChildTaskResponse> item, int position) {
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle(R.string.key_Delete)
                .setMessage(R.string.key_mess_delete)
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        fireBaseManager.deleteWork(context,item.get(position),key);
                        item.remove(item.get(position));
                        notifyDataSetChanged();
                    }
                }).setIcon(android.R.drawable.ic_dialog_alert).create();

        dialog.show();
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
}
