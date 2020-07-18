package com.thang.noteapp.controller;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.thang.noteapp.R;

import java.util.List;

public class ChildTaskAdapter extends FragmentPagerAdapter {
    private static final int[] TAB_TITLES = new int[]{R.string.key_works1, R.string.key_Description};
    private Context mContext;
    private List<Fragment> mListFragments;

    public ChildTaskAdapter(@NonNull FragmentManager fm, Context context, List<Fragment> mListFragments) {
        super(fm);
        this.mContext = context;
        this.mListFragments = mListFragments;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mListFragments.get(position);
    }

    @Override
    public int getCount() {
        return mListFragments != null ? mListFragments.size() : 0;
    }
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

}
