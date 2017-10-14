package zp.com.zpviewdemo.ceshi;

import android.view.View;

import zp.com.zpbase.activity.ZpBaseActivity;
import zp.com.zpbase.fragment.ZpOperationNotNetworkFragment;
import zp.com.zpbase.utils.ZpLog;
import zp.com.zpbase.utils.ZpToast;
import zp.com.zpbase.view.ZpNavigationBar;
import zp.com.zpviewdemo.R;

/**
 * Created by Administrator on 2017/7/25 0025.
 */

public class ZpCeshiActivity extends ZpBaseActivity {


    @Override
    public void initFNNavigationBar(ZpNavigationBar mTitle) {
        super.initFNNavigationBar(mTitle);
        showFNNavigationBar();
        mTitle.getmTitleName().setText("测试");
        mTitle.getmTitleRight().setVisibility(View.VISIBLE);
        mTitle.getmTitleRight().setText("右侧");
        mTitle.getmTitleRight().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ZpToast.getInstance().showShort(ZpCeshiActivity.this, "点击右侧按钮");
            }
        });
    }

    @Override
    protected int exInitLayout() {
        return R.layout.activity_ceshi;
    }

    @Override
    public ZpOperationNotNetworkFragment.OperaNetworkCallback isShowNotNetworkOfOnCreate() {

        return new ZpOperationNotNetworkFragment.OperaNetworkCallback() {
            @Override
            public void onClickRefresh() {
                operaHide();
            }

            @Override
            public void show() {

            }

            @Override
            public void hide() {

            }
        };
    }

    @Override
    protected void exInitAfter() {
        super.exInitAfter();
        ZpLog.getInstance().e("===== exInitAfter ======");
        ZpToast.getInstance().showShort(ZpCeshiActivity.this, "刷新页面");
    }
}
