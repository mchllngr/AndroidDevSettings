package de.mchllngr.devsettings.hover;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.View;

/**
 * TabIcon for each hover-screen.
 */
public class TabView extends View {

    private int backgroundColor;
    private Integer foregroundColor;

    private Drawable circleDrawable;
    private Drawable iconDrawable;
    private int iconInsetLeft, iconInsetTop, iconInsetRight, iconInsetBottom;

    public TabView(Context context, Drawable backgroundDrawable, Drawable iconDrawable) {
        super(context);
        circleDrawable = backgroundDrawable;
        this.iconDrawable = iconDrawable;
        init();
    }

    private void init() {
        int insetsDp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getContext().getResources().getDisplayMetrics());
        iconInsetLeft = iconInsetTop = iconInsetRight = iconInsetBottom = insetsDp;
    }

    public void setTabBackgroundColor(@ColorInt int backgroundColor) {
        this.backgroundColor = backgroundColor;
        circleDrawable.setColorFilter(this.backgroundColor, PorterDuff.Mode.SRC_ATOP);
    }

    public void setTabForegroundColor(@ColorInt Integer foregroundColor) {
        this.foregroundColor = foregroundColor;
        if (null != this.foregroundColor) {
            iconDrawable.setColorFilter(this.foregroundColor, PorterDuff.Mode.SRC_ATOP);
        } else {
            iconDrawable.setColorFilter(null);
        }
    }

    public void setIcon(@Nullable Drawable icon) {
        iconDrawable = icon;
        if (null != foregroundColor && null != iconDrawable) {
            iconDrawable.setColorFilter(foregroundColor, PorterDuff.Mode.SRC_ATOP);
        }
        updateIconBounds();

        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        // Make circle as large as View minus padding.
        circleDrawable.setBounds(getPaddingLeft(), getPaddingTop(), w - getPaddingRight(), h - getPaddingBottom());

        // Re-size the icon as necessary.
        updateIconBounds();

        invalidate();
    }

    private void updateIconBounds() {
        if (null != iconDrawable) {
            Rect bounds = new Rect(circleDrawable.getBounds());
            bounds.set(bounds.left + iconInsetLeft, bounds.top + iconInsetTop, bounds.right - iconInsetRight, bounds.bottom - iconInsetBottom);
            iconDrawable.setBounds(bounds);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        circleDrawable.draw(canvas);
        if (null != iconDrawable) {
            iconDrawable.draw(canvas);
        }
    }
}

