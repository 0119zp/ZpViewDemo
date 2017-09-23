package zp.com.zpviewdemo.voice.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

import zp.com.zpbase.utils.ZpLog;

/**
 * Circle Animation View.
 * */
public class VoiceIconView  extends View {

    private static final String TAG = VoiceIconView.class.getName();

    private float radiusUp;
    private int alphaUp;
    private float radiusDown;
    private int alphaDown;
    private int mark;
    private int timeStep;
    private float radiusUpStep;
    private float radiusDownStep;
    private int alphaStep;

    public VoiceIconView(Context context) {
        this(context, null);
    }

    public VoiceIconView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VoiceIconView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        init();
    }

    private void init(){
        timeStep = 1000 / 24;
        radiusUpStep = dp2px((275 - 80) / 12 / 2);
        radiusDownStep = dp2px((440 - 80) / 12 / 2);
        alphaStep = 255 / 12;
    }


    private static final int LARGEN = 0;
    private static final int LESSON = 2;
    public void setVisibility(int visibility, int style) {
        if (getVisibility() == visibility) {
            return;
        }
        super.setVisibility(visibility);
        if (visibility == View.VISIBLE) {
            switch (style){
                case LARGEN:
                    radiusUp = dp2px(40);
                    alphaUp = 255;
                    radiusDown = dp2px(40);
                    alphaDown = 255;
                    handler.sendEmptyMessage(LARGEN);
                    break;
                case LESSON:
                    radiusUp = dp2px(275 / 2);
                    alphaUp = 0;
                    radiusDown = dp2px(220);
                    alphaDown = 0;
                    handler.sendEmptyMessage(LESSON);
                    break;
                default:
                    break;
            }
        } else {
            handler.removeMessages(LARGEN);
            handler.removeMessages(LESSON);
        }
    }

    Handler handler = new Handler(){
        public void handleMessage(Message msg){
            if (getVisibility() == View.GONE) {
                return;
            }
            switch (msg.what){
                case LARGEN:
                    radiusUp += radiusUpStep;
                    alphaUp -= alphaStep;
                    if (alphaUp <= 255 - alphaStep * 12){
                        alphaUp = 0;
                        radiusUp = dp2px(275 / 2);
                    }
                    if (alphaUp == 255 - alphaStep * 6){
                        mark = 1;
                    }
                    if (alphaUp < 255 - alphaStep * 6){
                        radiusDown += radiusDownStep;
                        alphaDown -= 16;
                    }
                    if (alphaDown <= 255 - alphaStep * 12){
                        alphaDown = 0;
                        radiusDown = dp2px(220);
                    }

                    // 非完全透明的时候发送延迟信息
                    if (alphaDown != 0){
                        ZpLog.getInstance().e(TAG, " radiusUp=" + radiusUp + " radiusDown=" + radiusDown);
                        handler.sendEmptyMessageDelayed(0, timeStep);
                    }
                    break;
                case LESSON:
                    radiusUp -= radiusUpStep;
                    alphaUp += alphaStep;
                    if (alphaUp >= alphaStep * 12){
                        alphaUp = 255;
                        radiusUp = dp2px(40);
                    }
                    if (alphaUp == alphaStep * 6){
                        mark = 1;
                    }
                    if (alphaUp > alphaStep * 6){
                        radiusDown -= radiusDownStep;
                        alphaDown += alphaStep;
                    }
                    if (alphaDown >= alphaStep * 12){
                        alphaDown = 255;
                        radiusDown = dp2px(40);
                    }
                    handler.sendEmptyMessageDelayed(2, timeStep);
                    break;
                default:
                    break;
            }
            invalidate();
        }
    };

    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        Paint paintUp = new Paint();
        paintUp.setARGB(alphaUp, 219, 56, 76);
        paintUp.setAntiAlias(true);
        canvas.drawCircle(width / 2, height - dp2px(112), radiusUp, paintUp);
        if (mark == 1){
            Paint paintDown = new Paint();
            paintDown.setARGB(alphaDown, 219, 56, 76);
            paintDown.setAntiAlias(true);
            canvas.drawCircle(width / 2, height - dp2px(112), radiusDown, paintDown);
        }
    }

    private float dp2px(float dpValue){
        float scale = this.getResources().getDisplayMetrics().density;
        return dpValue * scale + 0.5f;
    }
}
