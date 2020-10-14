package com.example.ticket.Model;


import com.braintreepayments.cardform.view.CardForm;

public class CreditCard {
    private CardForm form1;

    public CreditCard(CardForm form1) {
        this.form1 = form1;
    }

    public CreditCard() {

    }

    public CardForm getForm1() {
        return form1;
    }

    public void setForm1(String form1) {
        form1 = form1;
    }
}
