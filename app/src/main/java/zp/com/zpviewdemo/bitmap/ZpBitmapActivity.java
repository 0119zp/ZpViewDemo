package zp.com.zpviewdemo.bitmap;

import android.view.View;

import zp.com.zpbase.activity.ZpBaseActivity;
import zp.com.zpbase.utils.ZpActivity;
import zp.com.zpviewdemo.R;

/**
 * Created by zpan on 2017/8/4 0004.
 * 自定义图片处理
 */

public class ZpBitmapActivity extends ZpBaseActivity{


    @Override
    protected int exInitLayout() {
        return R.layout.activity_bitmap;
    }

    public void setCircle(View view){
        ZpActivity.getInstance().start(ZpBitmapActivity.this, ZpCircleActivity.class);
    }

}
