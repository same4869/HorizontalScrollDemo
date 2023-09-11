package demo.app.com.horizontalscrolldemo;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.table_group_lib.TableScrollView;
import com.example.table_group_lib.base.BaseContentAdapter;
import com.example.table_group_lib.base.BaseViewHolder;
import com.example.table_group_lib.base.ITableContent;

import java.util.List;


/**
 * @author：WangJianFeng
 * @date：2022/11/4
 * @description：
 */
public class TableGroupContentAdapter extends BaseContentAdapter<TableGroupContentAdapter.ViewHolder> {



    public TableGroupContentAdapter() {
        super(R.layout.table_group_content_item);
    }

    @Override
    protected void convert(@NonNull ViewHolder helper, ITableContent item) {
        List<ITableContent> rowList = item.getRowList();
        helper.tvValue1.setText(rowList.get(0).getValue());
        helper.tvValue2.setText(rowList.get(1).getValue());
        helper.tvValue3.setText(rowList.get(2).getValue());
        helper.tvValue4.setText(rowList.get(3).getValue());
        helper.tvValue5.setText(rowList.get(4).getValue());
        helper.tvValue6.setText(rowList.get(5).getValue());
        helper.tvValue7.setText(rowList.get(6).getValue());
        helper.tvValue8.setText(rowList.get(7).getValue());
        helper.tvValue9.setText(rowList.get(8).getValue());
        helper.tvValue10.setText(rowList.get(9).getValue());



        helper.tableContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(helper.itemView.getContext(), "点击item布局,位置:"+helper.getLayoutPosition(), Toast.LENGTH_SHORT).show();
            }
        });
    }



    class ViewHolder extends BaseViewHolder {
        private LinearLayout llContentRoot;
        private TextView tvContentTitle;
        private TableScrollView tableContent;
        private TextView tvValue1;
        private TextView tvValue2;
        private TextView tvValue3;
        private TextView tvValue4;
        private TextView tvValue5;
        private TextView tvValue6;
        private TextView tvValue7;
        private TextView tvValue8;
        private TextView tvValue9;
        private TextView tvValue10;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View viewRoot) {
            llContentRoot = viewRoot.findViewById(R.id.ll_content_root);
            tvContentTitle = viewRoot.findViewById(R.id.tv_content_title);
            tableContent = viewRoot.findViewById(R.id.table_content);
            tvValue1 = viewRoot.findViewById(R.id.tv_value1);
            tvValue2 = viewRoot.findViewById(R.id.tv_value2);
            tvValue3 = viewRoot.findViewById(R.id.tv_value3);
            tvValue4 = viewRoot.findViewById(R.id.tv_value4);
            tvValue5 = viewRoot.findViewById(R.id.tv_value5);
            tvValue6 = viewRoot.findViewById(R.id.tv_value6);
            tvValue7 = viewRoot.findViewById(R.id.tv_value7);
            tvValue8 = viewRoot.findViewById(R.id.tv_value8);
            tvValue9 = viewRoot.findViewById(R.id.tv_value9);
            tvValue10 = viewRoot.findViewById(R.id.tv_value10);
        }
    }


}
