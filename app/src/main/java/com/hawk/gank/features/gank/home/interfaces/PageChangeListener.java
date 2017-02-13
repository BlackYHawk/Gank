package com.hawk.gank.features.gank.home.interfaces;

import android.support.v4.view.ViewPager;

/**
 * Created by heyong on 2017/2/7.
 */

public class PageChangeListener implements ViewPager.OnPageChangeListener {

    private int mCurrentPosition;
    private boolean mIsScrolling = false;


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
