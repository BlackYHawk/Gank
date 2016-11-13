package com.hawk.lib.base.ui.fragment.dialog;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.hawk.lib.base.ui.activity.StartActivityDelegate;
import com.hawk.lib.base.ui.fragment.SupportFragmentTransactionDelegate;
import com.hawk.lib.base.ui.fragment.TransactionCommitter;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by lan on 2016/10/27.
 */

public abstract class BaseDailogFragment extends DialogFragment implements TransactionCommitter {
    private static final float DEFAULT_DIM_AMOUNT = 0.2F;

    private final SupportDialogFragmentDismissDelegate mSupportDialogFragmentDismissDelegate
            = new SupportDialogFragmentDismissDelegate();
    private final SupportFragmentTransactionDelegate mSupportFragmentTransactionDelegate
            = new SupportFragmentTransactionDelegate();
    private Unbinder mUnBinder;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        // inject argument first
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        return new Dialog(getActivity(), getTheme()) {
            @Override
            public void onBackPressed() {
                if (isCanceledOnBackPressed()) {
                    super.onBackPressed();
                }
            }
        };
    }
    @Override
    public void onStart() {
        super.onStart();
        // Less dimmed background; see http://stackoverflow.com/q/13822842/56285
        final Window window = getDialog().getWindow();
        final WindowManager.LayoutParams params = window.getAttributes();
        params.dimAmount = getDimAmount(); // dim only a little bit
        window.setAttributes(params);

        window.setLayout(getWidth(), getHeight());
        window.setGravity(getGravity());

        // Transparent background; see http://stackoverflow.com/q/15007272/56285
        // (Needed to make dialog's alpha shadow look good)
        window.setBackgroundDrawableResource(android.R.color.transparent);

        final Resources res = getResources();
        final int titleDividerId = res.getIdentifier("titleDivider", "id", "android");
        if (titleDividerId > 0) {
            final View titleDivider = getDialog().findViewById(titleDividerId);
            if (titleDivider != null) {
                titleDivider.setBackgroundColor(res.getColor(android.R.color.transparent));
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(isCanceledOnTouchOutside());
        return inflater.inflate(getLayoutRes(), container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindView(view);
        onInitData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbindView();
    }

    @Override
    public void onResume() {
        super.onResume();
        mSupportDialogFragmentDismissDelegate.onResumed(this);
        mSupportFragmentTransactionDelegate.onResumed();
    }

    @Override
    public boolean isCommitterResumed() {
        return isResumed();
    }

    protected final boolean startActivitySafely(final Intent intent) {
        return StartActivityDelegate.startActivitySafely(this, intent);
    }

    protected boolean safeCommit(@NonNull final FragmentTransaction transaction) {
        return mSupportFragmentTransactionDelegate.safeCommit(this, transaction);
    }

    public boolean safeDismiss() {
        return mSupportDialogFragmentDismissDelegate.safeDismiss(this);
    }

    protected float getDimAmount() {
        return DEFAULT_DIM_AMOUNT;
    }

    protected abstract int getWidth();

    protected abstract int getHeight();

    protected int getGravity() {
        return Gravity.CENTER;
    }

    protected boolean isCanceledOnTouchOutside() {
        return true;
    }

    protected boolean isCanceledOnBackPressed() {
        return true;
    }

    protected abstract int getLayoutRes();

    protected boolean autoBindViews() {
        return true;
    }

    @CallSuper
    protected void bindView(final View rootView) {
        if (autoBindViews()) {
            mUnBinder = ButterKnife.bind(this, rootView);
        }
    }

    protected void unbindView() {
        if (autoBindViews() && mUnBinder != null) {
            mUnBinder.unbind();
        }
    }

    protected void onInitData() {

    }

}
