package com.hawk.lib.base.ui.fragment.dialog;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
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
import com.hawk.lib.mvp.component.BaseComponent;
import com.hawk.lib.mvp.component.GetComponent;
import com.hawk.lib.mvp.ui.display.BaseDisplay;
import com.hawk.lib.mvp.ui.presenter.BasePresenter;

/**
 * Created by lan on 2016/10/27.
 */

public abstract class BaseDailogFragment<V extends BaseDisplay, P extends BasePresenter<V>, C extends BaseComponent<V, P>>
        extends DialogFragment implements TransactionCommitter {
    private static final float DEFAULT_DIM_AMOUNT = 0.2F;
    protected P mPresenter;

    private final SupportDialogFragmentDismissDelegate mSupportDialogFragmentDismissDelegate
            = new SupportDialogFragmentDismissDelegate();
    private final SupportFragmentTransactionDelegate mSupportFragmentTransactionDelegate
            = new SupportFragmentTransactionDelegate();

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        // inject argument first
        final C component = ((GetComponent<C>) getActivity()).getComponent();
        mPresenter = component.presenter();
        injectDependence(component);
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
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mSupportDialogFragmentDismissDelegate.onResumed(this);
        mSupportFragmentTransactionDelegate.onResumed();
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

    @Override
    public boolean isCommitterResumed() {
        return isResumed();
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

    /**
     * inject dependencies.
     * Normally implementation should be {@code component.inject(this)}
     */
    protected abstract void injectDependence(C component);
}
