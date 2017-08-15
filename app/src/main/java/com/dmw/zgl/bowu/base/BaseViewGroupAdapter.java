package com.dmw.zgl.bowu.base;

import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public abstract class BaseViewGroupAdapter<T extends Object> {

    public ArrayList<T> mData = new ArrayList<>();
    public ViewGroup mContainer;

    public BaseViewGroupAdapter(ViewGroup mViewGroup) {
        this.mContainer = mViewGroup;
    }

    public ArrayList<T> getData() {
        return mData;
    }

    protected abstract View getView(int position, View convertView, ViewGroup parent);

    public void addHomeData(ArrayList<T> list) {
        this.mData.clear();
        if (list != null) {
            this.mData.addAll(list);
        }
        notifyDataSetChanged();
    }

    public void addMoreData(ArrayList<T> list) {
        if (list != null) {
            this.mData.addAll(list);
        }
        notifyDataSetChanged();
    }

    public void addData(T data) {
        if (data != null) {
            this.mData.add(data);
        }
        notifyDataSetChanged();
    }

    private void notifyDataSetChanged() {
        int count = mData.size();
        for (int i = 0; i < count; i++) {
            View mCacheView = getOldView(i);
            if (mCacheView != null) {
                getView(i, mCacheView, mContainer);
                mCacheView.setVisibility(View.VISIBLE);
            } else {
                View view = getView(i, null, mContainer);
                mContainer.addView(view);
            }
        }

        int childCount = mContainer.getChildCount();

        for (int i = mData.size(); i < childCount; i++) {
            mContainer.getChildAt(i).setVisibility(View.GONE);
        }
    }

    private View getOldView(int index) {
        if (index >= mContainer.getChildCount()) {
            return null;
        }
        return mContainer.getChildAt(index);
    }
}
