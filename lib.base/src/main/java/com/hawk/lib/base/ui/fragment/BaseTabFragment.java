package com.hawk.lib.base.ui.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.hawk.lib.base.R;
import com.hawk.lib.base.ui.widget.SlidingTabLayout;
import com.hawk.lib.mvp.ui.presenter.BasePresenter;
import com.hawk.lib.mvp.ui.view.BaseView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by heyong on 2016/11/6.
 */

public abstract class BaseTabFragment<V extends BaseView<VC>, VC, P extends BasePresenter<V, VC>>
        extends BaseFragment<V, VC, P> {
    private static final String SAVE_SELECTED_TAB = "selected_tab";

    private ViewPager mViewPager;
    private SlidingTabLayout mSlidingTabLayout;

    private TabPagerAdapter mAdapter;
    private int mCurrentItem = 0;


    @Override
    protected void bindView(View rootView) {
        super.bindView(rootView);
        mSlidingTabLayout = (SlidingTabLayout) rootView.findViewById(R.id.slidingTabLayout);
        mViewPager = (ViewPager) rootView.findViewById(R.id.viewpager);
    }

    @Override
    protected void onBindData() {
        mAdapter = new TabPagerAdapter(getChildFragmentManager());
        mViewPager.setAdapter(mAdapter);
        mSlidingTabLayout.setViewPager(mViewPager);
    }

    protected void setFragments(List<Fragment> fragments) {
        mAdapter.setFragments(fragments);
        mSlidingTabLayout.notifyDataSetChanged();
        mViewPager.setCurrentItem(mCurrentItem);
    }

    protected abstract String getTabTitle(int position);

    protected TabPagerAdapter getAdapter() {
        return mAdapter;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.ac_ui_base_tab;
    }

    @Override
    protected boolean autoBindViews() {
        return false;
    }

    public class TabPagerAdapter extends FragmentPagerAdapter {
        private final ArrayList<Fragment> mFragments;

        private TabPagerAdapter(FragmentManager fm) {
            super(fm);
            mFragments = new ArrayList<>();
        }

        void setFragments(List<Fragment> fragments) {
            mFragments.clear();
            mFragments.addAll(fragments);
            notifyDataSetChanged();
        }

        @Override
        public final Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public final int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return getTabTitle(position);
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }
    }
}
