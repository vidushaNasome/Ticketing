package com.example.ticket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


//Main Starting class
public class StartActivity extends AppCompatActivity {

    Button psgL,submit;
    TextView name;
    EditText pw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        //Initializing Variables
        psgL=findViewById(R.id.normal_login_bt);
        submit=findViewById(R.id.bussubmit);
        name=findViewById(R.id.busname);
        pw=findViewById(R.id.busPassword);
    }

    @Override
    protected void onResume() {
        super.onResume();

        //Button Click events
        psgL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(StartActivity.this, Login.class);
                startActivity(i);
            }
        });

        //Submit Button

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String n=name.getText().toString();
                String p=pw.getText().toString();
                if(n.equals("ccc") && p.equals("12")){
                    Intent i = new Intent(StartActivity.this, MainActivity.class);
                    startActivity(i);
                }else{
                    Toast.makeText(getApplicationContext(), "Invalid  ID or Password", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}