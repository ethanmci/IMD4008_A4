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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import com.example.imd4008_A4.fragments.ColorPicker;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    DrawingCanvas dc;
    CardView colorDisplay;
    int setColor = Color.BLUE;
    FragmentContainerView fcv;
    FragmentManager fm;
    Button editBtn, eraseBtn, undoBtn, deleteBtn;
    Spinner drawingMode;

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

        drawingMode = findViewById(R.id.selectMode);

        drawingMode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                setMode(drawingMode.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        colorDisplay.setOnClickListener(view -> openColorChanger());

        editBtn.setOnClickListener(view -> dc.toggleErase(false));
        eraseBtn.setOnClickListener(view -> dc.toggleErase(true));
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

    void setMode(String mode) {
        int newMode = 0;
        switch (mode) {
            case "Lines":
                newMode = dc.DRAWING;
                break;
            case "Polyline":
                newMode = dc.POLYLINE;
                break;
            case "Rectangle":
                newMode = dc.RECT;
                break;
            case "Circle":
                newMode = dc.CIRCLE;
                break;
        }
        dc.setMode(newMode);
    }

    void erase() {
        dc.clearCanvas();
    }

    void undo() {
        dc.undo();
    }
}