package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {


    EditText Name, ID,Password, Email;
    Button signUp;
    DatabaseReference reff;
    SignUpSchema schema;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Name = findViewById(R.id.nameTxt);
        ID = findViewById(R.id.idTxt);
        Password = findViewById(R.id.passwordTxt);
        Email = findViewById(R.id.emailTxt);
        signUp = findViewById(R.id.SignUp);
        schema = new SignUpSchema();
        reff = FirebaseDatabase.getInstance().getReference().child("Login");

        signUp.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                schema.setName(Name.getText().toString().trim());
                schema.setId(ID.getText().toString().trim());
                schema.setEmail(Email.getText().toString().trim());
                schema.setPassword(Password.getText().toString().trim());

                reff.child(ID.getText().toString().trim()).setValue(schema);
                Toast.makeText(SignUp.this,"data inserted successfully", Toast.LENGTH_LONG).show();
            }
        });

    }
}
