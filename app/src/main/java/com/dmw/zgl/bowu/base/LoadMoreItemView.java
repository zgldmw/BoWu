package com.dmw.zgl.bowu.base;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dmw.zgl.bowu.R;

/**
 * Author:          zgl_dmw
 * Email:           2559531803@qq.com
 * Create:          2017/8/11 14:10
 * Update:          2017/8/11 14:10
 * Description:     LoadMoreItemView
 */

public class LoadMoreItemView {
    private int mPreNumLoad = 3;
    private int lastPosition;
    private int[] lastPositions;
    private RecyclerView mRecyclerView;
    private View mLoadMoreView;
    private LoadMoreHandler mLoadMoreHandler;
    private ProgressBar progressBar;
    private TextView loadInfo;

    public void setupWithRecyclerView(RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
        mLoadMoreView = LayoutInflater.from(recyclerView.getContext()).inflate(R.layout.layout_load_more, recyclerView, false);
    }

    public void setLoadMoreHandler(LoadMoreHandler loadMoreHandler) {
        mLoadMoreHandler = loadMoreHandler;
        initLoadMoreView();
    }

    private void initLoadMoreView() {
        progressBar = mLoadMoreView.findViewById(R.id.progress);
        loadInfo = mLoadMoreView.findViewById(R.id.load_more_info);
        mLoadMoreView.setVisibility(mLoadMoreHandler == null ? View.GONE : View.VISIBLE);
        if (mRecyclerView != null) {
            if (mRecyclerView.getAdapter() != null && mRecyclerView.getAdapter() instanceof RecyclerAdapter) {
                RecyclerAdapter recyclerAdapter = (RecyclerAdapter) mRecyclerView.getAdapter();
                recyclerAdapter.addFooterView(mLoadMoreView);
                mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);

                        RecyclerView.LayoutManager layoutManager = mRecyclerView.getLayoutManager();

                        if (layoutManager instanceof GridLayoutManager) {
                            lastPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
                        } else if (layoutManager instanceof LinearLayoutManager) {
                            lastPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                            StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
                            if (lastPositions == null) {
                                lastPositions = new int[staggeredGridLayoutManager.getSpanCount()];
                            }
                            staggeredGridLayoutManager.findLastVisibleItemPositions(lastPositions);
                            lastPosition = findMax(lastPositions);
                        }
                    }

                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);

                        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                        int visibleItemCount = layoutManager.getChildCount();
                        int totalItemCount = layoutManager.getItemCount();

                        if ((visibleItemCount > 0 && newState == RecyclerView.SCROLL_STATE_IDLE && lastPosition >= totalItemCount - 1 - mPreNumLoad)) {
                            if (mLoadMoreHandler != null) {
                                if (mLoadMoreHandler.isCanLoadMore()) {
                                    progressBar.setVisibility(View.VISIBLE);
                                    loadInfo.setText(R.string.load_more);

                                    mLoadMoreHandler.loadMore();
                                } else {
                                    progressBar.setVisibility(View.GONE);
                                    loadInfo.setText(R.string.no_more_data);
                                }
                            }
                        }
                    }
                });
            }
        }
    }

    private int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }
}
