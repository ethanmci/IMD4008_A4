package com.example.imd4008_A4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.imd4008_A4.fragments.ColorPicker;

public class MainActivity extends AppCompatActivity {

    DrawingCanvas dc;
    CardView colorDisplay;
    int setColor = Color.BLUE;
    FragmentContainerView fcv;
    FragmentManager fm;
    Button editBtn, eraseBtn, undoBtn, deleteBtn;

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

        editBtn = findViewById(R.id.editBtn);
        undoBtn = findViewById(R.id.undoBtn);
        eraseBtn = findViewById(R.id.eraseBtn);
        deleteBtn = findViewById(R.id.delBtn);

        colorDisplay.setOnClickListener(view -> openColorChanger());

        undoBtn.setOnClickListener(view -> undo());
        deleteBtn.setOnClickListener(view -> erase());
        hideOverlay();
    }

    void openColorChanger() {
        dc.setPathColour(setColor);
        colorDisplay.setCardBackgroundColor(setColor);
        changeFragment(ColorPicker.class);
    }

    public void changeColor(int color) {
        setColor = color;
        dc.setPathColour(setColor);
        colorDisplay.setCardBackgroundColor(setColor);
    }

    public int getColor() {
        return setColor;
    }

    public void hideOverlay() {
        fcv.removeAllViewsInLayout();
    }

    public void changeFragment(@NonNull Class fragmentClass) {
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

    void erase() {
        dc.clearCanvas();
    }

    void undo() {
        dc.undo();
    }
}