package com.example.ticket;

import android.content.Intent;
import android.media.MediaPlayer;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class Ticketing extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticketing);
        Intent i = getIntent();
        String data = i.getStringExtra("id");

        final MediaPlayer mp=MediaPlayer.create(this,R.raw.processing_3);
        mp.start();
        Toast.makeText(Ticketing.this,data,Toast.LENGTH_SHORT).show();

    }
}
