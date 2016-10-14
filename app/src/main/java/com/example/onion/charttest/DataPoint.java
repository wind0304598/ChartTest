package com.example.onion.charttest;

import android.graphics.PointF;

/**
 * Created by Onion on 2016/10/14
 * for 希平方科技股份有限公司
 * you can contact me at : onionzhang@youtubelearn.com
 */

public class DataPoint {

    private PointF mPosition;

    private PointF mCurrentPosition;

    private PointF mAnimatingPosition;

    private PointF mTargetPosition;

    private boolean inPosition;

    private int mCountdown;

    public DataPoint(PointF position) {
        mPosition = position;
    }

    public DataPoint(float x, float y) {
        mPosition = new PointF(x, y);
    }

    public PointF getPosition() {
        return mPosition;
    }

    public void setPosition(PointF position) {
        mPosition = position;
    }

    public PointF getAnimatingPosition() {
        return mAnimatingPosition;
    }

    public void setAnimatingPosition(PointF animatingPosition) {
        mAnimatingPosition = animatingPosition;
    }

    public PointF getTargetPosition() {
        return mTargetPosition;
    }

    public void setTargetPosition(PointF targetPosition) {
        mTargetPosition = targetPosition;
    }

    public boolean isInPosition() {
        return inPosition;
    }

    public void setInPosition(boolean inPosition) {
        this.inPosition = inPosition;
    }

    public int getCountdown() {
        return mCountdown;
    }

    public void setCountdown(int countdown) {
        mCountdown = countdown;
        if (countdown == 0) {
            inPosition = true;
        }
    }

    public PointF getCurrentPosition() {
        return mCurrentPosition;
    }

    public void setCurrentPosition(PointF currentPosition) {
        mCurrentPosition = currentPosition;
    }
}
