package zp.com.zpviewdemo.radar;


import java.util.LinkedHashMap;

import zp.com.zpbase.activity.ZpBaseActivity;
import zp.com.zpviewdemo.R;
import zp.com.zpviewdemo.radar.view.RadarChartView;

/**
 * Created by Administrator on 2017/9/23 0023.
 */

public class ZpRadarActivity extends ZpBaseActivity {

    private RadarChartView radarChartView;

    @Override
    protected int exInitLayout() {
        return R.layout.activity_radar;
    }


    @Override
    protected void exInitView() {
        super.exInitView();

        radarChartView = (RadarChartView) findViewById(R.id.radar);

        LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
        map.put("数学",98);
        map.put("语文",88);
        map.put("英语",68);
        map.put("化学",98);
        map.put("物理",78);

        radarChartView.setMap(map);
        radarChartView.setCount(map.size());

    }
}
