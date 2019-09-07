package com.murat.todolist.util;

import android.text.TextUtils;
import android.util.Patterns;

import java.util.Date;

public class InputValidation {
    public static boolean isValidEmail(String email) {
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    public static boolean isValidPassword(String password) {
        return (!TextUtils.isEmpty(password) && password.length() > 5);
    }

    public static boolean isValidText(String text) {
        return !TextUtils.isEmpty(text);
    }

    public static boolean isValidDate(Date date) {
        return date != null;
    }
}
