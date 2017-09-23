package zp.com.zpviewdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import zp.com.zpbase.utils.ZpActivity;
import zp.com.zpviewdemo.bitmap.ZpBitmapActivity;
import zp.com.zpviewdemo.line.activity.ZpLineViewActivity;
import zp.com.zpviewdemo.load.ZpLoad01Activity;
import zp.com.zpviewdemo.load.ZpLoadActivity;
import zp.com.zpviewdemo.on_off.ZpSwitchActivity;
import zp.com.zpviewdemo.password.ZpPassWordActivity;
import zp.com.zpviewdemo.powerful.activity.ZpPowerfulActivity;
import zp.com.zpviewdemo.radar.ZpRadarActivity;
import zp.com.zpviewdemo.rainbow.ZpRainBowActivity;
import zp.com.zpviewdemo.shader.ZpShaderActivity;
import zp.com.zpviewdemo.shadow.ZpBitmapShadowActivity;
import zp.com.zpviewdemo.smile.ZpSmileActivity;
import zp.com.zpviewdemo.text.ZpTextActivity;
import zp.com.zpviewdemo.voice.ZpVoiceActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void setLine(View view){
        Intent intent = new Intent(MainActivity.this, ZpLineViewActivity.class);
        startActivity(intent);
    }

    public void setLoad(View view){
        Intent intent = new Intent(MainActivity.this, ZpLoadActivity.class);
        startActivity(intent);
    }

    public void setLoad_01(View view){
        ZpActivity.getInstance().start(MainActivity.this, ZpLoad01Activity.class);
    }

    public void button(View view){
        ZpActivity.getInstance().start(MainActivity.this, ZpCeshiActivity.class);
    }

    public void setVoice(View view){
        ZpActivity.getInstance().start(MainActivity.this, ZpVoiceActivity.class);
    }

    public void setShader(View view){
        ZpActivity.getInstance().start(MainActivity.this, ZpShaderActivity.class);
    }

    public void setGradientText(View view){
        ZpActivity.getInstance().start(MainActivity.this, ZpTextActivity.class);
    }

    public void setRainBow(View view){
        ZpActivity.getInstance().start(MainActivity.this, ZpRainBowActivity.class);
    }

    public void setBitmapShadow(View view){
        ZpActivity.getInstance().start(MainActivity.this, ZpBitmapShadowActivity.class);
    }

    public void setBitmap(View view){
        ZpActivity.getInstance().start(MainActivity.this, ZpBitmapActivity.class);
    }

    // 自定义输入密码
    public void setPassWord(View view){
        ZpActivity.getInstance().start(MainActivity.this, ZpPassWordActivity.class);
    }

    // 功能强大
    public void setPoworfulView(View view){
        ZpActivity.getInstance().start(MainActivity.this, ZpPowerfulActivity.class);
    }

    // 点赞笑脸
    public void setSmile(View view){
        ZpActivity.getInstance().start(MainActivity.this, ZpSmileActivity.class);
    }

    // 雷达分布图
    public void setRadar(View view){
        ZpActivity.getInstance().start(MainActivity.this, ZpRadarActivity.class);
    }

    // 开关
    public void setSwitch(View view) {
        ZpActivity.getInstance().start(MainActivity.this, ZpSwitchActivity.class);
    }

}
