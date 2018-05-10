package com.askjeffreyliu.library;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Change circle to any icon
 */

public class RippleBackground extends FrameLayout {

    private static final int DEFAULT_RIPPLE_COUNT = 6;
    private static final int DEFAULT_DURATION_TIME = 3000;
    private static final float DEFAULT_SCALE = 6.0f;
    private static final int DEFAULT_FILL_TYPE = 0;

    private float rippleStrokeWidth;

    private Paint paint;
    private boolean animationRunning = false;
    private boolean isIcon = false; // default is false, drawing circle
    private AnimatorSet animatorSet;

    private ArrayList<RippleView> rippleViewList = new ArrayList<>();

    public RippleBackground(Context context) {
        super(context);
    }

    public RippleBackground(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public RippleBackground(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(final Context context, final AttributeSet attrs) {
        if (isInEditMode())
            return;

        if (null == attrs) {
            throw new IllegalArgumentException("Attributes should be provided to this view,");
        }

        final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RippleBackground);
        int rippleColor = typedArray.getColor(R.styleable.RippleBackground_rb_color, getResources().getColor(R.color.rippelColor));
        rippleStrokeWidth = typedArray.getDimension(R.styleable.RippleBackground_rb_strokeWidth, getResources().getDimension(R.dimen.rippleStrokeWidth));
        float rippleRadius = typedArray.getDimension(R.styleable.RippleBackground_rb_radius, getResources().getDimension(R.dimen.rippleRadius));
        int rippleDurationTime = typedArray.getInt(R.styleable.RippleBackground_rb_duration, DEFAULT_DURATION_TIME);
        int rippleAmount = typedArray.getInt(R.styleable.RippleBackground_rb_rippleAmount, DEFAULT_RIPPLE_COUNT);
        float rippleScale = typedArray.getFloat(R.styleable.RippleBackground_rb_scale, DEFAULT_SCALE);
        int rippleType = typedArray.getInt(R.styleable.RippleBackground_rb_type, DEFAULT_FILL_TYPE);
        Drawable drawable = typedArray.getDrawable(R.styleable.RippleBackground_rb_icon);
        if (drawable != null) {
            isIcon = true;
        }
        typedArray.recycle();

        int rippleDelay = rippleDurationTime / rippleAmount;

        paint = new Paint();
        paint.setAntiAlias(true);
        if (rippleType == DEFAULT_FILL_TYPE) {
            rippleStrokeWidth = 0;
            paint.setStyle(Paint.Style.FILL);
        } else
            paint.setStyle(Paint.Style.STROKE);
        paint.setColor(rippleColor);

        LayoutParams rippleParams = new LayoutParams((int) (2 * (rippleRadius + rippleStrokeWidth)), (int) (2 * (rippleRadius + rippleStrokeWidth)));
        rippleParams.gravity = Gravity.CENTER;

        animatorSet = new AnimatorSet();
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        ArrayList<Animator> animatorList = new ArrayList<>();

        for (int i = 0; i < rippleAmount; i++) {
            RippleView rippleView = new RippleView(getContext());
            if (drawable != null) {
                rippleView.setImageDrawable(drawable);
            }
            addView(rippleView, rippleParams);
            rippleViewList.add(rippleView);
            final ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(rippleView, "ScaleX", 1.0f, rippleScale);
            scaleXAnimator.setRepeatCount(ObjectAnimator.INFINITE);
            scaleXAnimator.setRepeatMode(ObjectAnimator.RESTART);
            scaleXAnimator.setStartDelay(i * rippleDelay);
            scaleXAnimator.setDuration(rippleDurationTime);
            animatorList.add(scaleXAnimator);
            final ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(rippleView, "ScaleY", 1.0f, rippleScale);
            scaleYAnimator.setRepeatCount(ObjectAnimator.INFINITE);
            scaleYAnimator.setRepeatMode(ObjectAnimator.RESTART);
            scaleYAnimator.setStartDelay(i * rippleDelay);
            scaleYAnimator.setDuration(rippleDurationTime);
            animatorList.add(scaleYAnimator);
            final ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(rippleView, "Alpha", 1.0f, 0f);
            alphaAnimator.setRepeatCount(ObjectAnimator.INFINITE);
            alphaAnimator.setRepeatMode(ObjectAnimator.RESTART);
            alphaAnimator.setStartDelay(i * rippleDelay);
            alphaAnimator.setDuration(rippleDurationTime);
            animatorList.add(alphaAnimator);
        }

        animatorSet.playTogether(animatorList);
    }

    private class RippleView extends ImageView {


        public RippleView(Context context) {
            super(context);
            this.setVisibility(View.INVISIBLE);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            if (isIcon) {
                super.onDraw(canvas);
                return;
            }
            int radius = (Math.min(getWidth(), getHeight())) / 2;
            canvas.drawCircle(radius, radius, radius - rippleStrokeWidth, paint);
        }
    }

    public void startRippleAnimation() {
        if (!isRippleAnimationRunning()) {
            for (RippleView rippleView : rippleViewList) {
                rippleView.setVisibility(VISIBLE);
            }
            animatorSet.start();
            animationRunning = true;
        }
    }

    public void stopRippleAnimation() {
        if (isRippleAnimationRunning()) {
            animatorSet.end();
            animationRunning = false;
        }
    }

    public boolean isRippleAnimationRunning() {
        return animationRunning;
    }
}