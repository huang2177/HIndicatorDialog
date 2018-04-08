package com.huangbryant.hindicator.drawable;

import android.graphics.drawable.Drawable;

import com.huangbryant.hindicator.HIndicatorBuilder;


public abstract class BaseDrawable extends Drawable {

    public final int arrowDirection;

    public BaseDrawable(@HIndicatorBuilder.ARROWDIRECTION int arrowDirection) {
        this.arrowDirection = arrowDirection;
    }
}
