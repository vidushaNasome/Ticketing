package com.example.ticket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ActivatePackage extends AppCompatActivity {
    private Session session;
    String name;
    TextView username,email,credits;
    String credits1,name1,email1,password1;
    Button pack_ac1,pack_ac2,pack_ac3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activate_package);

        session = new Session(getApplicationContext());
        name = session.getusename();
        username = (TextView) findViewById(R.id.unid);
        email = findViewById(R.id.emailid);
        credits = findViewById(R.id.creditsId);

        pack_ac1 = findViewById(R.id.ac1);
        pack_ac2= findViewById(R.id.ac2);
        pack_ac3= findViewById(R.id.ac3);

        try{
        DatabaseReference displayDf = FirebaseDatabase.getInstance().getReference().child("User").child(name);
        displayDf.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()) {
                    name1 = dataSnapshot.child("username").getValue().toString();
                    email1 = dataSnapshot.child("email").getValue().toString();
                    credits1 = dataSnapshot.child("activatedcredits").getValue().toString();
                    username.setText(name1);
                    email.setText(email1);
                    credits.setText(credits1);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });}catch(Exception e){}
}

    @Override
    protected void onResume() {
        super.onResume();

        pack_ac1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ActivatePackage.this, Payment.class);
                i.putExtra("price","200");
                startActivity(i);
            }
        });

        pack_ac2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ActivatePackage.this, Payment.class);
                i.putExtra("price","500");
                startActivity(i);
            }
        });

        pack_ac3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ActivatePackage.this, Payment.class);
                i.putExtra("price","1000");
                startActivity(i);
            }
        });
    }
}
