package com.example.imd4008_A4.fragments;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.imd4008_A4.R;

public class ColorPicker extends Fragment {

    private ColorPickerViewModel mViewModel;

    public static ColorPicker newInstance() {
        return new ColorPicker();
    }

    public ColorPicker() {
        super(R.layout.fragment_color_picker);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_color_picker, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ColorPickerViewModel.class);
        // TODO: Use the ViewModel
    }

}