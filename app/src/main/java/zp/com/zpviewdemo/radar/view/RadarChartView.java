package zp.com.zpviewdemo.radar.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/23 0023.
 */

public class RadarChartView extends View{


    private int count = 4;//顶点个数
    private LinkedList<Integer> radius = new LinkedList<Integer>();//半径List
    private int maxRadius;//最大半径
    private LinkedList<Integer> marks = new LinkedList<Integer>();//每个顶点的数值List
    private LinkedList<String> keys = new LinkedList<String>();//每个顶点的文字List
    LinkedHashMap<String, Integer> map = new LinkedHashMap<>();//顶点数值和文字
    private Paint paintLine;//直线画笔
    private Paint paintPeripheryLine;//外围直线画笔
    private Paint paintMarkLine;
    private Paint paintMarkPoint;
    private Paint paintText;//文字画笔
    private double angle;//  angle=360/count
    private double x;
    private double y;
    private double x1;
    private double y1;
    private double lastX;
    private double lastY;
    private int centerX;//中心坐标X
    private int centerY;//中心坐标Y
    private Path path = new Path();

    public RadarChartView(Context context) {
        super(context);
    }

    public RadarChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RadarChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        maxRadius = (int) (Math.min(h, w) / 2 * 0.8f);//最大半径*0.8是为了保证文字部分显示
        init();
        centerX = w / 2;
        centerY = h / 2;
        postInvalidate();
        super.onSizeChanged(w, h, oldw, oldh);
    }

    /**
     * 初始化半径、最大半径
     */
    public void init() {
        for (int i = 0; i < count; ++i) {
            radius.add(maxRadius / count * (i + 1));
        }
        maxRadius = radius.get(radius.size() - 1);
        angle = 360 / getCount();
        Iterator iterator = getMap().entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            String key = (String) entry.getKey();
            Integer value = (Integer) entry.getValue();
            marks.add(value);
            keys.add(key);
        }
        paintLine = new Paint();
        paintLine.setColor(Color.parseColor("#EEF3FF"));
        paintLine.setStyle(Paint.Style.FILL);
        paintLine.setStrokeWidth(2);

        paintPeripheryLine = new Paint();
        paintPeripheryLine.setColor(Color.parseColor("#50E3C2"));
        paintPeripheryLine.setStyle(Paint.Style.FILL);
        paintPeripheryLine.setStrokeWidth(4);

        paintText = new Paint();
        paintText.setColor(Color.parseColor("#485465"));
        paintText.setAntiAlias(true);
        paintText.setStyle(Paint.Style.FILL_AND_STROKE );
        paintText.setTextSize(36);

        paintMarkPoint = new Paint();
        paintMarkPoint.setColor(Color.parseColor("#FAB84A"));
        paintText.setAntiAlias(true);
        paintMarkPoint.setStyle(Paint.Style.STROKE);
        paintMarkPoint.setStrokeWidth(5);

        paintMarkLine = new Paint();
        //渐变色
        LinearGradient shader = new LinearGradient(0, 0, 700, 700, new int[]{Color.parseColor("#3D75D7"), Color.parseColor("#90E3E1"), Color.parseColor("#263E9B")},
                new float[]{0, 0.5f, 1.0f}, Shader.TileMode.MIRROR);
        paintMarkLine.setShader(shader);
        paintMarkLine.setAntiAlias(true);
        paintMarkLine.setStyle(Paint.Style.FILL_AND_STROKE);
        paintMarkLine.setAlpha((int) (255 * 72 / 100));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < radius.size(); i++) {
            drawStroke(canvas, angle, radius.get(i));
        }
        path.reset();
        double firstX = 0;//第一个点的X值
        double firstY = 0;//第一个点的Y值
        for (int i = 0; i < marks.size(); i++) {
            x1 = getPointX(angle * i, radius.get(radius.size() - 1));
            y1 = getPointY(angle * i, radius.get(radius.size() - 1));
            x = getPointX(angle * i, marks.get(i));
            y = getPointY(angle * i, marks.get(i));
            canvas.drawCircle((float) x1, (float) y1, 10, paintMarkPoint);
            if (i == 0) {
                path.moveTo((float) x, (float) y);
                firstX = x;
                firstY = y;
            } else {
                path.lineTo((float) x, (float) y);
                canvas.drawLine((float) lastX, (float) lastY, (float) x, (float) y, paintPeripheryLine);
                if (i == marks.size() - 1) {
                    canvas.drawLine((float) x, (float) y, (float) firstX, (float) firstY, paintPeripheryLine);
                }
            }
            lastX = x;
            lastY = y;
        }
        canvas.drawPath(path, paintMarkLine);
    }

    /**
     * 绘制多边形，radius为其外接圆半径
     *
     * @param canvas
     * @param littleAngle
     * @param radius
     */
    private void drawStroke(Canvas canvas, double littleAngle, double radius) {
        for (int i = 0; i < count; i++) {
//            Paint paint = new Paint();
//            paint.setColor(Color.BLACK);
//            paint.setStyle(Paint.Style.FILL);
            int xx = getXX(littleAngle * i);
            x = getPointX(littleAngle * i, radius);
            y = getPointY(littleAngle * i, radius);
//            canvas.drawPoint((float) x, (float) y, paint);
            canvas.drawLine((float) centerX, (float) centerY, (float) x, (float) y, paintLine);
            if (i > 0) {
                canvas.drawLine((float) lastX, (float) lastY, (float) x, (float) y, paintLine);
            }
            if (i == (count - 1)) {
                canvas.drawLine((float) x, (float) y, (float) getPointX(0, radius), (float) getPointY(0, radius), paintLine);
            }
            lastX = x;
            lastY = y;
            //判断是否是最外层的圆，如果是添加对应的文字
            if (radius == this.radius.get(this.radius.size() - 1)) {
                Paint.FontMetrics fontMetrics = paintText.getFontMetrics();
                float fontHeight = fontMetrics.descent - fontMetrics.ascent;

                float textWidth = paintText.measureText(keys.get(i));
                if (xx == 1) {
                    canvas.drawText(keys.get(i), (float) (lastX + fontHeight / 2), (float) (lastY - fontHeight / 2), paintText);
                } else if (xx == 2) {
                    canvas.drawText(keys.get(i), (float) (lastX + fontHeight / 2), (float) (lastY + fontHeight), paintText);
                } else if (xx == 3) {
                    canvas.drawText(keys.get(i), (float) (lastX - textWidth - fontHeight / 2), (float) (lastY + fontHeight), paintText);
                } else if (xx == 4) {
                    canvas.drawText(keys.get(i), (float) (lastX - textWidth - fontHeight / 2), (float) (lastY - fontHeight / 2), paintText);
                } else if (xx == 5) {
                    canvas.drawText(keys.get(i), (float) (lastX - textWidth / 2), (float) (lastY - fontHeight), paintText);
                } else if (xx == 7) {
                    canvas.drawText(keys.get(i), (float) (lastX - textWidth / 2), (float) (lastY + fontHeight + 10), paintText);
                } else if (xx == 6) {
                    canvas.drawText(keys.get(i), (float) (lastX + fontHeight / 2), (float) (lastY + fontHeight / 4), paintText);
                } else if (xx == 8) {
                    canvas.drawText(keys.get(i), (float) (lastX - textWidth - fontHeight / 2), (float) (lastY + fontHeight / 4), paintText);
                }
            }
        }
    }

    /**
     * 获取角度对应的象限
     * X轴正方向为5，X轴负方向7，Y轴正方向6，Y轴负方向8
     *
     * @param angle
     * @return
     */
    public int getXX(double angle) {
        if (angle == 0 || angle == 360) {
            return 5;
        } else if (angle > 0 && angle < 90) {
            return 1;
        } else if (angle == 90) {
            return 6;
        } else if (angle > 90 && angle < 180) {
            return 2;
        } else if (angle == 180) {
            return 7;
        } else if (angle > 180 && angle < 270) {
            return 3;
        } else if (angle == 270) {
            return 8;
        } else if (angle > 270 && angle < 360) {
            return 4;
        } else {
            return -1;
        }

    }


    /**
     * 得到需要计算的角度
     *
     * @param angle
     * @return res
     */
    private double getNewAngle(double angle) {
        double res = angle;
        if (angle >= 0 && angle <= 90) {
            res = 90 - angle;
        } else if (angle > 90 && angle <= 180) {
            res = angle - 90;
        } else if (angle > 180 && angle <= 270) {
            res = 270 - angle;
        } else if (angle > 270 && angle <= 360) {
            res = angle - 270;
        }
        return res;
    }

    /**
     * 若以圆心为原点，返回该角度顶点的所在象限
     *
     * @param angle
     * @return
     */
    private int getQr(double angle) {
        int res = 0;
        if (angle >= 0 && angle <= 90) {
            res = 1;
        } else if (angle > 90 && angle <= 180) {
            res = 2;
        } else if (angle > 180 && angle <= 270) {
            res = 3;
        } else if (angle > 270 && angle <= 360) {
            res = 4;
        }
        return res;
    }

    /**
     * 返回多边形顶点X坐标
     *
     * @param angle
     * @return
     */
    private double getPointX(double angle, double radius) {
        double newAngle = getNewAngle(angle);
        double res = 0;
        double width = radius * Math.cos(newAngle / 180 * Math.PI);
        int qr = getQr(angle);
        switch (qr) {
            case 1:
            case 2:
                res = centerX + width;
                break;
            case 3:
            case 4:
                res = centerX - width;
                break;
            default:
                break;
        }
        return res;
    }


    /**
     * 返回多边形顶点Y坐标
     */
    private double getPointY(double angle, double radius) {
        double newAngle = getNewAngle(angle);
        double height = radius * Math.sin(newAngle / 180 * Math.PI);
        double res = 0;
        int qr = getQr(angle);
        switch (qr) {
            case 1:
            case 4:
                res = centerY - height;
                break;
            case 2:
            case 3:
                res = centerY + height;
                break;
            default:
                break;
        }
        return res;
    }

    public void setMap(LinkedHashMap<String, Integer> map) {
        this.map = map;
    }

    public LinkedHashMap<String, Integer> getMap() {
        return map;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

}
