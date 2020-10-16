package com.example.ticket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ticket.Test.EmailValidator;
import com.example.ticket.Model.Guest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GuestAccount extends AppCompatActivity {
    private Session session;

    EditText username;
    EditText email;

    String guestName;
    String guestEmail;
    Boolean connection;
    Button login;
    DatabaseReference dbRef;
    Guest guest;
    private EmailValidator mEmailValidator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_account);
        //Action bar title
        getSupportActionBar().setTitle("Guest Login");

        isOnline();

        session = new Session(getApplicationContext());

        username=findViewById(R.id.txtUsername);
        email=findViewById(R.id.txtGmail);
        login=findViewById(R.id.btnLogin);

        guest=new Guest();
        mEmailValidator = new EmailValidator();
        email.addTextChangedListener(mEmailValidator);

    }
    @Override
    protected void onResume() {
        super.onResume();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guestName=username.getText().toString();
                guestEmail=email.getText().toString();

                if(guestName.isEmpty()||guestEmail.isEmpty())
                Toast.makeText(getApplicationContext(), "Please Input Values For Empty Fields", Toast.LENGTH_LONG).show();
                else if(!mEmailValidator.isValid()){
                    email.setError("Invalid email Address");
                    Log.w("TAG", "Invalid email");
                }
                else{

                    connection = isOnline();
                    if (connection == true) {

                        DatabaseReference logindf = FirebaseDatabase.getInstance().getReference().child("Guest").child(guestName);

                        logindf.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.hasChildren()) {Toast.makeText(GuestAccount.this, "User Name Existed!", Toast.LENGTH_SHORT).show();}else{
                                    dbRef=FirebaseDatabase.getInstance().getReference().child("Guest");
                                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                                    DatabaseReference myRef = database.getReference("message");


                                    guest.setUsername(username.getText().toString().trim());
                                    guest.setEmail(email.getText().toString().trim());
                                    dbRef.child(guestName).setValue(guest);



                                    Intent i = new Intent(GuestAccount.this, GuestPurchaseTicket.class);


                                    i.putExtra("userName", guestName);

                                    session.setusename(guestName);
                                    Toast.makeText(GuestAccount.this, "You Have Login Successfully!", Toast.LENGTH_SHORT).show();
                                    LayoutInflater inflater = getLayoutInflater();
                                    View toastLayout = inflater.inflate(R.layout.successmsg, (ViewGroup) findViewById(R.id.successMsg));
                                    Toast toast = new Toast(getApplicationContext());
                                    toast.setDuration(Toast.LENGTH_LONG);
                                    toast.setView(toastLayout);
                                    toast.show();


                                    startActivity(i);

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }
                }
        });
    }
        private Boolean isOnline() {
            ConnectivityManager conMgr = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

            if(netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()){
                Toast.makeText(getApplicationContext(), "No Internet connection!", Toast.LENGTH_LONG).show();
                return false;
            }
            return true;
        }
}
