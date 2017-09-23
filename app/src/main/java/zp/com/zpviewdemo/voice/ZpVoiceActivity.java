package zp.com.zpviewdemo.voice;

import android.view.View;
import android.widget.ImageView;

import java.util.Random;

import zp.com.zpbase.activity.ZpBaseActivity;
import zp.com.zpbase.view.ZpNavigationBar;
import zp.com.zpviewdemo.R;
import zp.com.zpviewdemo.voice.view.VoiceIconView;
import zp.com.zpviewdemo.voice.view.VoiceWaveView;

/**
 * Created by zpan on 2017/7/27 0027.
 */

public class ZpVoiceActivity extends ZpBaseActivity implements View.OnClickListener {

    private VoiceWaveView waveView;
    private VoiceIconView iconView;
    private ImageView speechImg;

    @Override
    public void initFNNavigationBar(ZpNavigationBar mTitle) {
        super.initFNNavigationBar(mTitle);
        showFNNavigationBar();
        mTitle.getmTitleName().setText("语音识别动画");
    }

    @Override
    protected int exInitLayout() {
        return R.layout.activity_voice;
    }

    @Override
    protected void exInitView() {
        super.exInitView();
        waveView = (VoiceWaveView) findViewById(R.id.vwv_voice);
        iconView = (VoiceIconView) findViewById(R.id.viv_icon);
        speechImg = (ImageView) findViewById(R.id.speech_icon);

        speechImg.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.speech_icon:
                iconView.setVisibility(View.VISIBLE, 0);
                speechImg.setVisibility(View.GONE);
                waveView.setVisibility(View.VISIBLE);
                for (int i = 0 ; i < 20 ; i++){
                    waveView.setVolume(new Random().nextInt(100));
                }
                break;
        }

    }

    // 结束波浪动画
    public void stopAnim(View view){
        waveView.setVisibility(View.GONE);
    }

    // 开始波浪动画
    public void startAnim(View view){
        waveView.setVisibility(View.VISIBLE);
        for (int i = 0 ; i < 20 ; i++){
            waveView.setVolume(new Random().nextInt(100));
        }
    }



}
