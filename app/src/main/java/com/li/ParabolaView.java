package com.li;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.PointF;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

/**
 * Created by henseyVenom on 2016/1/15.
 */
public class ParabolaView extends ImageView {
    PointF startPoint;

    public ParabolaView(Context context) {
        this(context, null);
    }

    public ParabolaView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ParabolaView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onGlobalLayout() {
                startPoint = new PointF(getX(), getY());
                getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ParabolaView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /**
     * 开始抛射动画
     * @param speed 初始抛射速度
     * @param endPoint 结束位置
     */
    public void startParabola(final int speed, final PointF endPoint) {

        setAlpha(1.0f);
        //抛射角度
        final double alpha = 30;
        Log.e("ParabolaView","end y : "+endPoint.y);

        final float  x    = endPoint.x - startPoint.x;
        final float  y    = endPoint.y - startPoint.y;
        final double time = ((x) / (speed * Math.cos(Math.toRadians(alpha))));
        final double g    = (((y-startPoint.y) + speed * Math.sin(Math.toRadians(alpha) * time)) * 2 / Math.pow(time, 2));
        Log.e("ParabolaView","g : "+g);
        ValueAnimator animator = new ValueAnimator();
        animator.setDuration((long) (time * 1000));
        animator.setObjectValues(startPoint);
        animator.setEvaluator(new TypeEvaluator<PointF>() {
            @Override
            public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
                float currentTime= (float) (fraction*time);
                float x = (float) (speed*Math.cos(Math.toRadians(alpha)) * currentTime + startPoint.x);
                float y= (float) ((0.5*g*Math.pow(currentTime,2))-(speed*Math.sin(Math.toRadians(alpha))*currentTime)+startPoint.y);
                return new PointF(x, y);
            }
        });
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF pointF = (PointF) animation.getAnimatedValue();
                setX(pointF.x);
                setY(pointF.y);
            }
        });
        ObjectAnimator alphaAnimator=ObjectAnimator.ofFloat(this,"alpha",1.0f,0f).setDuration(200);
        int rotateCount= (int) (time*1000/100);
        ObjectAnimator rotateAnimator=ObjectAnimator.ofFloat(this,"rotation",0f,360*rotateCount).setDuration((long) (time*1000));
        AnimatorSet set=new AnimatorSet();
        set.play(animator).with(alphaAnimator);
        set.play(animator).with(rotateAnimator);
        set.play(alphaAnimator).after((long) (time*1000-200));
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                setX(startPoint.x);
                setY(startPoint.y);
                setRotation(0.f);
                setAlpha(1.0f);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        set.start();
    }
}
