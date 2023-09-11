package com.example.table_group_lib;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.OverScroller;

import java.util.ArrayList;
import java.util.List;

/**
 * @author：WangJianFeng
 * @date：2022/11/2
 * @description：表格布局RecyclerView,支持联动横竖向滑动
 */
public class TableRecyclerView extends RecyclerView {
    private List<TableScrollView> scrollViews = new ArrayList<>();
    public int recordX;

    public TableRecyclerView(@NonNull Context context) {
        this(context, null);
    }

    public TableRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TableRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setLayoutManager(new LinearLayoutManager(context));
    }

    public List<TableScrollView> getScrollViews() {
        return scrollViews;
    }


    @Override
    public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);
        //RecyclerView的item状态改变时,暂停所有列表的滑动状态
        for (TableScrollView scrollView : scrollViews) {
            OverScroller scroller = scrollView.getScroller();
            if (!scroller.isFinished()) {
                scroller.abortAnimation();
            }
        }
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        TableScrollView childView = child.findViewWithTag(TableScrollView.class.getSimpleName());
        if (null != childView) {
            childView.scrollTo(recordX, 0);
            scrollViews.add(childView);
            childView.bindRecyclerView(this);
        }
        super.addView(child, index, params);
    }

    @Override
    public void onViewRemoved(View child) {
        super.onViewRemoved(child);
        View childView = child.findViewWithTag(TableScrollView.class.getSimpleName());
        scrollViews.remove(childView);
    }

    /***
     *
     * @param headerScrollView
     */
    public void setLinkageHeader(TableScrollView headerScrollView) {
        if (null != headerScrollView) {
            headerScrollView.bindRecyclerView(this);
            if (scrollViews.contains(headerScrollView)) {
                scrollViews.remove(headerScrollView);
            }
            scrollViews.add(headerScrollView);
        }
    }
}
