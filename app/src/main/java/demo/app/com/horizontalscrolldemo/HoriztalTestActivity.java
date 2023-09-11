package demo.app.com.horizontalscrolldemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author：WangJianFeng
 * @date：2022/11/7
 * @description：
 */
public class HoriztalTestActivity extends Activity {

    private LinearLayout llRoot;
    private TextView tvTest;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_horiztal_test);
        initView();
    }

    private void initView() {
        llRoot = findViewById(R.id.ll_root);
        llRoot.setOnClickListener(click -> {
            Toast.makeText(HoriztalTestActivity.this, "点击root布局", Toast.LENGTH_SHORT).show();
        });
        tvTest = findViewById(R.id.tv_test);
        tvTest.setOnClickListener(click->{
            Toast.makeText(HoriztalTestActivity.this, "点击textView布局", Toast.LENGTH_SHORT).show();
        });
    }
}
