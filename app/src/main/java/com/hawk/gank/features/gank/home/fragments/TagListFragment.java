package com.hawk.gank.features.gank.home.fragments;

import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hawk.gank.R;
import com.hawk.gank.features.gank.home.GankPresenter;
import com.hawk.gank.model.bean.Tag;
import com.hawk.lib.base.ui.adapter.BaseViewHolder;

import butterknife.BindView;

import static com.hawk.gank.features.gank.home.GankPresenter.GankQueryType.TAGLIST;

/**
 * Created by heyong on 2016/11/28.
 */

public class TagListFragment extends BaseGankListFragment<Tag> implements GankPresenter.TagListView {

    public static TagListFragment newInstance() {
        TagListFragment fragment = new TagListFragment();

        return fragment;
    }

    @Override
    public GankPresenter.GankQueryType getGankQueryType() {
        return TAGLIST;
    }

    @Override
    public boolean isModal() {
        return false;
    }

    @Override
    protected boolean shouldHaveOptionsMenu() {
        return true;
    }

    @Override
    protected BaseViewHolder getViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tag, parent, false);
        return new TagViewHolder(view);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getCallbacks().finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    class TagViewHolder extends BaseViewHolder {

        @BindView(R.id.tvTitle) TextView tvTitle;
        @BindView(R.id.switchOn) SwitchCompat switchOn;

        public TagViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindViewHolder(final int position) {
            final Tag tag = mDataList.get(position);

            tvTitle.setText(tag.type());
            switchOn.setChecked(tag.valid());
            switchOn.setOnClickListener(view -> getCallbacks().updateTag(Tag.builder(tag).
                    valid(switchOn.isChecked()).build()));
        }

    }
}
