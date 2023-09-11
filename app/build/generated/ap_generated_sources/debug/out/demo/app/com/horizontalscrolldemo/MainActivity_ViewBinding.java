// Generated code from Butter Knife. Do not modify!
package demo.app.com.horizontalscrolldemo;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MainActivity_ViewBinding implements Unbinder {
  private MainActivity target;

  @UiThread
  public MainActivity_ViewBinding(MainActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public MainActivity_ViewBinding(MainActivity target, View source) {
    this.target = target;

    target.tvLeftTitle = Utils.findRequiredViewAsType(source, R.id.tv_left_title, "field 'tvLeftTitle'", TextView.class);
    target.rvTabRight = Utils.findRequiredViewAsType(source, R.id.rv_tab_right, "field 'rvTabRight'", RecyclerView.class);
    target.horScrollview = Utils.findRequiredViewAsType(source, R.id.hor_scrollview, "field 'horScrollview'", CustomHorizontalScrollView.class);
    target.llTopRoot = Utils.findRequiredViewAsType(source, R.id.ll_top_root, "field 'llTopRoot'", LinearLayout.class);
    target.recyclerContent = Utils.findRequiredViewAsType(source, R.id.recycler_content, "field 'recyclerContent'", RecyclerView.class);
    target.swipeRefresh = Utils.findRequiredViewAsType(source, R.id.swipe_refresh, "field 'swipeRefresh'", SwipeRefreshLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    MainActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvLeftTitle = null;
    target.rvTabRight = null;
    target.horScrollview = null;
    target.llTopRoot = null;
    target.recyclerContent = null;
    target.swipeRefresh = null;
  }
}
