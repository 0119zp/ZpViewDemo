package zp.com.zpviewdemo.load.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2017/7/21 0021.
 */

public class ZpLoadView extends View {

    private Paint mPaintIn,mPaintblack;
    private Paint mPaintOut,mPaintwhit;
    private int a;
    private int mCurPercent,mCurPercent180,mCurPercent120;
    private Handler handler = new Handler();
    private RectF rectf,rectfl;


    public ZpLoadView(Context context) {
        super(context);
        init();
    }

    public ZpLoadView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ZpLoadView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaintIn = new Paint();
        mPaintIn.setAntiAlias(true);
        mPaintIn.setDither(true);
        mPaintIn.setStyle(Paint.Style.FILL);
        mPaintIn.setColor(Color.parseColor("#660000ff"));
//        mPaintIn.setShadowLayer();

        mPaintOut = new Paint();
        // 抗锯齿
        mPaintOut.setAntiAlias(true);
        // 防抖动
        mPaintOut.setDither(true);
        mPaintOut.setStyle(Paint.Style.FILL);

        mPaintOut.setColor(Color.parseColor("#6600ff00"));
        mPaintblack = new Paint();
        mPaintblack.setAntiAlias(true);
        mPaintblack.setDither(true);
        mPaintblack.setStyle(Paint.Style.FILL);

        mPaintblack.setColor(Color.parseColor("#66ff0000"));

        mPaintwhit = new Paint();
        mPaintwhit.setAntiAlias(true);
        mPaintwhit.setDither(true);
        mPaintwhit.setStyle(Paint.Style.FILL);

        mPaintwhit.setColor(Color.parseColor("#ffffff"));

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                setCurPercent();
                setCurPercent180();
                setCurPercent120();
            }
        },1000);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        rectf = new RectF(0,0,getMeasuredWidth(), getMeasuredHeight());
        rectfl = new RectF(20,20,getMeasuredWidth()-20, getMeasuredHeight()-20);
        canvas.drawArc(rectf,0,mCurPercent,true,mPaintIn);
        canvas.drawArc(rectf,60,mCurPercent180,true,mPaintOut);
        canvas.drawArc(rectf,240,mCurPercent120,true,mPaintblack);

        canvas.drawArc(rectfl,0,360,true,mPaintwhit);
    }

    //内部设置百分比 用于动画效果
    private void setCurPercent() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i =0;i<=60;i++){
                    try {
                        Thread.sleep(15);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    mCurPercent = i;
                   ZpLoadView.this.postInvalidate();
                }
            }

        }).start();
    }

    //内部设置百分比 用于动画效果
    private void setCurPercent180() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i =0;i<=180;i++){
                    try {
                        Thread.sleep(15);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    mCurPercent180 = i;
                    ZpLoadView.this.postInvalidate();
                }
            }

        }).start();
    }
    //内部设置百分比 用于动画效果
    private void setCurPercent120() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i =0;i<=120;i++){
                    try {
                        Thread.sleep(15);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    mCurPercent120 = i;
                    ZpLoadView.this.postInvalidate();
                }
            }

        }).start();
    }




}
