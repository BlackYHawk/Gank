package com.hawk.gank.interfaces;

import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.text.TextWatcher;

/**
 * Created by heyong on 16/7/24.
 */
public abstract class AbstractTextWatcher implements TextWatcher {

    private TextInputLayout mTextInputLayout;
    private String errorMessage;

    protected AbstractTextWatcher(@NonNull final TextInputLayout textInputLayout, @NonNull final String errorMessage) {
        this.mTextInputLayout = textInputLayout;
        this.errorMessage = errorMessage;
    }

    protected void showError(final boolean error) {
        if (!error) {
            mTextInputLayout.setError(null);
            mTextInputLayout.setErrorEnabled(false);
        } else {
            if (!errorMessage.equals(mTextInputLayout.getError())) {
                // Stop the flickering that happens when setting the same error message multiple times
                mTextInputLayout.setError(errorMessage);
            }
            mTextInputLayout.requestFocus();
        }
    }

    public final boolean hasError() {
        return mTextInputLayout.getError() != null;
    }

    public String getEditTextValue() {
        return mTextInputLayout.getEditText().getText().toString();
    }

    public abstract boolean validate();

}
