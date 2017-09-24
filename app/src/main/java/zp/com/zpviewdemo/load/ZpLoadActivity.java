package zp.com.zpviewdemo.load;


import android.view.View;

import zp.com.zpbase.activity.ZpBaseActivity;
import zp.com.zpbase.utils.ZpActivity;
import zp.com.zpviewdemo.R;

/**
 * Created by Administrator on 2017/7/21 0021.
 */

public class ZpLoadActivity extends ZpBaseActivity {

    @Override
    protected int exInitLayout() {
        return R.layout.activity_load;
    }

    public void setLoad01(View view) {
        ZpActivity.getInstance().start(ZpLoadActivity.this, ZpLoad01Activity.class);
    }

    public void setLoad02(View view) {
        ZpActivity.getInstance().start(ZpLoadActivity.this, ZpLoad02Activity.class);
    }



}
