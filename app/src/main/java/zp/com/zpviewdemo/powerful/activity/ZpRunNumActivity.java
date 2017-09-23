package zp.com.zpviewdemo.powerful.activity;

import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;

import zp.com.zpbase.activity.ZpBaseActivity;
import zp.com.zpviewdemo.R;
import zp.com.zpviewdemo.powerful.view.runnum.NumberRunningTextView;

/**
 * Created by Administrator on 2017/8/8 0008.
 */

public class ZpRunNumActivity extends ZpBaseActivity{

    private SwipeRefreshLayout srlRoot;
    private NumberRunningTextView tvMoney;
    private NumberRunningTextView tvNum;

    @Override
    protected int exInitLayout() {
        return R.layout.activity_run_num;
    }

    @Override
    protected void exInitView() {
        super.exInitView();

        initView();
        initListener();

        tvMoney.setContent("1354.00");
        tvNum.setContent("200");
    }

    private void initView(){
        srlRoot = (SwipeRefreshLayout) findViewById(R.id.srl_root);
        tvMoney = (NumberRunningTextView) findViewById(R.id.tv_money);
        tvNum = (NumberRunningTextView) findViewById(R.id.tv_num);

        srlRoot.setColorSchemeColors(Color.parseColor("#ff7300"));
    }

    private void initListener() {
        srlRoot.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                tvMoney.setContent("1454.00");
                tvNum.setContent("300");
                srlRoot.setRefreshing(false);
            }
        });

    }
}
