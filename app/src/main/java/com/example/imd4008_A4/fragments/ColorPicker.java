package com.example.imd4008_A4.fragments;

import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Debug;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.example.imd4008_A4.MainActivity;
import com.example.imd4008_A4.R;

public class ColorPicker extends Fragment {
    private ColorPickerViewModel mViewModel;

    public static ColorPicker newInstance() {
        return new ColorPicker();
    }

    public ColorPicker() {
        super(R.layout.fragment_color_picker);
    }

    Button okBtn, cancelBtn;
    CardView colorView;
    SeekBar satSlider, lightSlider;
    ImageView hueImg;
    int colorVal;
    float hsvVal[] = new float[3];


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.d("START", "Created!");
        View root = inflater.inflate(R.layout.fragment_color_picker, container, false);
        okBtn = root.findViewById(R.id.okColorBtn);
        cancelBtn = root.findViewById(R.id.cancelColorBtn);
        colorView = root.findViewById(R.id.colorPreviewDisplay);
        satSlider = root.findViewById(R.id.satSlider);
        lightSlider = root.findViewById(R.id.lightSlider);
        hueImg = root.findViewById(R.id.hueImage);

        colorVal = ((MainActivity) getActivity()).getColor();
        Color.colorToHSV(colorVal, hsvVal);

        cancelBtn.setOnClickListener(view -> cancel());
        okBtn.setOnClickListener(view -> ok());
        satSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                hsvVal[1] = Float.valueOf(i) / 100;
                updateColor();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        lightSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                hsvVal[2] = Float.valueOf(i) / 100;
                updateColor();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        hueImg.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                getImageColor(view, event);
                return false;
            }
        });

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ColorPickerViewModel.class);
        // TODO: Use the ViewModel
    }

    void ok() {
        ((MainActivity) getActivity()).hideOverlay();
    }

    void cancel() {
        resetColor();
        ((MainActivity) getActivity()).hideOverlay();
    }

    void resetColor() {
        ((MainActivity) getActivity()).changeColor(colorVal);
        colorView.setCardBackgroundColor(((MainActivity) getActivity()).getColor());
    }

    void updateColor() {
        ((MainActivity) getActivity()).changeColor(Color.HSVToColor(hsvVal));
        colorView.setCardBackgroundColor(((MainActivity) getActivity()).getColor());
    }

    void getImageColor(View view, MotionEvent event) {
        Bitmap bitmap = ((BitmapDrawable) hueImg.getDrawable()).getBitmap();
        int x = (int) event.getX();
        int y = (int) event.getY();

        int projectedX = (int) ((double) x *
                ((double) bitmap.getWidth() / (double) hueImg.getWidth()));
        int projectedY = (int) ((double) y *
                ((double) bitmap.getHeight() / (double) hueImg.getHeight()));

        int pixel = bitmap.getPixel(projectedX, projectedY);

        int red = Color.red(pixel);
        int green = Color.green(pixel);
        int blue = Color.blue(pixel);
        Log.d("COLOR", String.valueOf(red+green+blue));
        float temp_hsvVal[] = new float[3];
        Color.colorToHSV(pixel, temp_hsvVal);
        hsvVal[0] = temp_hsvVal[0];
        updateColor();
    }
}