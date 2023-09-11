package com.example.table_group_lib;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.table_group_lib.base.BaseContentAdapter;
import com.example.table_group_lib.base.ITableContent;
import com.example.table_group_lib.base.ITableHeader;

import java.util.ArrayList;
import java.util.List;


/**
 * @author：WangJianFeng
 * @date：2022/11/3
 * @description：
 */
public class TableGroupLayout extends LinearLayout implements View.OnClickListener {

    private View inflate;
    private LinearLayout llHeaderRoot;
    private TextView tvHeaderTitle;
    private TableScrollView tableScrollView;
    private TableRecyclerView tableRecyclerView;
    private int itemTextSize;
    private int itemTextColor;
    private List<TextView> srotHeaderList = new ArrayList<>();
    private BaseContentAdapter contentAdapter;
    private OnTableClickListener onTableClickListener;

    public interface OnTableClickListener {
        void onHeaderTitleClick();

        void onHeaderItemClick(int index);
    }

    public void setOnTableClickListener(OnTableClickListener onTableClickListener) {
        this.onTableClickListener = onTableClickListener;
    }

    public TableGroupLayout(Context context) {
        this(context, null);
    }

    public TableGroupLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TableGroupLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initConfig(context, attrs);
        inflate = LayoutInflater.from(context).inflate(R.layout.table_group_layout, this, true);
        initView(inflate);
    }

    private void initConfig(Context context, AttributeSet attrs) {
    }

    private void initView(View rootview) {
        llHeaderRoot = rootview.findViewById(R.id.ll_header_root);
        tvHeaderTitle = rootview.findViewById(R.id.tv_header_title);
        tableScrollView = rootview.findViewById(R.id.table_scroll_view);
        tableRecyclerView = rootview.findViewById(R.id.table_recycler_view);
        tableScrollView.bindRecyclerView(tableRecyclerView);
        tableRecyclerView.setLinkageHeader(tableScrollView);
        tvHeaderTitle.setOnClickListener(click -> {
            if (null != onTableClickListener) {
                onTableClickListener.onHeaderTitleClick();
            }
        });
        tableScrollView.setIsHeader(true);
        tableScrollView.setOnHaderItemClickListener(new TableScrollView.OnHaderItemClickListener() {
            @Override
            public void onHeaderClick(int index) {
                if (null != onTableClickListener) {
                    onTableClickListener.onHeaderItemClick(index);
                }
            }

            @Override
            public void onItemClick() {

            }
        });
    }

    public void setHeaderTitle(String headerTitle) {
        if (null != tvHeaderTitle) {
            tvHeaderTitle.setText(headerTitle);
        }
    }

    public void setHederTitleTextColor(int textColor) {
        if (null != tvHeaderTitle) {
            tvHeaderTitle.setText(textColor);
        }
    }

    public void setHederTitleTextSize(int textSize) {
        if (null != tvHeaderTitle) {
            tvHeaderTitle.setTextSize(textSize);
        }
    }

    public void setHeaderItemTextSize(int itemTextSize) {
        this.itemTextSize = itemTextSize;
    }

    public void setHeaderItemTextColor(int itemTextColor) {
        this.itemTextColor = itemTextColor;
    }

    public void setHeaderData(List<ITableHeader> tableHeaderList, Context context) {
        if (null != tableHeaderList) {
            srotHeaderList.clear();
            tableScrollView.removeAllViews();
            for (ITableHeader header : tableHeaderList) {
                TextView headerView = createHeaderView(context, header.needSort());
                headerView.setText(header.getHeaderTitle());
                tableScrollView.addView(headerView);
            }
        }
    }

    public void setContentAdapter(BaseContentAdapter contentAdapter) {
        if (null != tableRecyclerView) {
            this.contentAdapter = contentAdapter;
            tableRecyclerView.setAdapter(contentAdapter);
        }
    }

    public void refreshContentData(List<ITableContent> tableContentList) {
        if (null != contentAdapter) {
            contentAdapter.refreshData(tableContentList);
        }
    }

    public TextView createHeaderView(Context context, boolean needSort) {
        TextView textView = new TextView(context);
        if (itemTextSize != 0) {
            textView.setTextSize(itemTextSize);
        }
        if (itemTextColor != 0) {
            textView.setTextColor(itemTextColor);
        }
        textView.setGravity(Gravity.CENTER);
        textView.setLayoutParams(new ViewGroup.LayoutParams(CommonUtils.dp2px(100f), ViewGroup.LayoutParams.MATCH_PARENT));
        if (needSort) {
            setSortDrawable(textView, -1, context);
//            textView.setOnClickListener(this);
            srotHeaderList.add(textView);
        }
        return textView;
    }

    /***
     *
     * @param textView
     * @param sortType 升序or降序
     */
    private void setSortDrawable(TextView textView, int sortType, Context context) {
        Drawable drawable;
        if (sortType == 0) {
            //降序
            drawable = ContextCompat.getDrawable(context, R.drawable.icon_sort_down);
        } else if (sortType == 1) {
            drawable = ContextCompat.getDrawable(context, R.drawable.icon_sort_up);
        } else {
            drawable = ContextCompat.getDrawable(context, R.drawable.icon_sort_none);
        }
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        textView.setCompoundDrawables(null, null, drawable, null);
    }

    @Override
    public void onClick(View v) {

    }
}
