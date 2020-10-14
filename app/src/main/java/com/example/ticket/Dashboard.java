package com.example.ticket;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class Dashboard extends AppCompatActivity {
    Button addr,pckageAct,main;
    Session session;
    public String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        session = new Session(getApplication());
        username=session.getusename();



        addr=(Button) findViewById(R.id.addroutes);
        pckageAct=(Button)findViewById(R.id.pckAc);
        main=(Button)findViewById(R.id.mm);
    }

    @Override
    protected void onResume() {
        super.onResume();

        addr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(username.equals("admin")) {
                    Intent i = new Intent(Dashboard.this, GetLocation.class);
                    startActivity(i);
                }else {
                    Toast.makeText(getApplicationContext(), "You Dont have ADMIN priviledges!", Toast.LENGTH_LONG).show();
                }

            }
        });

        pckageAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Dashboard.this, ActivatePackage.class);
                startActivity(i);
            }
        });
        main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Dashboard.this, Login.class);
                startActivity(i);
            }
        });
    }
}
