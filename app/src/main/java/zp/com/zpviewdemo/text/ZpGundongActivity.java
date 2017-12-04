package zp.com.zpviewdemo.text;

import java.util.ArrayList;

import zp.com.zpbase.activity.ZpBaseActivity;
import zp.com.zpviewdemo.R;
import zp.com.zpviewdemo.text.view.HeadlineView;

/**
 * Created by Administrator on 2017/12/4 0004.
 */

public class ZpGundongActivity extends ZpBaseActivity {

    private HeadlineView lv;

    @Override
    protected int exInitLayout() {
        return R.layout.activity_gun_dong;
    }

    @Override
    protected void exInitView() {
        super.exInitView();

        lv = (HeadlineView) findViewById(R.id.lv);

        lv.setData(getData());
    }

    private ArrayList<String> getData() {
        ArrayList<String> list = new ArrayList<>();
        list.add("我喜欢湖畔的风");
        list.add("我喜欢湖畔的风，因为有水的");
        list.add("我喜欢湖因为有水的");
        list.add("我因为有水的");
        list.add("我。。。。。。风");
        list.add("我===========风");

        return list;
    }


}
