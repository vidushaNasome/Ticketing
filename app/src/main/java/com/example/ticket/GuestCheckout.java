package com.example.ticket;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.braintreepayments.cardform.view.CardForm;
import com.example.ticket.Model.CreditCard;
import com.google.firebase.database.DatabaseReference;

public class GuestCheckout extends AppCompatActivity {
    CardForm cardForm;
    Button buy,exit;
    AlertDialog.Builder alertBuilder;
    CreditCard card;

    DatabaseReference dbRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_checkout);
        getSupportActionBar().setTitle("Credit Card Payment");

        cardForm=findViewById(R.id.card_form);
        buy=findViewById(R.id.btnBuy);
        exit=findViewById(R.id.btnExit);

        card = new CreditCard();

        cardForm.cardRequired(true)
                .expirationRequired(true)
                .cvvRequired(true)
                .postalCodeRequired(true)
                .mobileNumberRequired(true)
                .mobileNumberExplanation("SMS is required on this number")
                .setup(GuestCheckout.this);
        cardForm.getCvvEditText().setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cardForm.isValid())
                {
                    alertBuilder=new AlertDialog.Builder(GuestCheckout.this);
                    alertBuilder.setTitle("Confirm before purchase");
                    alertBuilder.setMessage("Card number:"+cardForm.getCardNumber()+"\n"+
                            "Card expiry date:"+cardForm.getExpirationDateEditText().getText().toString()+"\n"+
                            "Card CVV:"+cardForm.getCvv()+"\n"+
                            "Postal Code"+cardForm.getPostalCode()+"\n"+
                            "Phone Number"+cardForm.getMobileNumber());
                    alertBuilder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            Toast.makeText(GuestCheckout.this,"Success...!!!",Toast.LENGTH_LONG).show();
                        }
                    });
                    alertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    AlertDialog alertDialog=alertBuilder.create();
                    alertDialog.show();
                }else{
                    Toast.makeText(GuestCheckout.this,"Please complete the form",Toast.LENGTH_LONG).show();
                }

            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(GuestCheckout.this,MainActivity.class);
                startActivity(i);
            }
        });

    }
}