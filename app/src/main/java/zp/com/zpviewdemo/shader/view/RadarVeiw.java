package zp.com.zpviewdemo.shader.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import zp.com.zpbase.utils.ZpLog;
import zp.com.zpviewdemo.R;

/**
 * 雷达自定义view
 */

public class RadarVeiw extends View{

    private static final String TAG = RadarVeiw.class.getName();

    private static final int MSG_WHAT = 10086;
    private static final int DELAY_TIME = 100;

    // 设置默认宽高，雷达一般都是圆形，所以我们下面取宽高会去Math.min(宽,高)
    private final int DEFAULT_WIDTH = 200;
    private final int DEFAULT_HEIGHT = 200;

    // 雷达画笔
    private Paint mRadarPaint;
    // 雷达线条的颜色，默认为白色
    private int mRadarColor = Color.WHITE;
    // 雷达圆圈的个数，默认4个
    private int circleCount = 4;
    // 雷达半径
    private int mRadarRadius;
    // 旋转的角度
    private int rotateAngel = 0;

    // 雷达底色画笔
    private Paint mBgPaint;
    // 雷达圆圈背景色
    private int mBgColor = Color.BLACK;

    // 雷达扫描时候的起始和终止颜色
    private int startColor = 0x0000ff00;
    private int endColor = 0xaa00ff00;

    private Shader radarShader;
    // 矩阵
    private Matrix matrix;


    public RadarVeiw(Context context) {
        super(context);

    }

    public RadarVeiw(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 初始化自定义属性
        initAttrs(context, attrs);

        initPaint();
    }

    public RadarVeiw(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // 初始化自定义属性
        initAttrs(context, attrs);

        initPaint();
    }

    public void initPaint(){
        // 设置抗锯齿
        mRadarPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        // 画笔颜色
        mRadarPaint.setColor(mRadarColor);
        // 设置空心的画笔，只画圆边
        mRadarPaint.setStyle(Paint.Style.STROKE);
        // 画笔宽度
        mRadarPaint.setStrokeWidth(2);

        mBgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBgPaint.setColor(mBgColor);
        mBgPaint.setStyle(Paint.Style.FILL);

        radarShader = new SweepGradient(0,0,startColor,endColor);

        matrix = new Matrix();
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        if (attrs != null){
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RadarView);
            startColor = typedArray.getColor(R.styleable.RadarView_startColor,startColor);
            endColor = typedArray.getColor(R.styleable.RadarView_endColor,endColor);
            mRadarColor = typedArray.getColor(R.styleable.RadarView_raderLineColor,mRadarColor);
            mBgColor = typedArray.getColor(R.styleable.RadarView_backgColor,mBgColor);
            circleCount = typedArray.getInteger(R.styleable.RadarView_circleCount,circleCount);

            typedArray.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = measureSize(1, DEFAULT_WIDTH, widthMeasureSpec);
        int hight = measureSize(0, DEFAULT_HEIGHT, widthMeasureSpec);

        int measureSize = Math.max(width, hight);

        setMeasuredDimension(measureSize,measureSize);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mRadarRadius = Math.max(w/2, h/2);
        ZpLog.getInstance().e(TAG,"======= onSizeChanged ==========");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        ZpLog.getInstance().e(TAG,"========= onDraw =========== " + rotateAngel);

        // 将画板移动到屏幕的中心点
        canvas.translate(mRadarRadius , mRadarRadius);
        mBgPaint.setShader(null);
        // 绘制底色(默认为黑色)，可以使雷达的线看起来更清晰
        canvas.drawCircle(0,0,mRadarRadius,mBgPaint);
        // 根据用户设定的圆个数进行绘制
        for (int i = 1; i <= circleCount; i++) {
            // 画圆圈
            canvas.drawCircle(0, 0, (float) (i * 1.0 / circleCount * mRadarRadius), mRadarPaint);
        }
        // 绘制雷达基线 x轴
        canvas.drawLine(-mRadarRadius, 0 , mRadarRadius, 0 ,mRadarPaint);
        // 绘制雷达基线 y轴
        canvas.drawLine(0, mRadarRadius, 0, -mRadarRadius, mRadarPaint);

        //设置颜色渐变从透明到不透明
        mBgPaint.setShader(radarShader);
        canvas.concat(matrix);
        canvas.drawCircle(0, 0, mRadarRadius, mBgPaint);

    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            rotateAngel += 3;
            postInvalidate();

            matrix.reset();
            matrix.preRotate(rotateAngel, 0, 0);
            mHandler.sendEmptyMessageDelayed(MSG_WHAT, DELAY_TIME);
        }
    };


    public void startScan() {
        mHandler.removeMessages(MSG_WHAT);
        mHandler.sendEmptyMessage(MSG_WHAT);
    }

    public void stopScan() {
        mHandler.removeMessages(MSG_WHAT);
    }


    /**
     * 测量宽高
     * @param specType 1-宽，0-高
     * @param contentSize 默认值
     * @param measureSpec
     * @return
     */
    public int measureSize(int specType, int contentSize, int measureSpec){
        int result = 0;

        // 获取测量的模式和Size
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY){
            result = Math.max(contentSize, specSize);
        }else {
            result = contentSize;
            if (specType == 1){
                // 根据传入方式计算结果
                result += (getPaddingLeft() + getPaddingRight());
            }else {
                // 根据传入方式计算结果
                result += (getPaddingTop() + getPaddingBottom());
            }
        }

        return result;
    }

}
