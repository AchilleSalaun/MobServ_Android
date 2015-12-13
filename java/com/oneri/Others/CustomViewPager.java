package com.oneri.Others;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by quentinleroy on 27/11/15.
 */

/***
 * Ajoute la possibilite de desactiver le "paging" (changer de page en scrollant
 * horizontalement depuis le bord de l'ecran)
 * Trouve ici : http://stackoverflow.com/questions/25935890/best-way-to-disable-viewpager-paging
 *
 * Finalement ne sert a rien puisque je laisse le paging active
 */

public class CustomViewPager extends ViewPager {

    private boolean isPagingEnabled = true;

    public CustomViewPager(Context context) {
        super(context);
    }

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return this.isPagingEnabled && super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return this.isPagingEnabled && super.onInterceptTouchEvent(event);
    }

    public void setPagingEnabled(boolean b) {
        this.isPagingEnabled = b;
    }
}
