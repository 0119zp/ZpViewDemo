package zp.com.zpviewdemo.password;

import android.view.View;

import zp.com.zpbase.activity.ZpBaseActivity;
import zp.com.zpbase.utils.ZpLog;
import zp.com.zpviewdemo.R;
import zp.com.zpviewdemo.password.view.PasswordView;

/**
 * Created by zpan on 2017/8/7 0007.
 * 自定义密码输入
 */

public class ZpPassWordActivity extends ZpBaseActivity{

    private static final String TAG = ZpPassWordActivity.class.getName();

    private PasswordView passwordView;

    @Override
    protected int exInitLayout() {
        return R.layout.activity_password;
    }

    @Override
    protected void exInitView() {
        super.exInitView();
        passwordView = (PasswordView) findViewById(R.id.passwordView);
        passwordView.setPasswordListener(passwordListener);
    }

    public void setChangMode(View view){
        passwordView.setMode(passwordView.getMode() == PasswordView.Mode.RECT ? PasswordView.Mode.UNDERLINE : PasswordView.Mode.RECT);
    }

    private PasswordView.PasswordListener passwordListener = new PasswordView.PasswordListener() {
        @Override
        public void passwordChange(String changeText) {
            ZpLog.getInstance().e(TAG,"=changeText ==" + changeText);
        }

        @Override
        public void passwordComplete() {
            ZpLog.getInstance().e(TAG,"=passwordComplete ==");
        }

        @Override
        public void keyEnterPress(String password, boolean isComplete) {
            ZpLog.getInstance().e(TAG,"=password ==" + password + "=isComplete==" + isComplete);
        }
    };

}
