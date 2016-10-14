package com.example.onion.charttest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Onion on 2016/10/11
 * for 希平方科技股份有限公司
 * you can contact me at : onionzhang@youtubelearn.com
 */

public class DataLine extends View {

    private List<PointF> mPointFs = new ArrayList<>();
    private int inte = 0;
    private Paint mPaint;

    private int padding = 48;

    public DataLine(Context context) {
        super(context);
        initialize();
    }

    public DataLine(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public DataLine(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

    private void initialize() {
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(10);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (inte < mPointFs.size()) {
            canvas.drawLine(
                    mPointFs.get(0).x,
                    mPointFs.get(0).y,
                    mPointFs.get(inte).x,
                    mPointFs.get(inte).y,
                    mPaint
            );
            inte++;

            if (inte < mPointFs.size()) {
                invalidate();
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    public void setPointFs(List<PointF> pointFs) {
        mPointFs = pointFs;
        inte = 0;
    }
}
