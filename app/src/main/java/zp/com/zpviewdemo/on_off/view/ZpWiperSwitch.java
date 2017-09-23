package zp.com.zpviewdemo.on_off.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import zp.com.zpviewdemo.R;

/**
 * Created by Administrator on 2017/9/24 0024.
 */

public class ZpWiperSwitch extends View implements View.OnTouchListener {

    private Bitmap onBackground, offBackground;
    private Bitmap slipperOn, slipperOff;
    // 当前X坐标
    private float coordX;
    // 开关状态，open is true，close is false
    private boolean status = true;
    // 开关上一次点击时的状态，open is true，close is false
    private boolean laststatus = true;
    // 是否滑动过程中
    private boolean isSlipping = false;
    // 开关监听
    private OnSwitchChangedListener listener;

    public ZpWiperSwitch(Context context) {
        super(context);
        if (onBackground == null)
//            setOnBackground(R.drawable.sild_bg_on);
            setOnBackground(R.mipmap.ic_launcher);

        if (offBackground == null)
//            setOffBackground(R.drawable.sild_bg_off);
            setOffBackground(R.mipmap.ic_launcher);

        if (slipperOn == null)
//            setSlipperOnDrawable(R.drawable.sild_btn_on);
            setSlipperOnDrawable(R.mipmap.ic_launcher);

        if (slipperOff == null)
//            setSlipperOffDrawable(R.drawable.sild_btn_off);
            setSlipperOffDrawable(R.mipmap.ic_launcher);
        setOnTouchListener(this);
    }

    public ZpWiperSwitch(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ZpWiperSwitch(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.WiperSwitch);
//        setOnBackground(a.getResourceId(R.styleable.WiperSwitch_backgroundOn, R.drawable.sild_bg_on));
//        setOffBackground(a.getResourceId(R.styleable.WiperSwitch_backgroundOff, R.drawable.sild_bg_off));
//        setSlipperOnDrawable(a.getResourceId(R.styleable.WiperSwitch_drawableSlipperOn, R.drawable.sild_btn_on));
//        setSlipperOffDrawable(a.getResourceId(R.styleable.WiperSwitch_drawableSlipperOff, R.drawable.sild_btn_off));

        setOnBackground(a.getResourceId(R.styleable.WiperSwitch_backgroundOn, R.mipmap.ic_launcher));
        setOffBackground(a.getResourceId(R.styleable.WiperSwitch_backgroundOff, R.mipmap.ic_launcher));
        setSlipperOnDrawable(a.getResourceId(R.styleable.WiperSwitch_drawableSlipperOn, R.mipmap.ic_launcher));
        setSlipperOffDrawable(a.getResourceId(R.styleable.WiperSwitch_drawableSlipperOff, R.mipmap.ic_launcher));
        a.recycle();
        setOnTouchListener(this);
    }

    /**
     * 设置开关监听
     *
     * @param listener
     */
    public void setOnSwitchChangedListener(OnSwitchChangedListener listener) {
        this.listener = listener;
    }

    /**
     * 设置开关的状态
     *
     * @param isOn <p>
     *             open status is true <br>
     *             <p>
     *             close status is false <br>
     */
    public void setSwitchStatus(boolean isOn) {
        if (isOn)
            coordX = offBackground.getWidth();
        else
            coordX = 0;
        this.status = isOn;
        this.laststatus = isOn;
    }

    /**
     * 设置开状态的背景图片资源
     *
     * @param resId
     */
    public void setOnBackground(int resId) {
        onBackground = BitmapFactory.decodeResource(getResources(), resId);
    }

    /**
     * 设置关状态的背景图片资源
     *
     * @param resId
     */
    public void setOffBackground(int resId) {
        offBackground = BitmapFactory.decodeResource(getResources(), resId);
    }

    /**
     * 设置开状态下滑块的图片资源
     *
     * @param resId
     */
    public void setSlipperOnDrawable(int resId) {
        slipperOn = BitmapFactory.decodeResource(getResources(), resId);
    }

    /**
     * 设置关状态下滑块的图片资源
     *
     * @param resId
     */
    public void setSlipperOffDrawable(int resId) {
        slipperOff = BitmapFactory.decodeResource(getResources(), resId);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(offBackground.getWidth(), offBackground.getHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Matrix matrix = new Matrix();
        Paint paint = new Paint();
        float x = 0;

        // 根据当前x坐标位置，设置开或关状态
        if (coordX < (onBackground.getWidth()) / 2) {
            canvas.drawBitmap(offBackground, matrix, paint);
        } else {
            canvas.drawBitmap(onBackground, matrix, paint);
        }

        // 设置滑块的位置
        if (isSlipping) {
            // 滑动过程中，判断是否画出制定范围
            if (coordX >= onBackground.getWidth())
                x = onBackground.getWidth() - slipperOn.getWidth() / 2;
            else
                x = coordX - slipperOn.getWidth() / 2;
        } else {
            if (status)
                x = onBackground.getWidth() - slipperOn.getWidth();
            else
                x = 0;
        }

        // 对滑块滑动进行异常处理
        if (x < 0)
            x = 0;
        else if (x > onBackground.getWidth() - slipperOn.getWidth())
            x = onBackground.getWidth() - slipperOn.getWidth();

        // 绘制滑块
        if (status)
            canvas.drawBitmap(slipperOn, x, 0, paint);
        else
            canvas.drawBitmap(slipperOff, x, 0, paint);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (x > offBackground.getWidth() || y > offBackground.getHeight())
                    return false;
                else {
                    isSlipping = true;
                    coordX = x;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                getParent().requestDisallowInterceptTouchEvent(true);//屏蔽父控件拦截onTouch事件
                coordX = x;
                if (event.getX() >= (onBackground.getWidth() / 2)) {
                    status = true;
                } else {
                    status = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                isSlipping = false;
                if (event.getX() >= (onBackground.getWidth() / 2)) {
                    coordX = onBackground.getWidth() - slipperOn.getWidth();
                    status = true;
                } else {
                    coordX = 0;
                    status = false;
                }

                if (null != listener && laststatus != status) {
                    listener.onChanged(this, status);
                    //开关状态改变后记录上一次的状态
                    laststatus = status;
                }
                break;
        }
        // 刷新界面
        invalidate();
        return true;
    }

    /**
     * 状态改变回调接口
     *
     * @author EvaGu
     */
    public interface OnSwitchChangedListener {
        public void onChanged(ZpWiperSwitch view, boolean status);
    }
}

