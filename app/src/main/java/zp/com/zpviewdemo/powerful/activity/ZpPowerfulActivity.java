package zp.com.zpviewdemo.powerful.activity;

import android.view.View;

import zp.com.zpbase.activity.ZpBaseActivity;
import zp.com.zpbase.utils.ZpActivity;
import zp.com.zpviewdemo.R;

/**
 * Created by Administrator on 2017/8/8 0008.
 */

public class ZpPowerfulActivity extends ZpBaseActivity{

    @Override
    protected int exInitLayout() {
        return R.layout.activity_powerful;
    }


    public void setEditText(View view){
        ZpActivity.getInstance().start(ZpPowerfulActivity.this, ZpEditTextActivity.class);
    }

    public void setChartView(View view){
        ZpActivity.getInstance().start(ZpPowerfulActivity.this, ZpChartViewActivity.class);
    }

    public void setRunNum(View view){
        ZpActivity.getInstance().start(ZpPowerfulActivity.this, ZpRunNumActivity.class);
    }

    public void setTextView(View view){
        ZpActivity.getInstance().start(ZpPowerfulActivity.this, ZpExpandableTextViewActivity.class);
    }


}
