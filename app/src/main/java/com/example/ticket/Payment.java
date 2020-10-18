package com.example.ticket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.braintreepayments.cardform.view.CardForm;
import com.example.ticket.Model.CreditCard;
import com.example.ticket.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

//Payemnt Acitivity
//Called to credit card payemnt
public class Payment extends AppCompatActivity {
    //creating variables
    CardForm cardForm;
    Button buy;
    AlertDialog.Builder alertBuilder;
    CreditCard card;
    TextView displayAm;
    private Session session;
    String name;
    DatabaseReference updateeml;
    User us;
    float val;
    DatabaseReference checkdata;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //declaring varibles
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        Intent i=getIntent();
        val=Float.parseFloat(i.getStringExtra("price"));
        displayAm=(TextView) findViewById(R.id.disAmm);
        session = new Session(getApplicationContext());
        name = session.getusename();

        Toast.makeText(getApplicationContext(), "You Have Selected Package:"+val , Toast.LENGTH_LONG).show();
        displayAm.setText("Rs."+val);
        cardForm=findViewById(R.id.card_form);
        buy=findViewById(R.id.btnBuy);


        card = new CreditCard();
        us=new User();

        cardForm.cardRequired(true)
                .expirationRequired(true)
                .cvvRequired(true)
                .setup(Payment.this);

        cardForm.getCvvEditText().setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    alertBuilder=new AlertDialog.Builder(Payment.this);
                    alertBuilder.setTitle("Confirm before purchase");
                    alertBuilder.setMessage("Card number:"+cardForm.getCardNumber()+"\n"+
                            "Card expiry date:"+cardForm.getExpirationDateEditText().getText().toString()+"\n"+
                            "Card CVV:"+cardForm.getCvv()+"\n"+
                    alertBuilder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();

                            checkdata = FirebaseDatabase.getInstance().getReference().child("User").child(name);

                            checkdata.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.hasChildren()) {
                                        String username = dataSnapshot.child("username").getValue().toString();
                                        final String password= dataSnapshot.child("password").getValue().toString();
                                        final String email= dataSnapshot.child("email").getValue().toString();
                                        final String address= dataSnapshot.child("address").getValue().toString();
                                        final float Activatedcredits= Float.parseFloat(dataSnapshot.child("activatedcredits").getValue().toString());

                                                updateeml= FirebaseDatabase.getInstance().getReference().child("User");
                                                updateeml.addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                        float tot=Activatedcredits+val;
                                                        us.setPassword(password);
                                                        us.setEmail(email);
                                                        us.setAddress(address);
                                                        us.setUsername(name);
                                                        us.setActivatedcredits(tot);
                                                        updateeml.child(name).setValue(us);
                                                        Intent i = new Intent(Payment.this, Dashboard.class);
                                                        startActivity(i);
                                                        Toast.makeText(getApplicationContext(), "Successfully Activated the Credits!", Toast.LENGTH_LONG).show();
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                                    }
                                                });





                                    }

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });


                            /*updateeml= FirebaseDatabase.getInstance().getReference().child("User");
                            updateeml.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    us.setActivatedcredits(val);
                                    updateeml.child(name).setValue(us);
                                    Toast.makeText(getApplicationContext(), "blah blah blah!", Toast.LENGTH_LONG).show();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });*/



                        //Toast.makeText(Payment.this,"Thank you for purchase",Toast.LENGTH_LONG).show();
                        }
                    }));
                    alertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    AlertDialog alertDialog=alertBuilder.create();
                    alertDialog.show();


            }
        });

    }
}