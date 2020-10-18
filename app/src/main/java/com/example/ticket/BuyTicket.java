package com.example.ticket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
//purchase ticket
public class BuyTicket extends AppCompatActivity {
    Button btnc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_ticket);
        //Action bar title
        getSupportActionBar().setTitle("Buy Ticket");

        btnc=findViewById(R.id.btncheck);

        //buy now button
        btnc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cc=new Intent(BuyTicket.this,GuestCheckout.class);
                startActivity(cc);
            }
        });
    }
}