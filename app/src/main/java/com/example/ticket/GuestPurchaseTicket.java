package com.example.ticket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ticket.Model.RouteFair;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
//select journey information
public class GuestPurchaseTicket extends AppCompatActivity {
    TextView username,email;
    String name1,email1;
    String check;
    ListView list1_1;
    ArrayList<RouteFair> arrayList;
    ArrayList<String> arrayListnew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_purchase_ticket);
        //Action bar title
        getSupportActionBar().setTitle("Purchase Details");

        Intent i = getIntent();
        String data = i.getStringExtra("userName");
        check="hi";

        final MediaPlayer mp=MediaPlayer.create(this,R.raw.processing_3);
        mp.start();

        Toast.makeText(GuestPurchaseTicket.this,data,Toast.LENGTH_SHORT).show();

        //id
        username = (TextView) findViewById(R.id.uname);
        email = findViewById(R.id.emailid);
        list1_1= findViewById(R.id.list_route);

        //Checking Name
        try{
            DatabaseReference displayDf = FirebaseDatabase.getInstance().getReference().child("Guest").child(data);
            displayDf.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.hasChildren()) {

                        name1 = dataSnapshot.child("username").getValue().toString();
                        email1 = dataSnapshot.child("email").getValue().toString();
                        username.setText(name1);
                        email.setText(email1);

                        if(name1==null){
                            Toast.makeText(GuestPurchaseTicket.this,"You don't have an Account.Please Create an Account and Try Again.",Toast.LENGTH_SHORT).show();
                            Intent in = new Intent(GuestPurchaseTicket.this, GuestAccount.class);
                            startActivity(in);
                        }
                    }else{

                        Toast.makeText(GuestPurchaseTicket.this,"You don't have an Account.Please Create an Account and Try Again.",Toast.LENGTH_SHORT).show();
                        Intent in = new Intent(GuestPurchaseTicket.this, GuestAccount.class);
                        startActivity(in);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }

            });
        }catch(Exception e){}

        display();
    }
    private void display() {
        arrayList = new ArrayList<>();
        Toast.makeText(GuestPurchaseTicket.this, "Updating Content.......................", Toast.LENGTH_SHORT).show();

        DatabaseReference getDetails = FirebaseDatabase.getInstance().getReference().child("RouteFair");

        getDetails.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    try{
                        RouteFair loc = dsp.child("RouteFair").getValue(RouteFair.class);
                        arrayList.add(loc);
                    }catch (Exception e){}
                }
                arrayListnew = new ArrayList<>();
                //Toast.makeText(Ticketing.this, "Still Updating Content.................." + arrayList.size(), Toast.LENGTH_SHORT).show();

                for (RouteFair xx : arrayList) {
                    try {
                        arrayListnew.add(xx.getRoute() + "@Route ID: " + xx.getRouteId() + " " + "\n Fair:!" + xx.getFair());
                    }catch (Exception e){

                    }

                }

                displayMethod();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    //display details in list view
    private void displayMethod(){
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayListnew);

        list1_1.setAdapter(arrayAdapter);

        list1_1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String clickedItem=(String) list1_1.getItemAtPosition(position);
                Intent i = new Intent(GuestPurchaseTicket.this, GuestPayment.class );
                i.putExtra("details",clickedItem);
                i.putExtra("un", name1);
                startActivity(i);

            }
        });

    }
}