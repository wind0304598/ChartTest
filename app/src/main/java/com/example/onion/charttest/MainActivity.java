package com.example.onion.charttest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.chart) MyChart mChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mChart.setDataPoints(getData());
        mChart.setDuration(3000);
    }

    private List<DataPoint> getData() {
        List<DataPoint> dataPoints = new ArrayList<>();
        dataPoints.add(new DataPoint(10, 100));
        dataPoints.add(new DataPoint(40, 150));
        dataPoints.add(new DataPoint(70, 400));
        dataPoints.add(new DataPoint(100, 420));
        dataPoints.add(new DataPoint(130, 500));
        dataPoints.add(new DataPoint(160, 1000));
        return dataPoints;
    }

    @OnClick(R.id.gogoOnion)
    void onWalkingButtonClicked() {
        mChart.startAnimation();
    }
}
