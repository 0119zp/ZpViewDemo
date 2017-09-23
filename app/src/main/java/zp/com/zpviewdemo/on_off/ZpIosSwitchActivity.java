package zp.com.zpviewdemo.on_off;

import android.graphics.Color;

import zp.com.zpbase.activity.ZpBaseActivity;
import zp.com.zpbase.utils.ZpLog;
import zp.com.zpviewdemo.R;
import zp.com.zpviewdemo.on_off.view.IosSwitchView;

/**
 * Created by Administrator on 2017/9/24 0024.
 */

public class ZpIosSwitchActivity extends ZpBaseActivity {

    private IosSwitchView iosSwitchView;

    @Override
    protected int exInitLayout() {
        return R.layout.activity_switch_ios;
    }

    @Override
    protected void exInitView() {
        super.exInitView();

        iosSwitchView = (IosSwitchView) findViewById(R.id.switchView);

        iosSwitchView.setOn(true);
        iosSwitchView.setOnSwitchStateChangeListener(new IosSwitchView.OnSwitchStateChangeListener() {
            @Override
            public void onSwitchStateChange(boolean isOn) {
                ZpLog.getInstance().e("==== isOn ==== " + isOn);
            }
        });

        iosSwitchView.setTintColor(Color.RED);
//        iosSwitchView.setEnabled(false);

    }
}
