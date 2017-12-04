package zp.com.zpviewdemo.text;

import android.view.View;

import zp.com.zpbase.activity.ZpBaseActivity;
import zp.com.zpbase.utils.ZpActivity;
import zp.com.zpviewdemo.MainActivity;
import zp.com.zpviewdemo.R;

/**
 * Created by Administrator on 2017/8/10 0010.
 */

public class ZpTextActivity extends ZpBaseActivity{

    @Override
    protected int exInitLayout() {
        return R.layout.activity_text;
    }


    public void setSplash(View view){
        ZpActivity.getInstance().start(ZpTextActivity.this, ZpGradentTextActivity.class);
    }

    public void setUpDown(View view){
        ZpActivity.getInstance().start(ZpTextActivity.this, ZpUpDownTextActivity.class);
    }

    public void setGundong(View view) {
        ZpActivity.getInstance().start(ZpTextActivity.this, ZpGundongActivity.class);
    }

    // 字母选择
    public void setSideBar(View view){
        ZpActivity.getInstance().start(ZpTextActivity.this, ZpSideBarActivity.class);
    }

    public void setTag(View view) {
        ZpActivity.getInstance().start(ZpTextActivity.this, ZpTagActivity.class);
    }

}
