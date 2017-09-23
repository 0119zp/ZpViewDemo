package zp.com.zpviewdemo.bitmap.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import zp.com.zpviewdemo.R;

/**
 * Created by Administrator on 2017/4/10 0010.
 */

public class ExBitmap extends View{

    private int mWidth;
    private int mHeight;
    private Shader bmpShader;
    private Paint mPaint;

    private RectF bmpRect;

    public ExBitmap(Context context) {
        super(context);
        init();
    }

    public ExBitmap(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ExBitmap(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        angles = new int[angleCount];

        for (int i = 0; i < angleCount; i++) {
            angles[i] = currentAngle + partOfAngle * i;

            float x = (float) (Math.sin(Math.toRadians(angles[i])) * startRadius);
            float y = (float) (Math.cos(Math.toRadians(angles[i])) * startRadius);
            pointFList.add(new PointF(x, y));
        }

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mWidth = w;
        mHeight = h;
        bmpRect = new RectF(0, 0, mWidth, mHeight);


        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.dlw);

        bmpShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.REPEAT);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setShader(bmpShader);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(3);
    }

    Path mPath = new Path();
    private int startRadius = 350; //五角星的半径
    private int angleCount = 30;    //五角星，5个角
    private int partOfAngle = 360 / angleCount;  //每个顶点的角度
    private int[] angles;
    private int currentAngle = 180;  //开始绘制的角度
    private List<PointF> pointFList = new ArrayList<>();


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        canvas.drawRect(bmpRect, mPaint);
//        canvas.drawCircle(mWidth / 2, mHeight / 2, mWidth / 2, mPaint);


        canvas.translate(mWidth / 2, mHeight / 2);


//        mPath.moveTo(pointFList.get(0).x, pointFList.get(0).y);
//        mPath.lineTo(pointFList.get(2).x, pointFList.get(2).y);
//        mPath.lineTo(pointFList.get(4).x, pointFList.get(4).y);
//
//        mPath.lineTo(pointFList.get(1).x, pointFList.get(1).y);
//        mPath.lineTo(pointFList.get(3).x, pointFList.get(3).y);

        mPath.moveTo(pointFList.get(0).x, pointFList.get(0).y);
        for (int i = 2; i < angleCount; i++) {
            if (i % 2 == 0) {// 除以二取余数，余数为0则为偶数,否则奇数
                mPath.lineTo(pointFList.get(i).x, pointFList.get(i).y);
                Log.e("QDX", "curPoint==" + i);
            }

        }

        if (angleCount % 2 == 0) {  //如果是偶数，moveTo
            mPath.moveTo(pointFList.get(1).x, pointFList.get(1).y);
        } else {                    //奇数，lineTo
            mPath.lineTo(pointFList.get(1).x, pointFList.get(1).y);
        }
        Log.e("QDX", "curPoint==1");


        for (int i = 3; i < angleCount; i++) {
            if (i % 2 != 0) {
                mPath.lineTo(pointFList.get(i).x, pointFList.get(i).y);
                Log.e("QDX", "curPoint==" + i);
            }
        }


        canvas.drawPath(mPath, mPaint);

    }

}
