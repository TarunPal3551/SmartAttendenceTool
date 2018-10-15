package com.example.android.testin3;

import android.graphics.Outline;
import android.view.View;
import android.view.ViewOutlineProvider;

public class CustomOutlineProvider_for_ustom_dialog_box extends ViewOutlineProvider {

    int roundCorner;

    public CustomOutlineProvider_for_ustom_dialog_box(int round) {
        roundCorner = round;
    }

    @Override
    public void getOutline(View view, Outline outline) {
        outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), roundCorner);
    }
}