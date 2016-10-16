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

public class MyChart extends View {

    private static final int ARROW_SIZE_DP = 10;

    private int mAxisPadding = 16;

    private List<DataPoint> mDataPoints;
    private Paint mPaint;
    private boolean animating;
    private Path mAnimationPath;

    private int mAnimationCounter;

    private int mDuration;

    public MyChart(Context context, AttributeSet attrs) {
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
            if (mAnimationCounter >= mDataPoints.size()) {
                animating = false;
                invalidate();
                return;
            }

            float max = getMax(mDataPoints);
            DataPoint dataPoint = mDataPoints.get(mAnimationCounter);
            int countdown = dataPoint.getCountdown();
            int currentCount = dataPoint.getCurrentCount();
            float xDiff = currentCount * getXAnimatingPosition(mAnimationCounter);
            float xLineToPosition = getXPos(mAnimationCounter) + xDiff;
            float yDiff = currentCount * getYAnimatingPosition(mAnimationCounter, max);
            float yLineToPosition = getYPos(dataPoint.getPosition(), getMax(mDataPoints)) + yDiff;
            mAnimationPath.lineTo(xLineToPosition, yLineToPosition);
            currentCount++;
            dataPoint.setCurrentCount(currentCount);

            invalidate();
            if (currentCount == countdown) {
                mAnimationCounter++;
            }
            postDelayed(this, 20);
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
        float max = getMax(mDataPoints);

        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(10);
        paint.setStyle(Paint.Style.STROKE);

        Path path = new Path();
        path.moveTo(0, getHeight());
        for (int i = 0; i < mDataPoints.size(); i++) {
            DataPoint dataPoint = mDataPoints.get(i);
            path.lineTo(getXPos(i), getYPos(dataPoint.getPosition(), max));
        }
        canvas.drawPath(path, paint);
    }

    public void setDataPoints(List<DataPoint> dataPoints) {
        mDataPoints = dataPoints;
    }

    public void setDuration(int millisecond) {
        mDuration = millisecond;
    }

    public void startAnimation() {
        removeCallbacks(mRunnable);

        for (DataPoint dataPoint : mDataPoints) {
            dataPoint.setCountdown(mDuration / mDataPoints.size() / 20);
            dataPoint.setCurrentCount(0);
        }
        mAnimationCounter = 0;
        post(mRunnable);
        animating = true;
        mAnimationPath = new Path();
        mAnimationPath.moveTo(0, getHeight());
    }

    private float getMax(List<DataPoint> array) {
        if (array.size() == 0) {
            return 0;
        }

        float max = array.get(0).getPosition();
        for (int i = 1; i < array.size(); i++) {
            if (array.get(i).getPosition() > max) {
                max = array.get(i).getPosition();
            }
        }
        return max;
    }

    private float getYPos(float value, float maxValue) {
        float height = getHeight() - getPaddingTop() - getPaddingBottom();

        // scale it to the view size
        value = (value / maxValue) * height;

        // invert it so that higher values have lower y
        value = height - value;

        // offset it to adjust for padding
        value += getPaddingTop();

        return value;
    }

    private float getXPos(float value) {
        float width = getWidth() - getPaddingLeft() - getPaddingRight();
        float maxValue = mDataPoints.size() - 1;

        // scale it to the view size
        value = (value / maxValue) * width;

        // offset it to adjust for padding
        value += getPaddingLeft();

        return value;
    }

    private float getXAnimatingPosition(int index) {
        if (index > mDataPoints.size() - 1) {
            return 0;
        }

        int countdownPerDataPoint = mDuration / mDataPoints.size() / 20;
        if (countdownPerDataPoint > 0) {
            return (getXPos(index + 1) - getXPos(index)) / countdownPerDataPoint;
        }
        return 0;
    }

    private float getYAnimatingPosition(int index, float maxValue) {
        if (index >= mDataPoints.size() - 1) {
            return 0;
        }

        int countdownPerDataPoint = mDuration / mDataPoints.size() / 20;
        if (countdownPerDataPoint > 0) {
            DataPoint nextDataPoint = mDataPoints.get(index + 1);
            DataPoint currentDataPoint = mDataPoints.get(index);
            float nextPosition = getYPos(nextDataPoint.getPosition(), maxValue);
            float currentPosition = getYPos(currentDataPoint.getPosition(), maxValue);
            return (nextPosition - currentPosition) / countdownPerDataPoint;
        }
        return 0;
    }
}
