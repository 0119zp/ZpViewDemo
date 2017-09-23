package zp.com.zpviewdemo.load.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2017/8/1 0001.
 */

public class ProgressbarView extends View {

    private Paint paint;
    private float value = 0;
    private float Max = 100;
    private int roundWidth = 20;
    private int backgroundCircle = Color.RED;
    private int previousCirle = Color.BLUE;

    public ProgressbarView(Context context) {
        super(context);
        init();
    }

    public ProgressbarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ProgressbarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(backgroundCircle); //设置圆环的颜色
        paint.setStyle(Paint.Style.STROKE); //设置空心
        paint.setStrokeWidth(roundWidth); //设置圆环的宽度
        paint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int centre = getWidth() / 2; //获取圆心的x坐标
        int radius = (int) (centre - roundWidth / 2); //圆环的半径

        canvas.drawCircle(centre, centre, radius, paint);
        RectF rf = new RectF(centre - radius, centre - radius, centre
                + radius, centre + radius);
//        canvas.drawArc(rf, 0, (int)value/Max*360, false, paint);//true或者False是标志有没有边
        paint.setColor(previousCirle);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawArc(rf, 0, value / Max * 360, false, paint);

    }

    public void setValues(float i) {
        if (value <= Max) {
            value = i;
        }
        if (value > Max) {
            value = Max;
        }
        if (i <= 0) {
            value = 0;
        }
        postInvalidate();
    }

    public void setRoundWidth(int roundWidth) {
        this.roundWidth = roundWidth;
    }

    public void setMaxValues(float k) {
        Max = k;
    }

    public void setBackgroundCircle(int rgb) {
        this.backgroundCircle = rgb;
    }

    public void setPreviousCirle(int rgb) {
        this.previousCirle = rgb;
    }


}
