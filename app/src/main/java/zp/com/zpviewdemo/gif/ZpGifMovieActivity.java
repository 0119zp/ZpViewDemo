package zp.com.zpviewdemo.gif;

import zp.com.zpbase.activity.ZpBaseActivity;
import zp.com.zpviewdemo.R;
import zp.com.zpviewdemo.gif.view.GifMovieView;

/**
 * Created by Administrator on 2017/9/24 0024.
 */

public class ZpGifMovieActivity extends ZpBaseActivity {

    private GifMovieView gifMovieView;

    @Override
    protected int exInitLayout() {
        return R.layout.activity_movie;
    }

    @Override
    protected void exInitView() {
        super.exInitView();
        gifMovieView = (GifMovieView) findViewById(R.id.gif_movie);
        gifMovieView.setMovieResource(R.drawable.dongtu);
    }

}
