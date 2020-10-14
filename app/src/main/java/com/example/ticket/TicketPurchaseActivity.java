package com.example.ticket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ticket.Model.PassengerBusses;
import com.example.ticket.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TicketPurchaseActivity extends AppCompatActivity {
    TextView tp,pp,nn;
    float price;
   // private Session session;
    String name;
    Button purchase;
    User us;
    float val;
    DatabaseReference checkdata;
    DatabaseReference updateeml;
    String datadet;
    DatabaseReference dbRef,reff;
    long maxid=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_purchase);

        Intent i=getIntent();
        String data=i.getStringExtra("details");
        datadet=data;
        name = i.getStringExtra("un");

        //String string = "004-034556";
        String[] parts = data.split("-");
        String part1 = parts[0];
        String part2 = parts[1];
        tp=findViewById(R.id.ticketpt);
        pp=findViewById(R.id.pricept);
        nn=findViewById(R.id.namett);
        purchase=findViewById(R.id.purchaseTicket);

       // session = new Session(getApplicationContext());

        us=new User();
        tp.setText(data);
        pp.setText(part2);
        nn.setText(name);
        price=Float.parseFloat(part2);
        val=price;
        Toast.makeText(TicketPurchaseActivity.this, "Selected Price"+price, Toast.LENGTH_SHORT).show();




    }

    @Override
    protected void onResume() {
        super.onResume();

        purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                                    float tot=Activatedcredits - val;
                                    Toast.makeText(getApplicationContext(), "Available Credits"+tot, Toast.LENGTH_LONG).show();
                                    if(tot<=0){
                                        Intent i = new Intent(TicketPurchaseActivity.this, Login.class);
                                        startActivity(i);
                                        Toast.makeText(getApplicationContext(), "Insuffient Credits!", Toast.LENGTH_LONG).show();
                                        return;
                                    }
                                    us.setPassword(password);
                                    us.setEmail(email);
                                    us.setAddress(address);
                                    us.setUsername(name);
                                    us.setActivatedcredits(tot);
                                    updateeml.child(name).setValue(us);

                                    //Toast.makeText(getApplicationContext(), "Successfully Purchase the Ticket!", Toast.LENGTH_LONG).show();
                                    //adding bus

                                    reff=FirebaseDatabase.getInstance().getReference("PassengersRoutesDetails");
                                    reff.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            if(snapshot.exists()){
                                                maxid=(snapshot.getChildrenCount());
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });

                                    dbRef= FirebaseDatabase.getInstance().getReference().child("PassengersRoutesDetails");
                                    PassengerBusses pb=new PassengerBusses();
                                    pb.setDetails(datadet);
                                    pb.setUn(name);
                                    dbRef.child(String.valueOf(maxid+2)).setValue(pb);

                                    //adding bus

                                    Intent i = new Intent(TicketPurchaseActivity.this, Issue.class);
                                    i.putExtra("data",datadet);
                                    i.putExtra("email", email);
                                    i.putExtra("name",name);
                                    startActivity(i);

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

            }
        });
    }
}