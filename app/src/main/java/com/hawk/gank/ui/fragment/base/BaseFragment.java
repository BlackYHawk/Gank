package com.hawk.gank.ui.fragment.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.hawk.gank.ui.activity.base.BaseActivity;

/**
 * Created by lan on 2016/6/29.
 */
public abstract class BaseFragment extends Fragment {

    protected int titleId = -1;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof BaseActivity)
            ((BaseActivity) context).addFragment(toString(), this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getActivity() instanceof BaseActivity) {
      //      ((BaseActivity) getActivity()).component().inject(this);

            if(titleId != -1) {
                ((BaseActivity) getActivity()).setTitle(titleId);
            }
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        if (getActivity() != null && getActivity() instanceof BaseActivity)
            ((BaseActivity) getActivity()).removeFragment(this.toString());
    }

    protected void setTitle(int strId) {
        if (getActivity() != null && getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).setTitle(strId);
        }
    }

    protected void dismiss() {
        if (getActivity() != null && getActivity() instanceof BaseActivity) {
            ((BaseActivity)getActivity()).backFragment();
        }
    }

    protected void finish() {
        if (getActivity() != null) {
            getActivity().finish();
        }
    }

    /**
     * 返回按键被点击了
     *
     * @return
     */
    public boolean onBackClick() {
        return false;
    }
}
