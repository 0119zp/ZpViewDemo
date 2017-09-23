package zp.com.zpviewdemo.load.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import zp.com.zpbase.utils.ZpLog;
import zp.com.zpviewdemo.R;

/**
 * Created by zpan on 2017/8/1 0001.
 */

public class PullDownRefreshView extends LinearLayout {

    private static final String TAG = PullDownRefreshView.class.getName();

    private LayoutInflater layoutInflater;
    private View headerView;                    //头布局
    private Context context;
    private ImageView top_img;                  //头部钱包
    private int mheaderViewHeight;
    private boolean mLock = false;              //是否锁住下拉功能
    private ScrollView mScrollView;             //中间布局scrollview
    private AdapterView<?> mAdapterView;        //或者中间布局为listview
    private int mHeaderState;
    private int mPullState;
    private final int PULL_TO_REFRESH = 2;
    private final int RELEASE_TO_REFRESH = 3;
    private final int REFRESHING = 4;
    // pull state
    private final int PULL_UP_STATE = 0;
    private final int PULL_DOWN_STATE = 1;
    private int mLastMotionY;                    //开始触摸屏幕时，点击的Y轴的坐标
    private HeaderRefresh mOnHeaderRefreshListener;     //下拉刷新接口
    private FooterRefresh refresh;                      //上拉加载接口
    private MarginTopValue marginTopValue;
    private AnimationDrawable animation;
    private RelativeLayout relativeLayout;              //头布局背景
    private ImageView img_footer;
    private View footerView;
    private RelativeLayout relativeLayout_footer;
    private int mfooterViewHight;
    private int mFooterState;
    private AnimationDrawable footeranimation;
    private boolean lockdown = false;                   //是否锁住上拉
    private int pulldownview_w, pulldownview_h;
    private ProgressbarView pullDownProgressBar;        //下拉刷新进度条
    private ScrollListenner scrollListenner;


    public PullDownRefreshView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public PullDownRefreshView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public PullDownRefreshView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    public void init() {
        layoutInflater = LayoutInflater.from(context);
        addHeaderView();
        scrollListenner = new ScrollListenner();
    }

    @Override
    public void onFinishInflate() {         //加载完xml布局
        super.onFinishInflate();
        addFooterView();
        initContentView();
    }

    public void addHeaderView() {           //pull_down_refreshview
        headerView = layoutInflater.inflate(R.layout.pull_down_refreshview, null);
        top_img = (ImageView) headerView.findViewById(R.id.pull_down_refreshview_img_top);
        pullDownProgressBar = (ProgressbarView) headerView.findViewById(R.id.pull_down_refreshview_rel_progress);
        relativeLayout = (RelativeLayout) headerView.findViewById(R.id.pull_down_refreshview_rel);
        measureView(headerView);
        mheaderViewHeight = headerView.getMeasuredHeight();
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mheaderViewHeight);
        layoutParams.topMargin = -mheaderViewHeight;                //隐藏布局扯出屏幕
        pullDownProgressBar.setBackgroundCircle(0xAAe8e8e8);        //背景淡灰色
        pullDownProgressBar.setPreviousCirle(0xffA1C3E9);           //进度前景色
        pullDownProgressBar.setRoundWidth(6);
        addView(headerView, layoutParams);
        measureView(top_img);
        pulldownview_w = top_img.getMeasuredWidth();
        pulldownview_h = top_img.getMeasuredHeight();
//        pulldownview_w=dip2px(43);
//        pulldownview_h=dip2px(63);
    }

    public void addFooterView() {           //底部布局
        footerView = layoutInflater.inflate(R.layout.pull_down_headview, null);
        img_footer = (ImageView) footerView.findViewById(R.id.pull_down_footerview_img);
        relativeLayout_footer = (RelativeLayout) footerView.findViewById(R.id.pull_down_footerview);
        measureView(footerView);
        mfooterViewHight = footerView.getMeasuredHeight();
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mfooterViewHight);
        layoutParams.topMargin = mfooterViewHight;
        addView(footerView, layoutParams);
    }

    /**
     * 将中间身体布局赋值
     */
    public void initContentView() {
        int count = getChildCount();
        if (count < 3) {                //必须有中间布局
            throw new IllegalArgumentException(
                    "this layout must contain 3 child views,and AdapterView or ScrollView must in the second position!");
        }
        View view = null;
        for (int i = 0; i < count; ++i) {
            view = getChildAt(i);
            if (view instanceof AdapterView<?>) {
                mAdapterView = (AdapterView<?>) view;
            }
            if (view instanceof ScrollView) {
                // finish later
                mScrollView = (ScrollView) view;
            }
        }
        if (mAdapterView == null && mScrollView == null) {
            throw new IllegalArgumentException("must contain a AdapterView or ScrollView in this layout!");
        }
        if (null != mAdapterView) {
            ((ListView) mAdapterView).setOnScrollListener(scrollListenner);
        }
    }

    /**
     * 重新测量控件高度
     */
    private void measureView(View child) {
        ViewGroup.LayoutParams p = child.getLayoutParams();
        if (p == null) {
            p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
        int lpHeight = p.height;
        int childHeightSpec;
        if (lpHeight > 0) {
            childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight,
                    MeasureSpec.EXACTLY);
        } else {
            childHeightSpec = MeasureSpec.makeMeasureSpec(0,
                    MeasureSpec.UNSPECIFIED);
        }
        child.measure(childWidthSpec, childHeightSpec);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        int y = (int) e.getRawY();
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastMotionY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaY = y - mLastMotionY;
                if ((!mLock || !lockdown) && isRefreshViewScroll(deltaY)) {
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        return false;
    }

    public void setView(View view) {
        this.view = view;
    }

    private View view;


    /*
     * 如果在onInterceptTouchEvent()方法中没有拦截(即onInterceptTouchEvent()方法中 return
	 * false)则由PullToRefreshView 的子View来处理;否则由下面的方法来处理(即由PullToRefreshView自己来处理)
	 */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int y = (int) event.getRawY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // mLastMotionY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                if (null != marginTopValue) {
                    marginTopValue.onCurrentMarginTop(getHeaderTopMargin());
                }
                int deltaY = y - mLastMotionY;
                if (mPullState == PULL_DOWN_STATE) {
                    headerPrepareToRefresh(deltaY);
                    // setHeaderPadding(-mHeaderViewHeight);
                } else if (mPullState == PULL_UP_STATE) {
                    footerPrepareToRefresh(deltaY);
                }
                mLastMotionY = y;
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                int topMargin = getHeaderTopMargin();
                if (mPullState == PULL_DOWN_STATE) {
                    if (topMargin >= 0) {
                        // 开始刷新
                        headerRefreshing();
                    } else {
                        // 还没有执行刷新，重新隐藏
                        if (null != view) {
                            view.setAlpha(1.0f);
                        }
                        setHeaderTopMargin(-mheaderViewHeight);
                    }
                } else if (mPullState == PULL_UP_STATE) {
                    if (Math.abs(topMargin) >= mheaderViewHeight + mfooterViewHight) {
                        // 开始执行footer 刷新
                        footerRefreshing();
                    } else {
                        // 还没有执行刷新，重新隐藏
                        if (null != view) {
                            view.setAlpha(1.0f);
                        }
                        setHeaderTopMargin(-mheaderViewHeight);
                        setFooterTopMargin(mfooterViewHight);
                    }
                }
                break;
        }
        return super.onTouchEvent(event);
    }


    /**
     * 是否应该到了父View,即PullToRefreshView滑动
     *
     * @param deltaY deltaY > 0 是向下运动,< 0是向上运动
     * @return
     */
    private boolean isRefreshViewScroll(int deltaY) {
        if (mHeaderState == REFRESHING || mFooterState == REFRESHING) {
            return true;
        }
        //对于ListView和GridView
        if (mAdapterView != null) {
            // 子view(ListView or GridView)滑动到最顶端
            if (deltaY > 20) {
                View child = mAdapterView.getChildAt(0);
                if (child == null) {
                    // 如果mAdapterView中没有数据,不拦截
                    return false;
                }
                if (mAdapterView.getFirstVisiblePosition() == 0 && child.getTop() == 0) {
                    mPullState = PULL_DOWN_STATE;
                    return true;
                }
                int top = child.getTop();
                int padding = mAdapterView.getPaddingTop();
                if (mAdapterView.getFirstVisiblePosition() == 0 && Math.abs(top - padding) <= 8) {
                    mPullState = PULL_DOWN_STATE;
                    return true;
                }
            } else if (deltaY < -20) {
                View lastChild = mAdapterView.getChildAt(mAdapterView.getChildCount() - 1);
                if (lastChild == null) {
                    // 如果mAdapterView中没有数据,不拦截
                    return false;
                }
                // 最后一个子view的Bottom小于父View的高度说明mAdapterView的数据没有填满父view,
                // 等于父View的高度说明mAdapterView已经滑动到最后
                if (lastChild.getBottom() <= getHeight() && mAdapterView.getLastVisiblePosition() == mAdapterView.getCount() - 1) {
                    mPullState = PULL_UP_STATE;
                    if (!lockdown) {
                        return true;
                    }
                    return false;
                }
            }
        }
        // 对于ScrollView
        if (mScrollView != null) {
            // 子scroll view滑动到最顶端
            View child = mScrollView.getChildAt(0);
            if (deltaY > 0 && mScrollView.getScrollY() == 0) {
                mPullState = PULL_DOWN_STATE;
                return true;
            } else if (deltaY < 0
                    && child.getMeasuredHeight() <= getHeight()
                    + mScrollView.getScrollY()) {
                mPullState = PULL_UP_STATE;
                if (!lockdown) {
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    /**
     * 是否锁住下拉功能，默认不锁
     */
    public void setmLock(boolean islock) {
        this.mLock = islock;
    }

    /**
     * 获取头布局高度
     */
    private int getHeaderTopMargin() {
        LayoutParams params = (LayoutParams) headerView.getLayoutParams();
        return params.topMargin;
    }

    /**
     * 重新设置头布局margintop
     */
    private void setHeaderTopMargin(int topMargin) {
        LayoutParams params = (LayoutParams) headerView.getLayoutParams();
        params.topMargin = topMargin;
        headerView.setLayoutParams(params);
        invalidate();//重新绘制
    }

    private void setFooterTopMargin(int topMargin) {
        LayoutParams params = (LayoutParams) footerView.getLayoutParams();
        params.topMargin = topMargin;
        footerView.setLayoutParams(params);
        invalidate();//重新绘制
    }

    /**
     * header 准备刷新,手指移动过程,还没有释放
     *
     * @param deltaY 手指滑动的距离
     */
    private void headerPrepareToRefresh(int deltaY) {
        if (!mLock) {
            top_img.setImageDrawable(getResources().getDrawable(R.drawable.money_0001));
            int newTopMargin = changingHeaderViewTopMargin(deltaY);
            ZpLog.getInstance().e(TAG, "newTopMargin===" + newTopMargin);
            if (newTopMargin < -20) {//缩放view
                pullDownProgressBar.setValues(0);
                ViewGroup.LayoutParams layoutParams = top_img.getLayoutParams();
                layoutParams.width = pulldownview_w * (mheaderViewHeight + newTopMargin) / (mheaderViewHeight - 20);
                layoutParams.height = pulldownview_h * (mheaderViewHeight + newTopMargin) / (mheaderViewHeight - 20);
                top_img.setLayoutParams(layoutParams);
            } else {
                int value = 100 * (20 + newTopMargin) / 20;
                if (value > 0) {
                    pullDownProgressBar.setVisibility(View.VISIBLE);
                    pullDownProgressBar.setValues(value);//滞后0.1
                    if (value > 96) {
//                        BitmapAndDrawable.recycleViewBg(pullDownProgressBar);
                        pullDownProgressBar.setBackgroundDrawable(new BitmapDrawable(BitmapFactory.decodeResource(context.getResources(), R.drawable.money_rmb)));
                    }
                }
            }
            if (newTopMargin >= 0 && mHeaderState != RELEASE_TO_REFRESH) {
                mHeaderState = RELEASE_TO_REFRESH;
            } else if (newTopMargin < 0 && newTopMargin > -mheaderViewHeight) {// 拖动时没有释放
                mHeaderState = PULL_TO_REFRESH;
            }
            invalidate();
        }
    }

    /**
     * 修改Header view top margin的值
     */
    private int changingHeaderViewTopMargin(int deltaY) {
        LayoutParams params = (LayoutParams) headerView.getLayoutParams();
        float newTopMargin = params.topMargin + deltaY * 0.3f;
        if (deltaY > 0 && mPullState == PULL_UP_STATE && Math.abs(params.topMargin) <= mheaderViewHeight) {
            return params.topMargin;
        }
        if (deltaY < 0 && mPullState == PULL_DOWN_STATE && Math.abs(params.topMargin) >= mheaderViewHeight) {
            return params.topMargin;
        }
        params.topMargin = (int) newTopMargin;
        headerView.setLayoutParams(params);
        invalidate();
        return params.topMargin;
    }

    private void changingFooterViewTopMargin(int deltaY) {
        LayoutParams params = (LayoutParams) headerView.getLayoutParams();
        float newTopMargin = params.topMargin + deltaY * 0.3f;
        params.topMargin = (int) newTopMargin;
        headerView.setLayoutParams(params);
    }

    /**
     * header view 完成更新后恢复初始状态,并且回收动画
     */
    public void onHeaderRefreshComplete() {
        if (mFooterState != REFRESHING) {
            if (null != animation) {
                top_img.clearAnimation();
                top_img.setImageDrawable(getResources().getDrawable(R.drawable.money_0001));
                tryRecycleAnimationDrawable(animation);
                animation = null;//改变指向
            }
            ScaleAnimation scaleanimation = new ScaleAnimation(1f, 0f, 1f, 0f,
                    Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            scaleanimation.setDuration(200);//设置动画持续时间
            scaleanimation.setRepeatCount(0);//设置重复次数
            scaleanimation.setFillAfter(true);//动画执行完后是否停留在执行完的状态
            top_img.startAnimation(scaleanimation);
//            BitmapAndDrawable.recycleViewBg(pullDownProgressBar);
            pullDownProgressBar.setBackgroundDrawable(new BitmapDrawable(BitmapFactory.decodeResource(context.getResources(), R.drawable.money_rmb_gray)));
            scaleanimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    top_img.clearAnimation();
                    top_img.setImageDrawable(getResources().getDrawable(R.drawable.money_0001));
                    setHeaderTopMargin(-mheaderViewHeight);
                    mHeaderState = PULL_TO_REFRESH;
                    if (null != view) {
                        view.setAlpha(1.0f);
                    }
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
        }
    }

    /**
     * header refreshing
     */
    private void headerRefreshing() {
        if (!mLock) {
            mHeaderState = REFRESHING;
            ViewGroup.LayoutParams layoutParams = top_img.getLayoutParams();
            layoutParams.width = pulldownview_w;
            layoutParams.height = pulldownview_h;
            top_img.setLayoutParams(layoutParams);
            pullDownProgressBar.setValues(100);
            pullDownProgressBar.setVisibility(View.GONE);
            pullDownProgressBar.setValues(0);
            setHeaderTopMargin(0);
            animation = getAniDrawable("money_000", 17);
            animation.setOneShot(false);//重复播放
            top_img.setImageDrawable(animation);
            animation.start();
            if (mOnHeaderRefreshListener != null) {
                mOnHeaderRefreshListener.onHeaderRefresh(this);
            }
        }
    }

    /**
     * 底部加载准备
     */
    private void footerPrepareToRefresh(int deltaY) {
        if (!lockdown) {
            img_footer.setImageDrawable(getResources().getDrawable(R.drawable.loading_0000));
            int newTopMargin = changingHeaderViewTopMargin(deltaY);
            changingFooterViewTopMargin(deltaY);
            // 如果header view topMargin 的绝对值大于或等于header + footer 的高度
            // 说明footer view 完全显示出来了，修改footer view 的提示状态
            if (Math.abs(newTopMargin) >= (mheaderViewHeight + mfooterViewHight)
                    && mFooterState != RELEASE_TO_REFRESH) {
                mFooterState = RELEASE_TO_REFRESH;
            } else if (Math.abs(newTopMargin) < (mheaderViewHeight + mfooterViewHight)) {
                mFooterState = PULL_TO_REFRESH;
            }
        }
    }

    /**
     * 底部刷新
     */
    public void footerRefreshing() {
        if (!lockdown && refresh != null) {
            mFooterState = REFRESHING;
            int top = mheaderViewHeight + mfooterViewHight;
            setFooterTopMargin(0);
            setHeaderTopMargin(-top);
            footeranimation = getAniDrawable("loading_000", 40);
            footeranimation.setOneShot(false);//重复播放
            img_footer.setImageDrawable(footeranimation);
            footeranimation.start();
            if (refresh != null) {
                refresh.onFooterRefresh(this);
            }
        }
    }

    /**
     * footer view 完成更新后恢复初始状态
     */
    public void onFooterRefreshComplete() {
        if (mHeaderState != REFRESHING) {
            if (footeranimation != null) {
                img_footer.clearAnimation();
                img_footer.setImageDrawable(getResources().getDrawable(R.drawable.loading_0000));
                tryRecycleAnimationDrawable(footeranimation);//逐帧回收
                footeranimation = null;//改变指向
            }
            setHeaderTopMargin(-mheaderViewHeight);
            setFooterTopMargin(mfooterViewHight);
            mFooterState = PULL_TO_REFRESH;
        }
    }

    public interface MarginTopValue {
        public void onCurrentMarginTop(int HeaderTopMargin);
    }

    public interface HeaderRefresh {
        public void onHeaderRefresh(PullDownRefreshView view);
    }

    public interface FooterRefresh {
        public void onFooterRefresh(PullDownRefreshView view);
    }

    public void setFooterRefresh(FooterRefresh refresh) {
        this.refresh = refresh;
    }

    public void setHeaderRefreshListener(HeaderRefresh inf) {
        this.mOnHeaderRefreshListener = inf;
    }

    public int setMarginTopValue(MarginTopValue arginTopValue) {
        this.marginTopValue = arginTopValue;
        return getHeaderTopMargin();
    }

    /**
     * 输入color的rgb值
     */
//    public void setHeaderViewBackgroudColor(int color){
//        relativeLayout.setBackgroundColor(color);
//    }

    public void setLockDown(boolean islock) {
        this.lockdown = islock;
    }

    private void tryRecycleAnimationDrawable(AnimationDrawable animationDrawable) {
        if (animationDrawable != null) {
            animationDrawable.stop();
            for (int i = 0; i < animationDrawable.getNumberOfFrames(); i++) {
                Drawable frame = animationDrawable.getFrame(i);
                if (frame instanceof BitmapDrawable) {
                    ((BitmapDrawable) frame).getBitmap().recycle();
                }
                frame.setCallback(null);
            }
            animationDrawable.setCallback(null);
        }
    }

    public AnimationDrawable getAniDrawable(String _before, int size) {
        AnimationDrawable animationDrawable = new AnimationDrawable();
        for (int i = 1; i <= size; i++) {
            int resourcesId = getResources().getIdentifier(_before + i, "drawable", context.getPackageName());
            Drawable mDrawable = new BitmapDrawable(((BitmapDrawable) getResources().getDrawable(resourcesId)).getBitmap().copy(Bitmap.Config.ARGB_8888, true));
            animationDrawable.addFrame(mDrawable, 40);
        }
        return animationDrawable;
    }
//    private int dip2px(float dpvalue){
//        float scale=context.getResources().getDisplayMetrics().density;
//        return (int)(dpvalue*scale+0.5f);
//    }

    private class ScrollListenner implements AbsListView.OnScrollListener {

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            ZpLog.getInstance().e(TAG, "Scrollchange");
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            ZpLog.getInstance().e(TAG, "Scroll");
            if (firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount > 0 && !lockdown) {
                ZpLog.getInstance().e(TAG, "BOTTOM");
                footerRefreshing();
            }
        }
    }


}
