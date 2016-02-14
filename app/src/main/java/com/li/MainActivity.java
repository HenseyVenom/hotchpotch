package com.li;

import android.graphics.PointF;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.li.app.R;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton fab;
    ParabolaView         ball;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        ball = (ParabolaView) findViewById(R.id.parabola_ball);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int    speed    = 1500;
                PointF endPoint = new PointF(fab.getX(), fab.getY());
                ball.startParabola(speed,endPoint);
                Log.e(getClass().getSimpleName(), "click");
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
