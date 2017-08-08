package com.dmw.zgl.bowu;

import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:          zgl_dmw
 * Email:           2559531803@qq.com
 * Create:          2017/8/8 16:25
 * Update:          2017/8/8 16:25
 * Description:     RecyclerAdapter
 */

public abstract class RecyclerAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {

    private static final int BASE_ITEM_TYPE_HEADER = 100000;
    private static final int BASE_ITEM_TYPE_FOOTER = 200000;
    private SparseArrayCompat<View> mHeaderViews = new SparseArrayCompat<>();
    private SparseArrayCompat<View> mFooterViews = new SparseArrayCompat<>();
    private List<Object> mDatas = new ArrayList<>();

    private boolean isHeaderViewPos(int position) {
        return position < getHeadersCount();
    }

    private boolean isFooterViewPos(int position) {
        return position >= getHeadersCount() + mDatas.size();
    }


    public void addHeaderView(View view) {
        mHeaderViews.put(mHeaderViews.size() + BASE_ITEM_TYPE_HEADER, view);
    }

    public void addFooterView(View view) {
        mFooterViews.put(mFooterViews.size() + BASE_ITEM_TYPE_FOOTER, view);
    }

    public int getHeadersCount() {
        return mHeaderViews.size();
    }

    public int getFootersCount() {
        return mFooterViews.size();
    }

    public <T extends Object> void addHomePage(List<T> data) {
        mDatas.clear();
        if (data != null) {
            this.mDatas.addAll(data);
        }
        notifyDataSetChanged();
    }

    public <T extends Object> void addNextPage(List<T> data) {
        if (data != null) {
            int itemCount = data.size();
            int postionStart = getHeadersCount() + mDatas.size();
            this.mDatas.addAll(data);
            notifyItemRangeInserted(postionStart, itemCount);
        }
    }

    public List<Object> getData() {
        return mDatas;
    }

    public Object getData(int position) {
        return mDatas.get(position - getHeadersCount());
    }

    public boolean isEmpty() {
        return mDatas.isEmpty();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;

            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (isHeaderViewPos(position) || isFooterViewPos(position) || isFullSpan(position)) {
                        return gridLayoutManager.getSpanCount();
                    }
                    return 1;
                }
            });
            gridLayoutManager.setSpanCount(gridLayoutManager.getSpanCount());
        }
    }

    @Override
    public void onViewAttachedToWindow(RecyclerViewHolder holder) {
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        if (lp != null && lp instanceof StaggeredGridLayoutManager.LayoutParams) {
            int position = holder.getLayoutPosition();
            if (isHeaderViewPos(position) || isFooterViewPos(position) || isFullSpan(position)) {
                StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
                p.setFullSpan(true);
            }
        }
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        if (mHeaderViews.get(viewType) != null) {
            return new RecyclerViewHolder(mHeaderViews.get(viewType));

        } else if (mFooterViews.get(viewType) != null) {
            return new RecyclerViewHolder(mFooterViews.get(viewType));
        }
        return onCreateHolder(viewGroup, viewType);
    }

    protected abstract RecyclerViewHolder onCreateHolder(ViewGroup viewGroup, int viewType);

    @Override
    public void onBindViewHolder(RecyclerViewHolder viewHolder, int position) {
        if (isHeaderViewPos(position)) {
            return;
        }
        if (isFooterViewPos(position)) {
            return;
        }
        onBindHolder(viewHolder, position);
    }

    protected abstract void onBindHolder(RecyclerViewHolder viewHolder, int position);

    protected abstract boolean isFullSpan(int position);

    @Override
    public int getItemCount() {
        return getHeadersCount() + getFootersCount() + mDatas.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeaderViewPos(position)) {
            return mHeaderViews.keyAt(position);
        } else if (isFooterViewPos(position)) {
            return mFooterViews.keyAt(position - getHeadersCount() - mDatas.size());
        }
        return getItemType(position);
    }

    protected abstract int getItemType(int position);

}
