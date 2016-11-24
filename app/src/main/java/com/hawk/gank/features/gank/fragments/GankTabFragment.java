package com.hawk.gank.features.gank.fragments;

import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.hawk.gank.R;
import com.hawk.gank.features.gank.GankPresenter;
import com.hawk.gank.features.gank.GankUiCallbacks;
import com.hawk.gank.model.error.RxError;
import com.hawk.gank.util.StringUtil;
import com.hawk.gank.util.UIHelper;
import com.hawk.lib.base.ui.fragment.BaseTabFragment;
import com.hawk.lib.mvp.util.Preconditions;

import java.util.ArrayList;

/**
 * Created by heyong on 2016/11/6.
 */

public class GankTabFragment extends BaseTabFragment<GankPresenter.GankTabView, GankUiCallbacks,
        GankPresenter<GankPresenter.GankTabView>> implements GankPresenter.GankTabView {

    private GankPresenter.GankTab[] mTabs;

    @Override
    public GankPresenter.GankQueryType getGankQueryType() {
        return GankPresenter.GankQueryType.TAB;
    }

    @Override
    public boolean isModal() {
        return false;
    }

    @Override
    public void showError(RxError error) {
        UIHelper.showToast(getActivity(), error.message());
    }

    @Override
    public void setTabs(GankPresenter.GankTab... tabs) {
        Preconditions.checkNotNull(tabs, "tabs cannot be null");
        mTabs = tabs;

        if (getAdapter().getCount() != tabs.length) {
            ArrayList<Fragment> fragments = new ArrayList<>();
            for (int i = 0; i < tabs.length; i++) {
                fragments.add(createFragmentForTab(tabs[i]));
            }
            setFragments(fragments);
        }
    }

    @Override
    protected String getTabTitle(int position) {
        if (mTabs != null) {
            return getString(StringUtil.getStringResId(mTabs[position]));
        }
        return null;
    }

    private Fragment createFragmentForTab(GankPresenter.GankTab tab) {
        switch (tab) {
            case ANDROID:
                return AndroidListFragment.newInstance();
            case IOS:
                return IosListFragment.newInstance();
            case WELFARE:
                return WelfareListFragment.newInstance();
            case FROANT:
                return FrontListFragment.newInstance();
            case EXPAND:
                return ExpandListFragment.newInstance();
            case VIDEO:
                return VideoListFragment.newInstance();
        }
        return null;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_gank_tab, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.filter :

                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
