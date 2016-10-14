package com.example.onion.charttest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Onion on 2016/10/13
 * for 希平方科技股份有限公司
 * you can contact me at : onionzhang@youtubelearn.com
 */

public class MyChartBackup extends View {

    private static final int ARROW_SIZE_DP = 10;

    private int mAxisPadding = 16;

    private List<PointF> mDataPoints;
    private Paint mPaint;
    private boolean animating;
    private Path mAnimationPath;

    private int mAnimationCounter;

    public MyChartBackup(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    private void initialize() {
        mDataPoints = new ArrayList<>();
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(10);
        animating = false;

        mAnimationPath = new Path();
        mAnimationCounter = 0;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mDataPoints.size() == 0) {
            return;
        }

        drawBackground(canvas);
        if (!animating) {
            drawLineChart(canvas);
        } else {
            Paint paint = new Paint();
            paint.setColor(Color.BLUE);
            paint.setStrokeWidth(10);
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawPath(mAnimationPath, paint);
        }
//        Paint paint = new Paint();
//        paint.setColor(Color.BLUE);
//        paint.setStrokeWidth(10);
//        paint.setStyle(Paint.Style.STROKE);

//        Path path = new Path();
//        path.moveTo(50, 50);
//        path.lineTo(50, 500);
//        path.lineTo(200, 500);
//        path.lineTo(200, 300);
//        path.lineTo(350, 300);
//
//        float[] intervals = new float[]{50.0f, 20.0f};
//        float phase = 0;
//
//        DashPathEffect dashPathEffect =
//                new DashPathEffect(intervals, phase);
//
//        paint.setPathEffect(dashPathEffect);
//        canvas.drawPath(path, paint);
    }

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            if (mAnimationCounter < mDataPoints.size()) {
                mAnimationPath.lineTo(mDataPoints.get(mAnimationCounter).x, getHeight() - mDataPoints.get(mAnimationCounter).y);
            }

            if (mAnimationCounter < mDataPoints.size()) {
                postDelayed(this, 20);
                animating = true;
            } else {
                animating = false;
            }
            invalidate();
            mAnimationCounter++;
        }
    };

    private void drawBackground(Canvas canvas) {
        // yAxis
        canvas.drawLine(
                mAxisPadding,
                getHeight() - mAxisPadding,
                mAxisPadding,
                mAxisPadding,
                mPaint
        );
        Path yAxisArrowPath = makeYAxisArrow(mAxisPadding, mAxisPadding);
        canvas.drawPath(yAxisArrowPath, mPaint);

        // xAxis
        canvas.drawLine(
                mAxisPadding,
                getHeight() - mAxisPadding,
                getWidth() - mAxisPadding,
                getHeight() - mAxisPadding,
                mPaint
        );
        Path xAxisArrowPath = makeXAxisArrow(getWidth() - mAxisPadding, getHeight() - mAxisPadding);
        canvas.drawPath(xAxisArrowPath, mPaint);
    }

    private Path makeXAxisArrow(float x, float y) {
        float arrowSizePx = ARROW_SIZE_DP * 3;
        Path path = new Path();
        path.moveTo(x, y + -arrowSizePx / 2);
        path.lineTo(x + arrowSizePx, y);
        path.lineTo(x, y + arrowSizePx / 2);
        path.lineTo(x, y + -arrowSizePx / 2);
        path.close();
        return path;
    }

    private Path makeYAxisArrow(float x, float y) {
        float arrowSizePx = ARROW_SIZE_DP * 3;
        Path path = new Path();
        path.moveTo(x + -arrowSizePx / 2, y);
        path.lineTo(x, y - arrowSizePx);
        path.lineTo(x + arrowSizePx / 2, y);
        path.lineTo(x + -arrowSizePx / 2, y);
        path.close();
        return path;
    }

    private void drawLineChart(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(10);
        paint.setStyle(Paint.Style.STROKE);
        Path path = new Path();
        path.moveTo(0, getHeight());
        for (int i = 0; i < mDataPoints.size(); i++) {
            path.lineTo(mDataPoints.get(i).x, getHeight() - mDataPoints.get(i).y);
        }
        canvas.drawPath(path, paint);
    }

    public List<PointF> getDataPoints() {
        return mDataPoints;
    }

    public void setDataPoints(List<PointF> dataPoints) {
        mDataPoints = dataPoints;
    }

    public void startAnimation() {
        removeCallbacks(mRunnable);
        mAnimationCounter = 0;
        post(mRunnable);
        mAnimationPath = new Path();
        mAnimationPath.moveTo(0, getHeight());
    }
}
