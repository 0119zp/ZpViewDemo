package zp.com.zpviewdemo.voice.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

import zp.com.zpbase.utils.ZpLog;

/**
 * Wave Animation View.
 * */
public class VoiceWaveView extends View {

    private static final String TAG = VoiceWaveView.class.getName();

    private int j = 0;
    public VoiceWaveView(Context context) {
        this(context, null);
    }

    public VoiceWaveView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VoiceWaveView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        handler.sendEmptyMessage(1);
    }

    @Override
    public void setVisibility(int visibility) {
        if (getVisibility() == visibility) {
            return;
        }
        super.setVisibility(visibility);
        if (visibility == View.VISIBLE) {
            handler.sendEmptyMessage(1);
        } else {
            handler.removeMessages(1);
        }
    }

    private int volume = 1;
    public void setVolume(int volume) {
        this.volume = volume / 30;
        if (this.volume < 1) {
            this.volume = 1;
        }
    }


    Handler handler =  new Handler(){
        public void handleMessage(Message msg){
            if (getVisibility() == View.GONE) {
                return;
            }

            switch (msg.what){
                case 1:
                    invalidate();
                    j++;
                    handler.sendEmptyMessageDelayed(1, 83);
                    break;
                default:
                    break;
            }
        }
    };


    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int height = getHeight();
        int width = getWidth();

        int minLineY = height / 2 - 15;
        ZpLog.getInstance().e(TAG, "height= " + height + "width= " + width + "minLineY= " + minLineY);

        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStrokeWidth(dp2px(2));
        for (int i = 0; i < 9; i++){
            int lineX = width / 2 + dp2px(30 * (i - 4));
            int lineY[] = {minLineY - 60 * this.volume,
                    minLineY - 45 * this.volume,
                    minLineY - 30 * this.volume,
                    minLineY - 15 * this.volume,
                    minLineY,
                    minLineY - 15 * this.volume,
                    minLineY - 30 * this.volume,
                    minLineY - 45 * this.volume};

//            for (int k = 0 ; k < lineY.length ; k++){
//                ZpLog.getInstance().e(TAG, "lineX =" + lineX + "k = " + k + " == " + lineY[k]);
//            }

            if (i == 0 || i == 1 || i == 5 || i == 6 || i == 4){
                canvas.drawLine(lineX, height - lineY[(i + j) % 8], lineX, lineY[(i + j) % 8], paint);
            } else {
                canvas.drawLine(lineX, height - lineY[(8 - i + j) % 8], lineX, lineY[(8 - i + j) % 8], paint);
            }
        }
    }

    private int dp2px(float dpValue){
        float scale = this.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
