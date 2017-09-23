package zp.com.zpviewdemo.text;

import android.graphics.Color;
import android.view.View;

import zp.com.zpbase.activity.ZpBaseActivity;
import zp.com.zpviewdemo.R;
import zp.com.zpviewdemo.text.view.UpDownTextView;

/**
 * Created by zpan on 2017/8/10 0010.
 * 自定义文字上下滚动
 */

public class ZpUpDownTextActivity extends ZpBaseActivity{

    private String[] strings = {"啦啦啦","德马西亚","我喜欢湖畔的风","因为有水的宁静"};
    private UpDownTextView upDownText;

    @Override
    protected int exInitLayout() {
        return R.layout.activity_text_up_down;
    }

    @Override
    protected void exInitView() {
        super.exInitView();
        upDownText = (UpDownTextView) findViewById(R.id.tv_up_down);
        upDownText.setTextList(strings);
        upDownText.setTextColor(Color.parseColor("#00ff00"));
    }

    // 开始滚动
    public void setStart(View view){
        upDownText.startAutoScroll();
    }

    // 停止滚动
    public void setStop(View view){
        upDownText.stopAutoScroll();
    }

}
