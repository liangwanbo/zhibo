package com.mytv365.zb.utils;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.PointF;
import android.util.Log;
import android.view.View;

/**
 * Author   :hymanme
 * Email    :hymanme@163.com
 * Create at 2016/10/14
 * Description:
 */

public class AnimUtils {
    public static ValueAnimator BezierUpDown(Context context, final View view, PointF startP, PointF endP) {
        final ValueAnimator animator = ValueAnimator.ofObject(new BezierEvaluator(ScreenUtils.getScreenWidth(context), ScreenUtils.getScreenHeight(context)), startP, endP);
//            animator.setEvaluator(new BezierEvaluator(clean_screen.getWidth(), clean_screen.getHeight()));
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                final Object animatedValue = animation.getAnimatedValue();
                view.setX(((PointF) animatedValue).x);
                view.setY(((PointF) animatedValue).y);
                Log.i("X=" + ((PointF) animatedValue).x, "|Y=" + ((PointF) animatedValue).y);
            }
        });
        animator.setDuration(2000);
        return animator;
    }

    static class BezierEvaluator implements TypeEvaluator<PointF> {
        private float width;
        private float height;

        public BezierEvaluator(float width, float height) {
            this.width = width;
            this.height = height;
        }

        @Override
        public PointF evaluate(float fraction, PointF startValue,
                               PointF endValue) {
            final float t = fraction;
            float oneMinusT = 1.0f - t;
            PointF point = new PointF();

            //起始位置
            PointF point0 = (PointF) startValue;

            //达到的最右边位置（如屏幕右边缘）
            PointF point1 = new PointF();
            point1.set(width, 0);

            //达到的最底部边位置（如屏幕底部边缘）
            PointF point2 = new PointF();
            point2.set(0, height);

            //结束位置
            PointF point3 = (PointF) endValue;

            point.x = oneMinusT * oneMinusT * oneMinusT * (point0.x)
                    + 3 * oneMinusT * oneMinusT * t * (point1.x)
                    + 3 * oneMinusT * t * t * (point2.x)
                    + t * t * t * (point3.x);

            point.y = oneMinusT * oneMinusT * oneMinusT * (point0.y)
                    + 3 * oneMinusT * oneMinusT * t * (point1.y)
                    + 3 * oneMinusT * t * t * (point2.y)
                    + t * t * t * (point3.y);
            return point;
        }
    }
}
