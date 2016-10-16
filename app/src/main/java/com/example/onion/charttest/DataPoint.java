package com.example.onion.charttest;

import android.graphics.PointF;

/**
 * Created by Onion on 2016/10/14
 * for 希平方科技股份有限公司
 * you can contact me at : onionzhang@youtubelearn.com
 */

public class DataPoint {

    private float mPosition;

    private int mCountdown;

    private int mCurrentCount;

    public DataPoint(float position) {
        mPosition = position;
    }

    public float getPosition() {
        return mPosition;
    }

    public void setPosition(float position) {
        mPosition = position;
    }

    public int getCountdown() {
        return mCountdown;
    }

    public void setCountdown(int countdown) {
        mCountdown = countdown;
    }

    public int getCurrentCount() {
        return mCurrentCount;
    }

    public void setCurrentCount(int currentCount) {
        mCurrentCount = currentCount;
    }
}
