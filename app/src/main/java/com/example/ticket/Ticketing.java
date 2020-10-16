package com.example.ticket;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.example.ticket.Model.Locations;
import com.example.ticket.Model.RouteFair;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Ticketing extends AppCompatActivity {
    //private Session session;
    String name;
    TextView username,email,credits;
    String credits1,name1,email1,password1;
    String check;
    ListView list1_1;
    ArrayList<RouteFair> arrayList;
    ArrayList<String> arrayListnew;
    //ArrayList<Double> arrayListPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticketing);
        Intent i = getIntent();
        String data = i.getStringExtra("id");
        check="hi";
        final MediaPlayer mp=MediaPlayer.create(this,R.raw.processing_3);
        mp.start();
        Toast.makeText(Ticketing.this,data,Toast.LENGTH_SHORT).show();
        username = (TextView) findViewById(R.id.unid);
        email = findViewById(R.id.emailid);
        credits = findViewById(R.id.creditsId);
        list1_1= findViewById(R.id.list_route);

        //Checking ID
        try{
            DatabaseReference displayDf = FirebaseDatabase.getInstance().getReference().child("User").child(data);
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

                        if(name1==null){
                            Toast.makeText(Ticketing.this,"You don't have an Account.Please Create an Account and Try Again.",Toast.LENGTH_SHORT).show();
                            Intent in = new Intent(Ticketing.this, RegisterAct.class);
                            startActivity(in);
                        }



                    }else{

                        Toast.makeText(Ticketing.this,"You don't have an Account.Please Create an Account and Try Again.",Toast.LENGTH_SHORT).show();
                        Intent in = new Intent(Ticketing.this, RegisterAct.class);
                        startActivity(in);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }

            });
        }catch(Exception e){}

        display();

       /* Toast.makeText(Ticketing.this,"Updating Content.......................",Toast.LENGTH_SHORT).show();

        display();

        arrayListnew = new ArrayList<>();

        Toast.makeText(Ticketing.this,"Still Updating Content.................."+arrayList.size(),Toast.LENGTH_SHORT).show();

        for(RouteFair xx:arrayList){
            Toast.makeText(Ticketing.this,"inside he he he",Toast.LENGTH_SHORT).show();
            arrayListnew.add("Route ID: "+xx.getRouteId()+"\n Route: "+xx.getRoute()+"\n Fair:"+xx.getFair());
        }

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, arrayListnew);

        list1_1.setAdapter(arrayAdapter);*/


    }
    private void display() {
        arrayList = new ArrayList<>();
        Toast.makeText(Ticketing.this, "Updating Content.......................", Toast.LENGTH_SHORT).show();

            DatabaseReference getDetails = FirebaseDatabase.getInstance().getReference().child("RouteFair");

            getDetails.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    //long y = 0;
                    for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                       // y++;
                        try{
                        RouteFair loc = dsp.child("RouteFair").getValue(RouteFair.class);
                        //String xx= (String) dsp.child(String.valueOf(y)).child("RouteFair").child("Start").getValue();
                        //Toast.makeText(Ticketing.this,"rrrrrrrrr"+y+"****"+loc.getRoute(),Toast.LENGTH_SHORT).show();

                            arrayList.add(loc);
                        }catch (Exception e){}
                        //int y1 = arrayList.size();
                        //Toast.makeText(Ticketing.this, "helloooooooooo" + y1, Toast.LENGTH_SHORT).show();

                    }
                    arrayListnew = new ArrayList<>();
                    //Toast.makeText(Ticketing.this, "Still Updating Content.................." + arrayList.size(), Toast.LENGTH_SHORT).show();

                    for (RouteFair xx : arrayList) {
                        //Toast.makeText(Ticketing.this, "inside he he he", Toast.LENGTH_SHORT).show();
                        // arrayListnew.add("Route ID: " + xx.getRouteId() + "\n Route: " + xx.getRoute() + "\n Fair:-" + xx.getFair());
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

    private void displayMethod(){
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayListnew);

        list1_1.setAdapter(arrayAdapter);

        list1_1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String clickedItem=(String) list1_1.getItemAtPosition(position);
                Intent i = new Intent(Ticketing.this, TicketPurchaseActivity.class );
                i.putExtra("details",clickedItem);
                i.putExtra("un", name1);
                startActivity(i);

            }
        });

    }
}
