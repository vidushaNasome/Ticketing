package com.example.ticket.Test;
import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import java.util.regex.Pattern;

public class GuestAccountValidator implements TextWatcher {
    public static final Pattern EMAIL_PATTERN=Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    );
    private boolean mIsValid = false;

    public boolean isValid() {
        return mIsValid;
    }
    public static boolean isValidEmail(CharSequence email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }
    public static boolean isValidUserName(CharSequence username){
        return username !=null;
    }
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        mIsValid=isValidEmail(s);
        mIsValid=isValidUserName(s);
    }
}
