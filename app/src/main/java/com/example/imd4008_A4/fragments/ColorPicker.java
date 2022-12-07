package com.example.imd4008_A4.fragments;

import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
    int colorVal;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_color_picker, container, false);
        okBtn = root.findViewById(R.id.okColorBtn);
        cancelBtn = root.findViewById(R.id.cancelColorBtn);
        colorView = root.findViewById(R.id.colorPreviewDisplay);
        satSlider = root.findViewById(R.id.satSlider);
        lightSlider = root.findViewById(R.id.lightSlider);

        colorVal = ((MainActivity) getActivity()).getColor();

        cancelBtn.setOnClickListener(view -> cancel());
        okBtn.setOnClickListener(view -> ok());
        satSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

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

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

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
        //((MainActivity) getActivity()).changeFragment(null);
    }

    void cancel() {
        ((MainActivity) getActivity()).hideOverlay();
    }

    void updateColor(int color) {
        ((MainActivity) getActivity()).changeColor(color);
        colorView.setCardBackgroundColor(((MainActivity) getActivity()).getColor());
    }

}