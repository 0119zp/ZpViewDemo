package zp.com.zpviewdemo.gif;

import android.view.View;

import zp.com.zpbase.activity.ZpBaseActivity;
import zp.com.zpbase.utils.ZpActivity;
import zp.com.zpviewdemo.R;

/**
 * Created by Administrator on 2017/9/24 0024.
 */

public class ZpGifActivity extends ZpBaseActivity {

    @Override
    protected int exInitLayout() {
        return R.layout.activity_gif;
    }

    public void setGif_01(View view) {
        ZpActivity.getInstance().start(ZpGifActivity.this, ZpGifMovieActivity.class);
    }


}
