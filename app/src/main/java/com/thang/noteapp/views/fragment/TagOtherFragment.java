package com.thang.noteapp.views.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.thang.noteapp.R;
import com.thang.noteapp.controller.TagAdapter;
import com.thang.noteapp.net.FireBaseManager;
import com.thang.noteapp.net.interfaces.TagsStatus;
import com.thang.noteapp.net.response.TagRespont;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class TagOtherFragment extends BaseFragment {

    @BindView(R.id.rv_tag)
    RecyclerView rvTag;

    private List<TagRespont> items = new ArrayList<>();

    private FireBaseManager fireBaseManager = new FireBaseManager();
    private TagAdapter adapter;


    public static TagOtherFragment newInstance() {
        TagOtherFragment fragment = new TagOtherFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_tag_other;
    }

    @Override
    protected void initializeViews(View view, Bundle savedInstanceState) {
        fireBaseManager.getAllTag(getContext());
        this.setupTag();
    }

    private void setupTag(){
        rvTag.setLayoutManager(new LinearLayoutManager(getContext()));
        rvTag.setHasFixedSize(true);

        fireBaseManager.setTags(new TagsStatus() {
            @Override
            public void getData(List<TagRespont> item) {
                for (int i = 0 ; i < item.size(); i ++){
                    if (item.get(i).getStatus() == 3){
                        items.add(item.get(i));
                    }
                }
                if (items != null) {
                    if (adapter == null) {
                        adapter = new TagAdapter(getContext(),items);
                        rvTag.setAdapter(adapter);
                    } else {
                        adapter.update(items);
                    }
                }
            }
        });
    }
}