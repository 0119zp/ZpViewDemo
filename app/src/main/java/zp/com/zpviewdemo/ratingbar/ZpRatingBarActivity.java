package zp.com.zpviewdemo.ratingbar;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;
import zp.com.zpviewdemo.R;
import zp.com.zpviewdemo.ratingbar.view.ZpRatingBar;

/**
 * Created by Administrator on 2017/10/14 0014.
 */

public class ZpRatingBarActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ratingbar);

        ZpRatingBar ratingBar = (ZpRatingBar) findViewById(R.id.ratingBar);
        ratingBar.setClickable(false);
        ratingBar.setStar(3.5f);

    }
}
