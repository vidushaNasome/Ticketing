package com.example.ticket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;

import com.example.ticket.Model.Guest;

import java.util.Date;
import java.util.Locale;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
//issue ticket and send email
public class GuestTicketActivity extends AppCompatActivity {
    TextToSpeech t1;
    Button btn;
    String data123;
    String email;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_ticket);

        btn=findViewById(R.id.email_txt);

        Intent i=getIntent();
        String data=i.getStringExtra("data");

        String[] parts = data.split("@");
        String part1 = parts[0];
        String part2 = parts[1];

        data123=part1;

        data123 =data123.replace(",", "");

        email=i.getStringExtra("email");
        name=i.getStringExtra("name");

        //Email and Voice recognition

        t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.US);

                }
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //voice
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {


                    t1.speak("You have Successfully Purchase the ticket "+data123 + "Enjoy your Trip",TextToSpeech.QUEUE_FLUSH,null,null);


                } else {

                }

                //Email

                try {
                    String xcon="You have successfully Purchase a ticket. Your Ticket details are shown below.\n"+name+data123;
                    sendmail(email, xcon);
                    Intent i = new Intent(GuestTicketActivity.this, BuyTicket.class);

                    startActivity(i);


                } catch (MessagingException e) {
                    e.printStackTrace();

                }

            }
        });
    }

    public void onPause(){
        if(t1 !=null){
            t1.stop();
            t1.shutdown();
        }
        super.onPause();
    }

    private void sendmail(String useremail,Object xcon) throws MessagingException {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Properties props=new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        javax.mail.Session session =  javax.mail.Session .getInstance(props,
                new javax.mail.Authenticator(){
                    protected PasswordAuthentication getPasswordAuthentication(){
                        return new PasswordAuthentication("teamblossom0@gmail.com","oshani123");
                    }
                }
        );



        Message msg=new MimeMessage(session);

        msg.setFrom(new InternetAddress("teamblossom0@gmail.com",false));
        msg.setRecipients(Message.RecipientType.TO,InternetAddress.parse(useremail));
        msg.setSubject("Ticket Issue");
        msg.setContent(xcon,"text/html");
        msg.setSentDate(new Date());
        Transport.send(msg);
    }

}