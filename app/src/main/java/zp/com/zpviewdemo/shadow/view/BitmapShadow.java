package zp.com.zpviewdemo.shadow.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Xfermode;
import android.util.AttributeSet;
import android.view.View;

import zp.com.zpviewdemo.R;

/**
 * Created by Administrator on 2017/4/10 0010.
 */

public class BitmapShadow extends View{

    private Bitmap bitmap;
    private Paint shadowPaint;
    private Paint mPaint;
    private Xfermode xfermode;
    private RectF shadowRectF;
    private Bitmap bitmapShadow;

    public BitmapShadow(Context context) {
        super(context);
        initView();
    }

    public BitmapShadow(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public BitmapShadow(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        // 得到bitmap对象
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.dongtu);
        shadowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        xfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        int bmpW = bitmap.getWidth();
        //我们设置图片的宽度为屏幕的 1/3
        float ratioW = w * 1.0f / bmpW * 2 / 3;

        bitmap = zoomImg(bitmap, (int) (bitmap.getWidth() * ratioW), (int) (bitmap.getHeight() * ratioW));

        Matrix matrix = new Matrix();
        matrix.setScale(1, -1);
        bitmapShadow = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

        LinearGradient mLgShader = new LinearGradient(0, bitmap.getHeight(), 0, bitmap.getHeight() * 2, new int[]{0x00000000,0x11000000,0xaa000000, Color.WHITE},new float[]{0,0.1f,0.4f,0.6f}, Shader.TileMode.REPEAT);
        shadowPaint.setShader(mLgShader);

        shadowRectF = new RectF(0, bitmap.getHeight(), bitmap.getWidth(), bitmap.getHeight() * 2);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.translate(getWidth() / 6, getHeight() / 10);

        canvas.drawBitmap(bitmap, 0, 0, null);


        int layerID = canvas.saveLayer(shadowRectF, null, Canvas.ALL_SAVE_FLAG);

        canvas.drawRect(shadowRectF, shadowPaint);
        mPaint.setXfermode(xfermode);
        canvas.drawBitmap(bitmapShadow, 0, bitmap.getHeight(), mPaint);
        mPaint.setXfermode(null);

        canvas.restoreToCount(layerID);

    }


    public  Bitmap zoomImg(Bitmap bm, int newWidth, int newHeight) {
        // 获得图片的宽高
        int width = bm.getWidth();
        int height = bm.getHeight();
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片
        return Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
    }


}
