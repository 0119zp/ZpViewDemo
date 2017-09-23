package zp.com.zpviewdemo.rainbow.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2017/4/10 0010.
 */

public class LinearLayoutGradient extends View{

    private Paint paint;
    private Paint mPaint;
    private Rect rect;

    public LinearLayoutGradient(Context context) {
        super(context);
        initView();
    }

    public LinearLayoutGradient(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public LinearLayoutGradient(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        // 抗锯齿
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(2);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        Shader shader = new LinearGradient(200,200,200,300,new int[]{
                0xFFFF0000,     //红
                0xFFFF7F00,     //橙
                0xFFFFFF00,     //黄
                0xFF00FF00,     //绿
                0xFF00FFFF,     //青
                0xFF0000FF,     //蓝
                0xFF8B00FF      //紫
        },null, Shader.TileMode.REPEAT);  // position为null则均匀分配

        mPaint.setShader(shader);

        rect = new Rect(0,0,300,300);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawRect(rect,mPaint);

        // 画2条分割线，方便理解
        canvas.drawLine(0,100,300,100,paint);
        canvas.drawLine(0,200,300,200,paint);

    }
}
