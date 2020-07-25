package com.thang.noteapp.views.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.thang.noteapp.R;
import com.thang.noteapp.controller.TagtableAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class TagFragment extends BaseFragment {

    @BindView(R.id.tab_tag)
    TabLayout tabTag;
    @BindView(R.id.vp_tag)
    ViewPager vpTag;

    private TagtableAdapter tableAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_tag;
    }

    @Override
    protected void initializeViews(View view, Bundle savedInstanceState) {
        this.setUpTabs();
    }

    private void setUpTabs() {
        List<Fragment> mListFragments = new ArrayList<>();
        mListFragments.add(TagWorkFragment.newInstance());
        mListFragments.add(TagFamilyFragment.newInstance());
        mListFragments.add(TagOtherFragment.newInstance());
        this.tableAdapter = new TagtableAdapter(getActivity().getSupportFragmentManager(), getContext(), mListFragments);
        this.vpTag.setAdapter(tableAdapter);
        this.tabTag.setupWithViewPager(vpTag);
    }
}