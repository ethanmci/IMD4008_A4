package com.example.imd4008_A4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.example.imd4008_A4.fragments.ColorPicker;

public class MainActivity extends AppCompatActivity {

    DrawingCanvas dc;
    CardView colorDisplay;
    int setColor = Color.YELLOW;
    FragmentContainerView fcv;
    FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fcv = findViewById(R.id.fragmentContainerView);
        fm = getSupportFragmentManager();

        dc = super.findViewById(R.id.drawCanvas);
        dc.setPathColour(setColor);

        colorDisplay = findViewById(R.id.colorDisplay);
        colorDisplay.setCardBackgroundColor(setColor);

        colorDisplay.setOnClickListener(view -> openColorChanger());
    }

    void openColorChanger() {
        setColor = Color.MAGENTA;
        dc.setPathColour(setColor);
        colorDisplay.setCardBackgroundColor(setColor);
        changeFragment(ColorPicker.class);
    }

    void ChangeColor() {

    }

    public void changeFragment(Class fragmentClass) {
        Fragment fragment = null;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragmentContainerView, fragment, null);
        ft.commit();
    }
}