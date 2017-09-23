package zp.com.zpviewdemo.text;

import android.util.Log;
import zp.com.zpbase.activity.ZpBaseActivity;
import zp.com.zpviewdemo.R;
import zp.com.zpviewdemo.text.view.ZpSideBar;

/**
 * Created by Administrator on 2017/9/23 0023.
 */

public class ZpSideBarActivity extends ZpBaseActivity {

    private ZpSideBar zpSideBar;

    @Override
    protected int exInitLayout() {
        return R.layout.activity_sidebar;
    }

    @Override
    protected void exInitView() {
        super.exInitView();

        zpSideBar = (ZpSideBar) findViewById(R.id.sidebar);

        zpSideBar.setOnTouchingLetterChangedListener(new ZpSideBar.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String s) {
                Log.e("zpan","=== s ====" + s);
            }
        });
    }
}
