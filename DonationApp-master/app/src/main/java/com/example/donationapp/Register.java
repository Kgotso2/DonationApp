package com.example.donationapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends AppCompatActivity {

    private EditText username , name , surname , password , contact;
    private Button register;
    private String registerURL = "https://lamp.ms.wits.ac.za/~s1832967/register.php";
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();
        context = this;
        username = findViewById(R.id.etRegUserName);
        name = findViewById(R.id.etRegName);
        surname = findViewById(R.id.etRegSurname);
        password = findViewById(R.id.etRegPass);
        contact = findViewById(R.id.etRegContact);
        register = findViewById(R.id.btnRegister);


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerNewUser();
            }
        });
    }

    private void registerNewUser() {
        String Username = username.getText().toString().trim();
        String Name = name.getText().toString().trim();
        String Surname = surname.getText().toString().trim();
        String Password = password.getText().toString().trim();
        String Contact  = contact.getText().toString().trim();

        if(Username.equals("") || Username.length() < 6){
            username.setError("Please Enter Your Student Number of length 6 and higher");
            return;
        }
        if(Name.equals("")){
            name.setError("Please Enter Your Name");
            return;
        }
        if (Surname.equals("")){
            surname.setError("Please Enter Surname");
            return;
        }
        if(Password.equals("")){
            password.setError("Please Enter a Strong password");
            return;
        }
        if(Contact.equals("") || Contact.length()!=10){
            contact.setError("Please Enter a 10 digit phone number");
            return;
        }

        ContentValues cv = new ContentValues();
        cv.put("number",Username);
        cv.put("password",Password);
        cv.put("name",Name);
        cv.put("surname",Surname);
        cv.put("contact",Contact);

        @SuppressLint("StaticFieldLeak")
        AsyncHTTPPost asyncHTTPPost = new AsyncHTTPPost(registerURL , cv) {

            @Override
            protected void onPostExecute(String output) {
                if(output.equals("1")){
                    startActivity(new Intent(context , Login.class));
                    finish();
                }
                else {
                    Toast.makeText(context , "Something went, try logging in this student number be registered already" ,Toast.LENGTH_LONG).show();
                }
            }
        };
        asyncHTTPPost.execute();
    }
}
