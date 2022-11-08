package com.example.imd4008_tutorial6;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    DrawingCanvas dc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dc = super.findViewById(R.id.drawCanvas);
        dc.setPathColour(Color.YELLOW);
    }
}