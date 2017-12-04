package zp.com.zpviewdemo.text;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import zp.com.zpbase.activity.ZpBaseActivity;
import zp.com.zpviewdemo.R;
import zp.com.zpviewdemo.text.view.FlowLayout;

/**
 * Created by Administrator on 2017/12/4 0004.
 */

public class ZpTagActivity extends ZpBaseActivity {

    private FlowLayout flowLayout;

    @Override
    protected int exInitLayout() {
        return R.layout.activity_flowlayout;
    }

    @Override
    protected void exInitView() {
        super.exInitView();

        flowLayout = (FlowLayout) findViewById(R.id.fl);

        for (int i = 0; i < getData().size(); i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.item_tag_flowlayout, null);
            TextView textView = (TextView) view.findViewById(R.id.tv_tag);
            textView.setText(getData().get(i));

            flowLayout.addView(view);
        }

    }

    private ArrayList<String> getData() {
        ArrayList<String> list = new ArrayList<>();
        list.add("我喜欢");
        list.add("湖畔的风，因为有水的");
        list.add("因为有水的");
        list.add("我因为有水的");
        list.add("我。。。风");
        list.add("我=====风");

        return list;
    }

}
