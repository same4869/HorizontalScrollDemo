package com.example.table_group_lib;

import android.content.Context;
import android.graphics.Rect;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.OverScroller;
import android.widget.Toast;

import java.util.List;

/**
 * @author：WangJianFeng
 * @date：2022/11/2
 * @description：
 */
public class TableScrollView extends ViewGroup implements GestureDetector.OnGestureListener {
    public static final String TAG = "TableScrollView==>";
    private OverScroller mScroller;
    protected GestureDetectorCompat mDetector;
    private int totalViewWidth;
    private TableRecyclerView horizontalRecyclerview;
    private boolean isHeader;
    private SparseArray<Rect> clickRectList = new SparseArray<>();
    private OnHaderItemClickListener onHaderItemClickListener;

    public TableScrollView(Context context) {
        this(context, null);
    }

    public TableScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TableScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mDetector = new GestureDetectorCompat(getContext(), this);
        mScroller = new OverScroller(context);
    }

    public OverScroller getScroller() {
        return mScroller;
    }

    public interface OnHaderItemClickListener {
        void onHeaderClick(int index);

        void onItemClick();
    }

    public void setOnHaderItemClickListener(OnHaderItemClickListener onHaderItemClickListener) {
        this.onHaderItemClickListener = onHaderItemClickListener;
    }

    /***
     * 绑定recyclerView,用于操作所有子view滑动
     * @param horizontalRecyclerview
     */
    public void bindRecyclerView(TableRecyclerView horizontalRecyclerview) {
        this.horizontalRecyclerview = horizontalRecyclerview;
    }

    /***
     * 设置当前是否是header,用于区分header的item点击事件
     * @param isHeader
     */
    public void setIsHeader(boolean isHeader) {
        this.isHeader = isHeader;
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measureWidth = MeasureSpec.getSize(widthMeasureSpec);
        int measureHeight = MeasureSpec.getSize(heightMeasureSpec);
        int measureWidhtMode = MeasureSpec.getMode(widthMeasureSpec);
        int measureHeightMode = MeasureSpec.getMode(heightMeasureSpec);

        int height = 0;
        int width = 0;
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            //测量子控件
            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();
            width += childWidth;
            height = Math.max(height, childHeight);
        }
        setMeasuredDimension((measureWidhtMode == MeasureSpec.EXACTLY) ? measureWidth : width, (measureHeightMode == MeasureSpec.EXACTLY) ? measureHeight : height);
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int layoutLeft = 0;
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != View.GONE) {
                int measuredWidth = child.getMeasuredWidth();
                int measuredHeight = child.getMeasuredHeight();
                child.layout(layoutLeft, getPaddingTop(), layoutLeft + measuredWidth, getPaddingTop() + measuredHeight);
                Rect rect = new Rect();
                rect.left = layoutLeft;
                rect.right = layoutLeft + measuredWidth;
                clickRectList.put(i, rect);
                layoutLeft += measuredWidth;
            }
        }
        totalViewWidth = layoutLeft;
    }


    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            scrollAllViews(mScroller.getCurrX());
            postInvalidateOnAnimation();
        }
    }

    private void scrollAllViews(int scollX) {
        if (null != horizontalRecyclerview) {
            List<TableScrollView> scrollViews = horizontalRecyclerview.getScrollViews();
            int size = scrollViews.size();
            for (int i = 0; i < size; i++) {
                TableScrollView customHorView = scrollViews.get(i);
                customHorView.scrollTo(scollX, 0);
            }
            setRecordX(scollX);
        }
    }


    private void setRecordX(int recordX) {
        if (null != horizontalRecyclerview) {
            horizontalRecyclerview.recordX = recordX;
        }
    }

    public int getRecordX() {
        if (null != horizontalRecyclerview) {
            return horizontalRecyclerview.recordX;
        }
        return 0;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.mDetector.onTouchEvent(event);
        return true;
    }


    @Override
    public boolean onDown(MotionEvent e) {
        List<TableScrollView> scrollViews = horizontalRecyclerview.getScrollViews();
        for (TableScrollView scrollView : scrollViews) {
            OverScroller scroller = scrollView.getScroller();
            if (!scroller.isFinished()) {
                scroller.abortAnimation();
            }
        }
        setRecordX(getScrollX());
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        if (isHeader) {
            float tapX = e.getX();
            int recordX = getRecordX();
            float clickX = recordX + tapX;
            for (int i = 0; i < clickRectList.size(); i++) {
                Rect rect = clickRectList.get(i);
                if (clickX >= rect.left && clickX <= rect.right) {
                    if (null != onHaderItemClickListener) {
                        onHaderItemClickListener.onHeaderClick(i);
                    }
                    break;
                }
            }
        }else {
            performClick();
        }
        getParent().requestDisallowInterceptTouchEvent(false);
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        int scrollX = (int) (getScrollX() + distanceX);
        int maxW = totalViewWidth - getMeasuredWidth();
        if (scrollX < 0) {
            scrollX = 0;
        }
        if (scrollX > maxW) {
            scrollX = maxW;
        }
        scrollAllViews(scrollX);
        getParent().requestDisallowInterceptTouchEvent(true);
        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if (null != mScroller) {
            int scrollX = getScrollX();
            if (scrollX < 0) {
                scrollX = 0;
            }
            int maxX = totalViewWidth - getMeasuredWidth();
            if (maxX < 0) {
                maxX = 0;
            }
            int round = Math.round(velocityX / 1.5f);
            mScroller.fling(scrollX, 0
                    , -round, 0,
                    0, maxX,
                    0, 0);
            postInvalidateOnAnimation();
        }
        return true;
    }


}
