package com.example.ticket;


import android.location.Address;
import android.location.Geocoder;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.ticket.Model.Locations;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;


public class GetLocation extends AppCompatActivity {

    SupportMapFragment supportMapFragment;
    FusedLocationProviderClient client;
    Button mainmenu, cl, ADDLOC;
    String location123;
    DatabaseReference dbRef;
    Locations location_adding;
    double[] arr;
    ListView list1;
    ArrayList<String> arrayList;
    DatabaseReference reff;
    long maxid=0;
    long maxid1=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_location);
        list1= findViewById(R.id.list);
        location_adding =new Locations();

        mainmenu = findViewById(R.id.button);
        cl = findViewById(R.id.button1);
        ADDLOC = findViewById(R.id.button7);
        ADDLOC.setVisibility(View.GONE);

        display();

        supportMapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.google_map);

        client = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(GetLocation.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //getCurrentLocation();
        } else {
            ActivityCompat.requestPermissions(GetLocation.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }




        for(String x:arrayList){
            Toast.makeText(getApplicationContext(), "craz22222222222y"+x, Toast.LENGTH_LONG).show();
        }
       // arrayList = display();
        /*arrayList.add("aaa");
        arrayList.add("bbb");*/
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, arrayList);
        list1.setAdapter(arrayAdapter);


    }

    @Override
    protected void onResume() {
        super.onResume();

        mainmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(GetLocation.this, Dashboard.class);
                startActivity(i);
            }
        });

        cl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCurrentLocation();
                //Toast.makeText(getApplicationContext(), "Address:123"+arr[0]+"****"+arr[1], Toast.LENGTH_LONG).show();
                //String add=getAddress(arr[0],arr[1]);
                //Toast.makeText(getApplicationContext(), "Address:"+add, Toast.LENGTH_LONG).show();
            }
        });

    }

    private void getCurrentLocation() {
          arr = new double[2];
        //Toast.makeText(getApplicationContext(), "crazy", Toast.LENGTH_LONG).show();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        Task<Location> task = client.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(final Location location) {
                if(location != null){
                    //Toast.makeText(getApplicationContext(), "crazy1", Toast.LENGTH_LONG).show();
                    supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(GoogleMap googleMap) {
                            LatLng latLng=new LatLng(location.getLatitude(),location.getLongitude());
                            //Toast.makeText(getApplicationContext(), "crazy2", Toast.LENGTH_LONG).show();
                            arr[0]=location.getLatitude();
                            arr[1]=location.getLongitude();
                            //Toast.makeText(getApplicationContext(), "Address inside "+arr[0]+"****"+arr[1], Toast.LENGTH_LONG).show();
                            MarkerOptions options=new MarkerOptions().position(latLng)
                                    .title("I AM HERE");
                            final String add=getAddress(arr[0],arr[1]);
                            Toast.makeText(getApplicationContext(), "Address:"+add, Toast.LENGTH_LONG).show();

                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,100));
                            googleMap.addMarker(options);
                            location123=add;
                            ADDLOC.setVisibility(View.VISIBLE);

                            reff=FirebaseDatabase.getInstance().getReference("locations");
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

                            ADDLOC.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dbRef= FirebaseDatabase.getInstance().getReference().child("locations");
                                    FirebaseDatabase database = FirebaseDatabase.getInstance();

                                    location_adding.setLocation(add);
                                    dbRef.child(String.valueOf(maxid+1)).setValue(location_adding);
                                    Toast.makeText(getApplicationContext(), "Successfully Added the location"+add, Toast.LENGTH_LONG).show();
                                    ADDLOC.setVisibility(View.GONE);

                                }
                            });



                        }
                    });
                }
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==44){
            if(grantResults.length>0&& grantResults[0]==PackageManager.PERMISSION_GRANTED){
                getCurrentLocation();
            }
        }
    }

    private String getAddress(double latitude, double longitude) {
        //Toast.makeText(getApplicationContext(), "crazy3", Toast.LENGTH_LONG).show();
        StringBuilder result = new StringBuilder();
        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses.size() > 0) {
                Address address = addresses.get(0);
                result.append(address.getPostalCode());
                result.append(address.getFeatureName());
                result.append(address.getLocality());
                result.append(address.getAdminArea());
                result.append(address.getCountryName());
            }
        } catch (IOException e) {
            Log.e("tag", e.getMessage());
        }

        return result.toString();
    }

    private void display(){
        arrayList = new ArrayList<>();
       // arrayList.removeAll(arrayList);
       // getCount();

        DatabaseReference getDetails = FirebaseDatabase.getInstance().getReference().child("locations");

        getDetails.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                long y=0;
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    y++;

                        //String loc = (String) dsp.child(String.valueOf(y)).child("location").getValue();
                        String loc = (String) dsp.child("location").getValue();
                        //Toast.makeText(getApplicationContext(), "crazyyyyyyyyyyyyyyy"+y+loc, Toast.LENGTH_LONG).show();
                        arrayList.add(loc);



                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


       // return arrayList;
    }
   /* public long getCount(){

        reff=FirebaseDatabase.getInstance().getReference("locations");
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    maxid1=(snapshot.getChildrenCount());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }*/
}
