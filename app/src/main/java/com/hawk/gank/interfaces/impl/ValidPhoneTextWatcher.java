package com.hawk.gank.interfaces.impl;

import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;

import com.hawk.gank.R;
import com.hawk.gank.interfaces.AbstractTextWatcher;

/**
 * Created by heyong on 16/7/24.
 */
public class ValidPhoneTextWatcher extends AbstractTextWatcher {

    private final PhoneValidator mValidator = new PhoneValidator();
    private boolean mValidated = true;

    public ValidPhoneTextWatcher(@NonNull TextInputLayout textInputLayout) {
        this(textInputLayout, R.string.error_invalid_phone);
    }

    public ValidPhoneTextWatcher(@NonNull final TextInputLayout textInputLayout, @StringRes final int errorMessage) {
        super(textInputLayout, textInputLayout.getContext().getString(errorMessage));
    }

    @Override
    public boolean validate() {
        showError(!mValidator.isValid(getEditTextValue()));
        mValidated = true;
        return !hasError();
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int start, int count, int after) {
        if(mValidated) {
            validate();
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
