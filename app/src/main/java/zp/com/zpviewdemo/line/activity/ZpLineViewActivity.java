package zp.com.zpviewdemo.line.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.List;
import zp.com.zpviewdemo.R;
import zp.com.zpviewdemo.line.view.LineChartView;

/**
 * Created by Administrator on 2017/7/21 0021.
 */

public class ZpLineViewActivity extends Activity {

    private LineChartView mLineChartView;
    private List<LineChartView.ItemBean> mItems;
    private int[] shadeColors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line);
        initView();
        initData();
    }

    private void initData() {
        //  初始化折线数据
        mItems = new ArrayList<>();
        mItems.add(new LineChartView.ItemBean(1489507200, 23));
        mItems.add(new LineChartView.ItemBean(1489593600, 88));
        mItems.add(new LineChartView.ItemBean(1489680000, 60));
        mItems.add(new LineChartView.ItemBean(1489766400, 50));
        mItems.add(new LineChartView.ItemBean(1489852800, 70));
        mItems.add(new LineChartView.ItemBean(1489939200, 10));
        mItems.add(new LineChartView.ItemBean(1490025600, 33));
        mItems.add(new LineChartView.ItemBean(1490112000, 44));
        mItems.add(new LineChartView.ItemBean(1490198400, 99));
        mItems.add(new LineChartView.ItemBean(1490284800, 17));

        shadeColors= new int[]{
                Color.argb(100, 255, 86, 86),
                Color.argb(15, 255, 86, 86),
                Color.argb(0, 255, 86, 86)};

        //  设置折线数据
        mLineChartView.setItems(mItems);
        //  设置渐变颜色
        mLineChartView.setShadeColors(shadeColors);
        //  开启动画
        mLineChartView.startAnim(mLineChartView,5000);
    }

    private void initView() {
        mLineChartView = (LineChartView) findViewById(R.id.lcv);
    }
}
