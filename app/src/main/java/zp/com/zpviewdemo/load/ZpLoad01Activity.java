package zp.com.zpviewdemo.load;

import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ScrollView;

import zp.com.zpbase.activity.ZpBaseActivity;
import zp.com.zpbase.utils.ZpLog;
import zp.com.zpviewdemo.R;
import zp.com.zpviewdemo.load.view.ProgressbarView;
import zp.com.zpviewdemo.load.view.PullDownRefreshView;

/**
 * Created by zpan on 2017/8/1 0001.
 */

public class ZpLoad01Activity extends ZpBaseActivity {

    private static final String TAG = ZpLoad01Activity.class.getName();

    private ScrollView card_scrview;
    private PullDownRefreshView card_pay_pull_refresh_view;

    private ProgressbarView pbView;

    @Override
    protected int exInitLayout() {
        return R.layout.activity_load_01;
    }

    @Override
    protected void exInitView() {
        super.exInitView();

        card_scrview = (ScrollView) findViewById(R.id.card_scrview);
        card_pay_pull_refresh_view = (PullDownRefreshView) findViewById(R.id.card_pay_pull_refresh_view);

        if(null!=card_pay_pull_refresh_view){
            card_pay_pull_refresh_view.setLockDown(true);
        }

        card_pay_pull_refresh_view.setmLock(false);
        card_pay_pull_refresh_view.setHeaderRefreshListener(new PullDownRefreshView.HeaderRefresh() {
            @Override
            public void onHeaderRefresh(PullDownRefreshView view) {
                ZpLog.getInstance().e(TAG, "====== onHeaderRefresh =====");
            }
        });

        pbView = (ProgressbarView) findViewById(R.id.pv_load);
        pbView.setBackgroundCircle(0xAAe8e8e8);//背景淡灰色
        pbView.setPreviousCirle(0x66ff0000);//进度前景色
        pbView.setRoundWidth(20);
        pbView.setBackgroundDrawable(new BitmapDrawable(BitmapFactory.decodeResource(this.getResources(), R.drawable.money_rmb)));
        pbView.setValues(60f);
    }

}
