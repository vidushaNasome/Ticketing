package com.example.ticket;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {
    private Session session;

    TextView un;
    TextView pw;

    String name;
    String password;

    Button login;
    Button register,main_nav;

    String un_1;
    String pw_1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        isOnline();

        session = new Session(getApplicationContext());

        un=findViewById(R.id.un_log);
        pw=findViewById(R.id.pw_log);
        login=findViewById(R.id.login_log);
        register=findViewById(R.id.reg_log);
        main_nav=findViewById(R.id.main_nav);
    }
    @Override
    protected void onResume() {
        super.onResume();
        main_nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Login.this, MainActivity.class);
                startActivity(i);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                isOnline();
                un_1=un.getText().toString();
                pw_1=pw.getText().toString();


                DatabaseReference logindf = FirebaseDatabase.getInstance().getReference().child("User").child(un_1);

                logindf.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        try {
                            if (dataSnapshot.hasChildren()) {
                                name = dataSnapshot.child("username").getValue().toString();
                                password = dataSnapshot.child("password").getValue().toString();
                                System.out.println(name);
                                System.out.println(password);
                                System.out.println(pw_1);
                                Intent i = new Intent(Login.this, Dashboard.class);
                                if (pw_1.equals(password)) {

                                    session.setusename(un_1);

                                    i.putExtra("userNameMsg", un_1);
                                    startActivity(i);
                                } else {
                                    Toast.makeText(getApplicationContext(), "Invalid User Name or Password", Toast.LENGTH_LONG).show();


                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "Invalid User Name or Password", Toast.LENGTH_LONG).show();


                            }
                        } catch (Exception e) {
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });





            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent(Login.this, RegisterAct.class);
                startActivity(i1);
            }
        });

    }

    public boolean isOnline() {
        ConnectivityManager conMgr = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

        if(netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()){
            Toast.makeText(getApplicationContext(), "No Internet connection!", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}
