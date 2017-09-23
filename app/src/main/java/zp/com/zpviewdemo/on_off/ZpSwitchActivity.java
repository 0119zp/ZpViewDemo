package zp.com.zpviewdemo.on_off;

import android.content.Intent;
import android.view.View;

import zp.com.zpbase.activity.ZpBaseActivity;
import zp.com.zpviewdemo.R;

/**
 * Created by Administrator on 2017/9/24 0024.
 */

public class ZpSwitchActivity extends ZpBaseActivity {

    @Override
    protected int exInitLayout() {
        return R.layout.activity_switch;
    }

    // 仿Ios开关
    public void setIos(View view) {
        Intent intent = new Intent(ZpSwitchActivity.this, ZpIosSwitchActivity.class);
        startActivity(intent);
    }


}
