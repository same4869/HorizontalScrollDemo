package demo.app.com.horizontalscrolldemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.table_group_lib.TableGroupLayout;
import com.example.table_group_lib.base.ITableContent;
import com.example.table_group_lib.base.ITableHeader;

import java.util.ArrayList;
import java.util.List;

/**
 * @author：WangJianFeng
 * @date：2022/11/4
 * @description：
 */
public class TableGroupTestActivity extends Activity {

    private TableGroupLayout tableGroupLayout;
    List<ITableHeader> tableGroupHeaderEntityList = new ArrayList<>();
    List<ITableContent> tableContentList = new ArrayList<>();
    private TableGroupContentAdapter contentAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_table_group_test);
        tableGroupLayout = findViewById(R.id.tab_group_layout);
        //头部数据
        for (int i = 0; i < 10; i++) {
            tableGroupHeaderEntityList.add(new TableGroupHeaderEntity("头部" + i));
        }
        //内容数据
        for (int i = 0; i < 30; i++) {
            List<ITableContent> rowList = new ArrayList<>();
            TableGroupContentEntity tableGroupContentEntity = new TableGroupContentEntity();
            for (int j = 0; j < 10; j++) {
                rowList.add(new TableGroupContentEntity("第"+i+"行,第"+j+"列"));
            }
            tableGroupContentEntity.setRowDatalist(rowList);
            tableContentList.add(tableGroupContentEntity);
        }

        tableGroupLayout.setHeaderData(tableGroupHeaderEntityList, this);
        contentAdapter = new TableGroupContentAdapter();
        tableGroupLayout.setContentAdapter(contentAdapter);
        tableGroupLayout.refreshContentData(tableContentList);

        tableGroupLayout.setOnTableClickListener(new TableGroupLayout.OnTableClickListener() {
            @Override
            public void onHeaderTitleClick() {
                startActivity(new Intent(TableGroupTestActivity.this, HoriztalTestActivity.class));
            }

            @Override
            public void onHeaderItemClick(int index) {
                Toast.makeText(TableGroupTestActivity.this, "点击了头部第" + index + "个item", Toast.LENGTH_SHORT).show();
            }
        });
    }


    class TableGroupContentEntity implements ITableContent {
        List<ITableContent> rowDatalist;
        private String value;

        public TableGroupContentEntity() {
        }


        public void setRowDatalist(List<ITableContent> rowDatalist) {
            this.rowDatalist = rowDatalist;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public TableGroupContentEntity(String value) {
            this.value = value;
        }


        @Override
        public List<ITableContent> getRowList() {
            return rowDatalist;
        }

        @Override
        public String getValue() {
            return value;
        }
    }


    class TableGroupHeaderEntity implements ITableHeader {

        private String headerTitle;

        public TableGroupHeaderEntity(String headerTitle) {
            this.headerTitle = headerTitle;
        }

        @Override
        public String getHeaderTitle() {
            return headerTitle;
        }

        @Override
        public boolean needSort() {
            return true;
        }

        @Override
        public int sortId() {
            return 0;
        }

        @Override
        public int sortType() {
            return -1;
        }
    }


}
