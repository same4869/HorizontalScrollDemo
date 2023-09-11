// Generated code from Butter Knife. Do not modify!
package demo.app.com.horizontalscrolldemo;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ContentAdapter$ItemViewHolder_ViewBinding implements Unbinder {
  private ContentAdapter.ItemViewHolder target;

  @UiThread
  public ContentAdapter$ItemViewHolder_ViewBinding(ContentAdapter.ItemViewHolder target,
      View source) {
    this.target = target;

    target.tvLeftTitle = Utils.findRequiredViewAsType(source, R.id.tv_left_title, "field 'tvLeftTitle'", TextView.class);
    target.rvItemRight = Utils.findRequiredViewAsType(source, R.id.rv_item_right, "field 'rvItemRight'", RecyclerView.class);
    target.horItemScrollview = Utils.findRequiredViewAsType(source, R.id.hor_item_scrollview, "field 'horItemScrollview'", CustomHorizontalScrollView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ContentAdapter.ItemViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvLeftTitle = null;
    target.rvItemRight = null;
    target.horItemScrollview = null;
  }
}
