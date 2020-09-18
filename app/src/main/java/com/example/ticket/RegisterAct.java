package com.example.ticket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ticket.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
public class RegisterAct extends AppCompatActivity {

    private Session session;
    String username,password,cpwd,eml,name,add;
    TextView un;
    TextView pw;
    TextView email;
    TextView cpw,address;
    Button reg,login_nav;
    Boolean conn;
    String name21="";
    DatabaseReference dbRef;
    User us;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        session = new Session(getApplicationContext());

        un=findViewById(R.id.unid_Reg);
        pw=findViewById(R.id.pw_Reg);
        reg=findViewById(R.id.reg_Reg);
        cpw=findViewById(R.id.cpw_Reg);
        email=findViewById(R.id.email_Reg);
        address=findViewById(R.id.address_Reg);
        login_nav=findViewById(R.id.login_nav);

        us=new User();
    }
    @Override
    protected void onResume() {
        super.onResume();
        login_nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RegisterAct.this, Login.class);
                startActivity(i);
            }
        });

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                username=un.getText().toString();
                password=pw.getText().toString();
                cpwd=cpw.getText().toString();
                eml=email.getText().toString();
                add=address.getText().toString();

                if(username.isEmpty()||password.isEmpty())
                    Toast.makeText(getApplicationContext(), "Please Input Values For Empty Fields", Toast.LENGTH_LONG).show();

                else if(!password.equals(cpwd))
                    Toast.makeText(getApplicationContext(),"Passwords are Mismatching", Toast.LENGTH_LONG).show();
                else if(!Patterns.EMAIL_ADDRESS.matcher(eml).matches()){
                    Toast.makeText(getApplicationContext(),"Invalid email Address", Toast.LENGTH_LONG).show();
                }
                else {
                    conn = isOnline();
                    if (conn == true) {

                        DatabaseReference logindf = FirebaseDatabase.getInstance().getReference().child("User").child(username);

                        logindf.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.hasChildren()) {Toast.makeText(RegisterAct.this, "User Name Existed!", Toast.LENGTH_SHORT).show();}else{
                                    dbRef=FirebaseDatabase.getInstance().getReference().child("User");
                                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                                    DatabaseReference myRef = database.getReference("message");


                                    us.setUsername(un.getText().toString().trim());
                                    us.setEmail(email.getText().toString().trim());
                                    us.setPassword(pw.getText().toString().trim());
                                    us.setAddress(address.getText().toString().trim());
                                    us.setActivatedcredits(0);
                                    dbRef.child(username).setValue(us);



                                    Intent i = new Intent(RegisterAct.this, Dashboard.class);


                                    i.putExtra("userName", username);

                                    session.setusename(username);
                                    Toast.makeText(RegisterAct.this, "You Have Registerd Successfully! \n Yor SMART CARD will deliver you soon", Toast.LENGTH_SHORT).show();
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
