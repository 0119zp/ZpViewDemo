package zp.com.zpviewdemo.text.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/12/4 0004.
 */

public class FlowLayout extends ViewGroup {

    public static final int DEFAULT_SPACING = 20;
    private static final int SPACE_NO_NEED = -1;
    private static final int SPACE_NEED = -2;
    private final List<Line> mLines;
    boolean mNeedLayout;
    private int mHorizontalSpacing;
    private int mVerticalSpacing;
    private int mUsedWidth;
    private FlowLayout.Line mLine;
    private int mMaxLinesCount;
    private boolean lastFull;

    public FlowLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mHorizontalSpacing = 20;
        this.mVerticalSpacing = 20;
        this.mNeedLayout = true;
        this.mUsedWidth = 0;
        this.mLines = new ArrayList();
        this.mLine = null;
        this.mMaxLinesCount = 2147483647;
        this.lastFull = false;
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowLayout(Context context) {
        this(context, (AttributeSet) null);
    }

    public void setLastFull(boolean lastFull) {
        this.lastFull = lastFull;
        this.requestLayoutInner();
    }

    public void setHorizontalSpacing(int spacing) {
        if (this.mHorizontalSpacing != spacing) {
            this.mHorizontalSpacing = spacing;
            this.requestLayoutInner();
        }

    }

    public void setVerticalSpacing(int spacing) {
        if (this.mVerticalSpacing != spacing) {
            this.mVerticalSpacing = spacing;
            this.requestLayoutInner();
        }

    }

    public void setMaxLines(int count) {
        if (this.mMaxLinesCount != count) {
            this.mMaxLinesCount = count;
            this.requestLayoutInner();
        }

    }

    private void requestLayoutInner() {
        this.post(new Runnable() {

            public void run() {
                FlowLayout.this.requestLayout();
            }
        });
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec) - this.getPaddingRight() - this.getPaddingLeft();
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec) - this.getPaddingTop() - this.getPaddingBottom();
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);
        this.restoreLine();
        int count = this.getChildCount();

        int totalWidth;
        int linesCount;
        int i;
        for (totalWidth = 0; totalWidth < count; ++totalWidth) {
            View totalHeight = this.getChildAt(totalWidth);
            if (totalHeight.getVisibility() != 8) {
                linesCount = MeasureSpec.makeMeasureSpec(sizeWidth, modeWidth == 1073741824 ? -2147483648 : modeWidth);
                i = MeasureSpec.makeMeasureSpec(sizeHeight, modeHeight == 1073741824 ? -2147483648 : modeHeight);
                totalHeight.measure(linesCount, i);
                if (this.mLine == null) {
                    this.mLine = new FlowLayout.Line();
                }

                int childWidth = totalHeight.getMeasuredWidth();
                this.mUsedWidth += childWidth;
                if (this.mUsedWidth <= sizeWidth) {
                    this.mLine.addView(totalHeight);
                    this.mUsedWidth += this.mHorizontalSpacing;
                    if (this.mUsedWidth >= sizeWidth && !this.newLine()) {
                        break;
                    }
                } else if (this.mLine.getViewCount() == 0) {
                    this.mLine.addView(totalHeight);
                    if (!this.newLine()) {
                        break;
                    }
                } else {
                    if (!this.newLine()) {
                        break;
                    }

                    this.mLine.addView(totalHeight);
                    this.mUsedWidth += childWidth + this.mHorizontalSpacing;
                }
            }
        }

        if (this.mLine != null && this.mLine.getViewCount() > 0 && !this.mLines.contains(this.mLine)) {
            this.mLines.add(this.mLine);
        }

        totalWidth = MeasureSpec.getSize(widthMeasureSpec);
        int var13 = 0;
        linesCount = this.mLines.size();

        for (i = 0; i < linesCount; ++i) {
            var13 += ((FlowLayout.Line) this.mLines.get(i)).mHeight;
        }

        var13 += this.mVerticalSpacing * (linesCount - 1);
        var13 += this.getPaddingTop() + this.getPaddingBottom();
        this.setMeasuredDimension(totalWidth, resolveSize(var13, heightMeasureSpec));
    }

    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (!this.mNeedLayout || changed) {
            this.mNeedLayout = false;
            int left = this.getPaddingLeft();
            int top = this.getPaddingTop();
            int linesCount = this.mLines.size();

            for (int i = 0; i < linesCount; ++i) {
                FlowLayout.Line oneLine = (FlowLayout.Line) this.mLines.get(i);
                if (i < linesCount - 1) {
                    oneLine.layoutView(left, top);
                } else {
                    oneLine.layoutView(left, top, this.lastFull ? -2 : -1);
                }

                top += oneLine.mHeight + this.mVerticalSpacing;
            }
        }

    }

    private void restoreLine() {
        this.mLines.clear();
        this.mLine = new FlowLayout.Line();
        this.mUsedWidth = 0;
    }

    private boolean newLine() {
        this.mLines.add(this.mLine);
        if (this.mLines.size() < this.mMaxLinesCount) {
            this.mLine = new FlowLayout.Line();
            this.mUsedWidth = 0;
            return true;
        } else {
            return false;
        }
    }

    class Line {

        int mWidth = 0;
        int mHeight = 0;
        List<View> views = new ArrayList();

        Line() {
        }

        public void addView(View view) {
            this.views.add(view);
            this.mWidth += view.getMeasuredWidth();
            int childHeight = view.getMeasuredHeight();
            this.mHeight = this.mHeight < childHeight ? childHeight : this.mHeight;
        }

        public int getViewCount() {
            return this.views.size();
        }

        public void layoutView(int l, int t) {
            this.layoutView(l, t, -2);
        }

        public void layoutView(int l, int t, int flag) {
            int left = l;
            int top = t;
            int count = this.getViewCount();
            int layoutWidth = FlowLayout.this.getMeasuredWidth() - FlowLayout.this.getPaddingLeft() - FlowLayout.this.getPaddingRight();
            int surplusWidth = layoutWidth - this.mWidth - FlowLayout.this.mHorizontalSpacing * (count - 1);
            if (surplusWidth >= 0) {
                int view = flag == -1 ? 0 : (int) ((double) (surplusWidth / count) + 0.5D);

                for (int i = 0; i < count; ++i) {
                    View view1 = (View) this.views.get(i);
                    int childWidth = view1.getMeasuredWidth();
                    int childHeight = view1.getMeasuredHeight();
                    int topOffset = (int) ((double) (this.mHeight - childHeight) / 2.0D + 0.5D);
                    if (topOffset < 0) {
                        topOffset = 0;
                    }

                    childWidth += view;
                    view1.getLayoutParams().width = childWidth;
                    if (view > 0) {
                        int widthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidth, 1073741824);
                        int heightMeasureSpec = MeasureSpec.makeMeasureSpec(childHeight, 1073741824);
                        view1.measure(widthMeasureSpec, heightMeasureSpec);
                    }

                    view1.layout(left, top + topOffset, left + childWidth, top + topOffset + childHeight);
                    left += childWidth + FlowLayout.this.mHorizontalSpacing;
                }
            } else if (count == 1) {
                View var17 = (View) this.views.get(0);
                var17.layout(l, t, l + var17.getMeasuredWidth(), t + var17.getMeasuredHeight());
            }

        }
    }
}
