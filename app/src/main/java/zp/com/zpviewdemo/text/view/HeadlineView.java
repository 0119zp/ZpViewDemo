package zp.com.zpviewdemo.text.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import java.util.List;

import zp.com.zpviewdemo.R;


/**
 * 上下滚动 View
 * Created by Administrator on 2017/12/4 0004.
 */

public class HeadlineView extends RelativeLayout {

    View rootView;
    private ViewSwitcher viewSwitcher;
    private List<String> data;
    private LinearLayout subView1, subView2;
    private int currentPosition = 0;
    private final Runnable runnable = new Runnable() {

        @Override
        public void run() {
            currentPosition++;
            final ViewHolder holder = (ViewHolder) ((currentPosition % 2) == 0 ? subView1.getTag() : subView2.getTag());
            setContent(holder.content_tv, data.get(currentPosition % data.size()));
            viewSwitcher.setDisplayedChild(currentPosition % 2);
//            if (currentPosition >= data.size()) {
//                if (rootView != null) {
//                    rootView.setVisibility(View.GONE);
//                }
//                return;
//            }
            postDelayed(runnable, 3000);
        }
    };

    public HeadlineView(Context context) {
        this(context, null);
        initView();
    }

    public HeadlineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        initView();
    }

    private void initView() {
        rootView = LayoutInflater.from(getContext()).inflate(R.layout.headline_layout, this, true);
        viewSwitcher = (ViewSwitcher) rootView.findViewById(R.id.headline_viewswitcher);
        if (subView1 == null) {
            subView1 = (LinearLayout) viewSwitcher.findViewById(R.id.subView1);
            final ViewHolder holder = new ViewHolder();
            holder.content_tv = (TextView) subView1.findViewById(R.id.headline_content_tv);

            subView1.setTag(holder);
        }
        if (subView2 == null) {
            subView2 = (LinearLayout) viewSwitcher.findViewById(R.id.subView2);
            final ViewHolder holder = new ViewHolder();
            holder.content_tv = (TextView) subView2.findViewById(R.id.headline_content_tv);
            subView2.setTag(holder);
        }

        //进入动画
        viewSwitcher.setInAnimation(getContext(), R.anim.headline_in);
        //退出动画
        viewSwitcher.setOutAnimation(getContext(), R.anim.headline_out);

    }

    //配置滚动的数据
    public void setData(List<String> data) {
        this.data = data;
        currentPosition = 0;
        viewSwitcher.setDisplayedChild(0);
        setContent(((ViewHolder) subView1.getTag()).content_tv, data.get(0));
        if (data.size() >= 1) {
            this.setVisibility(View.VISIBLE);
            postDelayed(runnable, 3000);
        }
    }

    private void setContent(TextView view, String content) {
        view.setText(content);
    }

    public void stopRunnable() {
        if (runnable != null) {
            removeCallbacks(runnable);
        }
    }

    private class ViewHolder {
        TextView content_tv;
    }
}