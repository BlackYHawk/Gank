package com.hawk.gank.features.gank.home.interfaces;

import android.graphics.Color;
import android.support.design.widget.CollapsingToolbarLayout;
import android.util.Log;

import com.hawk.lib.base.ui.widget.SlidingTabLayout;

/**
 * Created by heyong on 2017/2/7.
 */

public class ImageAnimator {
    int[] mColors = {
            Color.parseColor("#F44336"),
            Color.parseColor("#E91E63"),
            Color.parseColor("#9C27B0"),
            Color.parseColor("#673AB7"),
            Color.parseColor("#3F51B5"),
            Color.parseColor("#2196F3"),
            Color.parseColor("#03A9F4"),
            Color.parseColor("#00BCD4"),
            Color.parseColor("#009688"),
            Color.parseColor("#4CAF50"),
    };

    private static final float FACTOR = 0.1f;

    private CollapsingToolbarLayout collapsingToolbar;
    private SlidingTabLayout slidingTabLayout;

    private int mActualStart; // 实际起始位置

    private int mStart;
    private int mEnd;

    private boolean isSkip = false;//是否跳页

    public ImageAnimator(CollapsingToolbarLayout collapsingToolbar) {
        this.collapsingToolbar = collapsingToolbar;
        collapsingToolbar.setContentScrimColor(mColors[0]);
        collapsingToolbar.setStatusBarScrimColor(mColors[0]);
    }

    public void setTabLayout(SlidingTabLayout slidingTabLayout) {
        this.slidingTabLayout = slidingTabLayout;
        slidingTabLayout.setBackgroundColor(mColors[0]);
    }

    /**
     * 启动动画, 之后选择向前或向后滑动
     *
     * @param startPosition 起始位置
     * @param endPosition   终止位置
     */
    public void start(int startPosition, int endPosition) {
        if (Math.abs(endPosition - startPosition) > 1) {
            isSkip = true;
        }
        mActualStart = startPosition;
        Log.e("DEBUG", "startPosition: " + startPosition + ", endPosition: " + endPosition);
        // 终止位置的图片
        mStart = Math.min(startPosition, endPosition);
        mEnd = Math.max(startPosition, endPosition);
    }

    /**
     * 滑动结束的动画效果
     *
     * @param endPosition 滑动位置
     */
    public void end(int endPosition) {
        isSkip = false;
        //@DrawableRes int incomeId = ids[endPosition % ids.length];
        Log.e("DEBUG", "endPosition: " + endPosition);

        // 设置原始图片
        if (endPosition == mActualStart) {

        } else {
            collapsingToolbar.setContentScrimColor(mColors[endPosition]);
            collapsingToolbar.setStatusBarScrimColor(mColors[endPosition]);
            slidingTabLayout.setBackgroundColor(mColors[endPosition]);
        }
    }

    // 向前滚动, 比如0->1, offset滚动的距离(0->1), 目标渐渐淡出
    public void forward(int position, float positionOffset) {
        if (isSkip)
            return;

        int color = evaluate(positionOffset, mColors[position], mColors[position + 1]);

        collapsingToolbar.setContentScrimColor(color);
        collapsingToolbar.setStatusBarScrimColor(color);
        slidingTabLayout.setBackgroundColor(color);
    }

    // 向后滚动, 比如1->0, offset滚动的距离(1->0), 目标渐渐淡入
    public void backwards(int position, float positionOffset) {
        if (isSkip)
            return;

        int color = evaluate(1 - positionOffset, mColors[position + 1], mColors[position]);

        collapsingToolbar.setContentScrimColor(color);
        collapsingToolbar.setStatusBarScrimColor(color);
        slidingTabLayout.setBackgroundColor(color);
    }

    // 判断停止
    public boolean isWithin(int position) {
        return position >= mStart && position < mEnd;
    }

    /**
     * @param fraction
     * @param startValue
     * @param endValue
     * @return
     */
    public static Integer evaluate(float fraction, Object startValue, Object endValue) {
        int startInt = (Integer) startValue;
        int startA = (startInt >> 24) & 0xff;
        int startR = (startInt >> 16) & 0xff;
        int startG = (startInt >> 8) & 0xff;
        int startB = startInt & 0xff;
        int endInt = (Integer) endValue;
        int endA = (endInt >> 24) & 0xff;
        int endR = (endInt >> 16) & 0xff;
        int endG = (endInt >> 8) & 0xff;
        int endB = endInt & 0xff;
        return (int) ((startA + (int) (fraction * (endA - startA))) << 24)
                | (int) ((startR + (int) (fraction * (endR - startR))) << 16)
                | (int) ((startG + (int) (fraction * (endG - startG))) << 8)
                | (int) ((startB + (int) (fraction * (endB - startB))));
    }
}
