package zp.com.zpviewdemo.shader;

import android.view.View;
import zp.com.zpbase.activity.ZpBaseActivity;
import zp.com.zpviewdemo.R;
import zp.com.zpviewdemo.shader.view.RadarVeiw;

/**
 * Created by zpan on 2017/8/4 0004.
 * 自定义雷达
 */

public class ZpShaderActivity extends ZpBaseActivity{

    private RadarVeiw radarVeiw;

    @Override
    protected int exInitLayout() {
        return R.layout.activity_shader;
    }

    @Override
    protected void exInitView() {
        super.exInitView();
        radarVeiw = (RadarVeiw) findViewById(R.id.rv_radar_view);
    }

    public void start(View view){
        radarVeiw.startScan();
    }

    public void end(View view){
        radarVeiw.stopScan();
    }
}
