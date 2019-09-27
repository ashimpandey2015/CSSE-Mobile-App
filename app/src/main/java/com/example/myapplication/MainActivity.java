package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private EditText usernametext;
    private EditText passwordtext;
    private TextView Timetext;

    private Button loginb;

    private String id, password;
    public long MillisecondTime, StartTime, TimeBuff, UpdateTime = 0L;
    public Handler handler;
    public int Seconds, Minutes, MilliSeconds;

    private FirebaseDatabase dbMng = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginpage();

        usernametext = (EditText) findViewById(R.id.uidTV);
        passwordtext = (EditText) findViewById(R.id.pswdTV);
        handler = new Handler();

        Button BtnLogin = (Button) findViewById(R.id.signupBtn);

        BtnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,SignUp.class);
                startActivity(i);
            }

        });
    }

    public void loginpage() {
        loginb = (Button) findViewById(R.id.loginBtn);

        loginb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                readID();
                readpass();
                Timetext = (TextView) findViewById(R.id.timeTxt);
                StartTime = SystemClock.uptimeMillis();
                handler.postDelayed(runnable, 0);
            }
        });
    }

    public void readID() {
        String inputuser;
        inputuser = usernametext.getText().toString();
        DatabaseReference ref = dbMng.getReference("Login");
        DatabaseReference refM = ref.child(inputuser);
        DatabaseReference refM2 = refM.child("id");


// Attach a listener to read the data at our posts reference
        refM2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                id = dataSnapshot.getValue(String.class);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void readpass() {
        String inputuser;
        inputuser = usernametext.getText().toString();
        DatabaseReference ref = dbMng.getReference("Login");
        DatabaseReference refM = ref.child(inputuser);
        DatabaseReference refM2 = refM.child("password");


// Attach a listener to read the data at our posts reference
        refM2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                password = dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void ifstate() {
        if (passwordtext.getText().toString().equals(password) && usernametext.getText().toString().equals(id)) {
            Intent loginpage = new Intent(MainActivity.this, Home.class);
            startActivity(loginpage);

        } else {
            Toast.makeText(MainActivity.this, "UserID or Password not correct",
                    Toast.LENGTH_SHORT).show();


        }
    }

    public Runnable runnable = new Runnable() {


        @Override
        public void run() {

            MillisecondTime = SystemClock.uptimeMillis() - StartTime;

            UpdateTime = TimeBuff + MillisecondTime;

            Seconds = (int) (UpdateTime / 1000);

            Minutes = Seconds / 60;

            Seconds = Seconds % 60;

            MilliSeconds = (int) (UpdateTime % 1000);

            Timetext.setText("" + Minutes + ":"
                    + String.format("%02d", Seconds));


            handler.postDelayed(this, 0);
            if(Seconds==5){
                ifstate();
                handler.removeCallbacks(runnable);
                Timetext.setText("00:00");
            }



        }
    };



}