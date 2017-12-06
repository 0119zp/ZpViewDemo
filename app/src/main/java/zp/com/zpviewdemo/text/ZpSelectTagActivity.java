package zp.com.zpviewdemo.text;

import java.util.ArrayList;
import java.util.List;

import zp.com.zpbase.activity.ZpBaseActivity;
import zp.com.zpviewdemo.R;
import zp.com.zpviewdemo.text.view.ZpSelectTag;

/**
 * Created by Administrator on 2017/12/6 0006.
 */

public class ZpSelectTagActivity extends ZpBaseActivity {

    @Override
    protected int exInitLayout() {
        return R.layout.activity_select_tag;
    }

    @Override
    protected void exInitView() {
        super.exInitView();

        ZpSelectTag tags = (ZpSelectTag) findViewById(R.id.st_select_tag_source);

        tags.setData(getList());
        tags.setMultiSelect(false);
        tags.addSelected(0);
        tags.setSelectChangedListener(new ZpSelectTag.HftSelectTagListener() {

            @Override
            public void onSelectChanged(List<Integer> positions) {

            }
        });
    }

    private ArrayList<String> getList() {
        ArrayList<String> list = new ArrayList<>();
        list.add("倚天剑");
        list.add("屠龙刀");
        list.add("扛把子");
        list.add("张无忌");
        list.add("哈哈哈哈");
        list.add("你好");
        list.add("小果壳");
        list.add("黑天");
        list.add("白天");
        return list;
    }

}
