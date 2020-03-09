package com.example.mpdemo;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.to_line)
    TextView toLine;
    @BindView(R.id.to_bar)
    TextView toBar;
    @BindView(R.id.to_pie)
    TextView toPie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initListener();
    }

    private void initListener() {
        toLine.setOnClickListener(this);
        toBar.setOnClickListener(this);
        toPie.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.to_line:
                startActivity(new Intent(MainActivity.this, LineChartActivity.class));
                break;
            case R.id.to_bar:
                startActivity(new Intent(MainActivity.this, BarChartActivity.class));
                break;
            case R.id.to_pie:
                startActivity(new Intent(MainActivity.this, PieChartActivity.class));
                break;
        }
    }
}
