package com.hawk.gank.interfaces.impl;

import com.hawk.gank.interfaces.AbstractValidator;

/**
 * Created by heyong on 16/7/24.
 */
public class MinLenValidator extends AbstractValidator {

    private static final String regex = "^[A-Za-z0-9]{4,12}";

    @Override
    public boolean isValid(String str) {
        return str.matches(regex);
    }
}
