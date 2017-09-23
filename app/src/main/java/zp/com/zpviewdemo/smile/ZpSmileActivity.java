package zp.com.zpviewdemo.smile;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import zp.com.zpbase.activity.ZpBaseActivity;
import zp.com.zpviewdemo.R;
import zp.com.zpviewdemo.smile.view.ZpSmlieView;

/**
 * Created by Administrator on 2017/8/9 0009.
 */

public class ZpSmileActivity extends ZpBaseActivity{

    SeekBar seekBar;
    LinearLayout backGround;
    ImageView smileFace;

    ZpSmlieView smileView;

    @Override
    protected int exInitLayout() {
        return R.layout.activity_smile;
    }

    @Override
    protected void exInitView() {
        super.exInitView();

        seekBar = (SeekBar) findViewById(R.id.seekBar);
        backGround = (LinearLayout) findViewById(R.id.backGround);
        smileFace = (ImageView) findViewById(R.id.smileFace);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) smileFace.getLayoutParams();
                layoutParams.bottomMargin = i*3;
                smileFace.setLayoutParams(layoutParams);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        smileView = (ZpSmlieView) findViewById(R.id.smileView);
        smileView.setNum(60,40);

    }
}
