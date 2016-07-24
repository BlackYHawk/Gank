package com.hawk.gank.interfaces.impl;

import com.hawk.gank.interfaces.AbstractValidator;

/**
 * Created by heyong on 16/7/24.
 */
public class PhoneValidator extends AbstractValidator {

    private static final String regex = "^1[3|4|5|7|8]\\d{9}$";

    @Override
    public boolean isValid(String phone) {
        return phone.matches(regex);
    }
}
