package zp.com.zpviewdemo.on_off;

import zp.com.zpbase.activity.ZpBaseActivity;
import zp.com.zpviewdemo.R;
import zp.com.zpviewdemo.on_off.view.ZpWiperSwitch;

/**
 * Created by Administrator on 2017/9/24 0024.
 */

public class ZpWiperSwitchActivity extends ZpBaseActivity {

    private ZpWiperSwitch zpWiperSwitch;

    @Override
    protected int exInitLayout() {
        return R.layout.activity_switch_wiper;
    }

    @Override
    protected void exInitView() {
        super.exInitView();
        zpWiperSwitch = (ZpWiperSwitch) findViewById(R.id.wiperswitch);


    }
}
