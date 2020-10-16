package com.example.ticket.Test;

import android.text.Editable;
import android.text.TextWatcher;
import com.braintreepayments.cardform.view.CardForm;


public class GuestValidator implements TextWatcher {
    public void validCreditCard(){
        CardForm cardForm = null;
        cardForm.cardRequired(true)
                .expirationRequired(true)
                .cvvRequired(true)
                .postalCodeRequired(true)
                .mobileNumberRequired(true)
                .mobileNumberExplanation("SMS is required on this number");
    }
    private boolean mIsValid = false;

    public boolean isValid() {
        return mIsValid;
    }

    public static boolean isValidCreditCard(CharSequence card) {
        return card != null && isValidCreditCard(card);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        mIsValid=isValidCreditCard(s);
    }
}
