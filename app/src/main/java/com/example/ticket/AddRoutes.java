package com.example.ticket;

import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class AddRoutes extends AppCompatActivity {
Spinner spinner1,spinner2;
TextView routeNo,fair;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_routes);
        spinner1=(Spinner)findViewById(R.id.spinner1);
        spinner2=(Spinner)findViewById(R.id.spinner2);

        routeNo=(TextView)findViewById(R.id.route_no);
        fair=(TextView) findViewById(R.id.fair);

        ArrayList<String> options=new ArrayList<String>();

        options.add("option 1");
        options.add("option 2");
        options.add("option 3");

       // use default spinner item to show options in spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,options);
        spinner1.setAdapter(adapter);
    }

}
