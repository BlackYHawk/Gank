package com.hawk.gank.features.gank.home.fragments;

import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.hawk.gank.R;
import com.hawk.gank.features.gank.home.GankPresenter;
import com.hawk.gank.features.gank.home.GankUiCallbacks;
import com.hawk.gank.model.error.RxError;
import com.hawk.gank.util.StringUtil;
import com.hawk.lib.base.ui.fragment.BaseTabFragment;
import com.hawk.lib.base.util.UIHelper;
import com.hawk.lib.mvp.util.Preconditions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by heyong on 2016/11/6.
 */

public class GankTabFragment extends BaseTabFragment<GankPresenter.GankTabView, GankUiCallbacks,
        GankPresenter<GankPresenter.GankTabView>> implements GankPresenter.GankTabView {

    private List<GankPresenter.GankTab> mTabList;

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
    public void setTabs(List<GankPresenter.GankTab> tabList) {
        Preconditions.checkNotNull(tabList, "tabList cannot be null");
        mTabList = tabList;

        if (getAdapter().getCount() != tabList.size()) {
            ArrayList<Fragment> fragments = new ArrayList<>();
            for (int i = 0; i < tabList.size(); i++) {
                fragments.add(createFragmentForTab(tabList.get(i)));
            }
            setFragments(fragments);
        }
    }

    @Override
    protected String getTabTitle(int position) {
        if (mTabList != null) {
            return getString(StringUtil.getStringResId(mTabList.get(position)));
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
                getCallbacks().showGankTag();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected boolean shouldHaveOptionsMenu() {
        return true;
    }
}
