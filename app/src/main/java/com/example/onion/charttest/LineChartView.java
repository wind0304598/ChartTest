package com.example.onion.charttest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.ViewGroup;

/**
 * Created by Onion on 2016/10/12
 * for 希平方科技股份有限公司
 * you can contact me at : onionzhang@youtubelearn.com
 */

public class LineChartView extends ViewGroup {

    private static final int ARROW_SIZE_DP = 10;

    private int mAxisPadding = 16;
    private Paint mPaint;
    private Path mArrowPath;

    private DataLine mDataLine;

    public LineChartView(Context context) {
        super(context);
        initialize();
    }

    public LineChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public LineChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

    private void initialize() {
        mArrowPath = new Path();
        mPaint = new Paint();
        mPaint.setColor(Color.GRAY);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(5);

        setWillNotDraw(false);

        mDataLine = new DataLine(getContext());
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
        addView(mDataLine, layoutParams);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mDataLine.layout(0, 0, getMeasuredWidth(), getMeasuredHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // yAxis
        canvas.drawLine(
                mAxisPadding,
                getHeight() - mAxisPadding,
                mAxisPadding,
                mAxisPadding,
                mPaint
        );
        makeYAxisArrow(mAxisPadding, mAxisPadding);
        canvas.drawPath(mArrowPath, mPaint);

        // xAxis
        canvas.drawLine(
                mAxisPadding,
                getHeight() - mAxisPadding,
                getWidth() - mAxisPadding,
                getHeight() - mAxisPadding,
                mPaint
        );
        makeXAxisArrow(getWidth() - mAxisPadding, getHeight() - mAxisPadding);
        canvas.drawPath(mArrowPath, mPaint);
    }

    private void makeXAxisArrow(float x, float y) {
        float arrowSizePx = ARROW_SIZE_DP * 3;
        mArrowPath.moveTo(x, y + -arrowSizePx / 2);
        mArrowPath.lineTo(x + arrowSizePx, y);
        mArrowPath.lineTo(x, y + arrowSizePx / 2);
        mArrowPath.lineTo(x, y + -arrowSizePx / 2);
        mArrowPath.close();
    }

    private void makeYAxisArrow(float x, float y) {
        float arrowSizePx = ARROW_SIZE_DP * 3;
        mArrowPath.moveTo(x + -arrowSizePx / 2, y);
        mArrowPath.lineTo(x, y - arrowSizePx);
        mArrowPath.lineTo(x + arrowSizePx / 2, y);
        mArrowPath.lineTo(x + -arrowSizePx / 2, y);
        mArrowPath.close();
    }

    public void startLineAnimation() {
        mDataLine.requestLayout();
        mDataLine.invalidate();
    }

    public DataLine getDataLine() {
        return mDataLine;
    }
}
