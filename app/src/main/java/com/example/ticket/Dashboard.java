package com.example.ticket;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class Dashboard extends AppCompatActivity {
    Button addr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        addr=(Button) findViewById(R.id.addroutes);

    }

    @Override
    protected void onResume() {
        super.onResume();

        addr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Dashboard.this, GetLocation.class);
                startActivity(i);
            }
        });
    }
}
