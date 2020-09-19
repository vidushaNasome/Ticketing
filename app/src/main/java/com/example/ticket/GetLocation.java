package com.example.ticket;


import android.location.Address;
import android.location.Geocoder;
import android.util.Log;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class GetLocation extends AppCompatActivity {

    SupportMapFragment supportMapFragment;
    FusedLocationProviderClient client;
    Button mainmenu, cl, ADDLOC;
    String location123;
    DatabaseReference dbRef;
    Locations location_adding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_location);

        location_adding =new Locations();

        mainmenu = findViewById(R.id.button1);
        cl = findViewById(R.id.button1);
        ADDLOC = findViewById(R.id.button7);
        ADDLOC.setVisibility(View.GONE);
        supportMapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.google_map);

        client = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(GetLocation.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //getCurrentLocation();
        } else {
            ActivityCompat.requestPermissions(GetLocation.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }
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
        final double[] arr = new double[2];
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Task<Location> task = client.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(final Location location) {
                if(location != null){
                    supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(GoogleMap googleMap) {
                            LatLng latLng=new LatLng(location.getLatitude(),location.getLongitude());
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

                            ADDLOC.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dbRef= FirebaseDatabase.getInstance().getReference().child("locations");
                                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                                    //DatabaseReference myRef = database.getReference("message");

                                    location_adding.setLocation(add);
                                    dbRef.child("checking").setValue(location_adding);
                                    Toast.makeText(getApplicationContext(), "Successfully Added the location", Toast.LENGTH_LONG).show();
                                    ADDLOC.setVisibility(View.GONE);

                                }
                            });


                            //ADDLOC.setVisibility(View.GONE);
                            /*Location temp = new Location(LocationManager.GPS_PROVIDER);
                            temp.setLatitude(location.getLatitude());
                            temp.setLongitude(location.getLongitude());
                            float distance = location.distanceTo(temp);*/
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
        StringBuilder result = new StringBuilder();
        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses.size() > 0) {
                Address address = addresses.get(0);
                result.append(address.getPostalCode()).append("\n");
                result.append(address.getFeatureName()).append("\n");
                result.append(address.getLocality()).append("\n");
                result.append(address.getAdminArea()).append("\n");
                result.append(address.getCountryName());
            }
        } catch (IOException e) {
            Log.e("tag", e.getMessage());
        }

        return result.toString();
    }
}
