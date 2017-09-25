package com.huangbryant.hindicator;

import android.graphics.drawable.Drawable;


public abstract class BaseDrawable extends Drawable {

    public final int arrowDirection;

    public BaseDrawable(@HIndicatorBuilder.ARROWDIRECTION int arrowDirection) {
        this.arrowDirection = arrowDirection;
    }
}
