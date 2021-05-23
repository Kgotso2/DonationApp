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
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {

    private EditText userName , password;
    private Button login;
    private TextView forgotPass , register;
    private String loginUrl = "https://lamp.ms.wits.ac.za/~s1832967/login.php";
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        getSupportActionBar().hide();
        userName = findViewById(R.id.etUserName);
        password = findViewById(R.id.etPass);
        login = findViewById(R.id.btnLogin);
        forgotPass = findViewById(R.id.tvForgotPass);
        register = findViewById(R.id.tvRegister);

        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Login.this, "This feature will be added soon",Toast.LENGTH_SHORT).show();;
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logingIn();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this,Register.class));
            }
        });

    }

    private void logingIn() {

        String userN = userName.getText().toString().trim();
        String pass = password.getText().toString().trim();

        if(userN.equals("")){
            userName.setError("Please Enter a User name");
            return;
        }
        if (pass.equals("")){
            password.setError("Please Enter Password");
            return;
        }

        ContentValues cv = new ContentValues();
        cv.put("username",userN);
        cv.put("password",pass);


        @SuppressLint("StaticFieldLeak")
        AsyncHTTPPost asyncHTTPPost = new AsyncHTTPPost(loginUrl,cv) {

            @Override
            protected void onPostExecute(String output) {
                if(output.equals("!exists")){

                    Toast.makeText(context, "Login Failed, Please check credentials", Toast.LENGTH_SHORT).show();
                }
                else{
                    try {
                        // Only userID and balance is an integer


                        final JSONObject userJO = new JSONArray(output).getJSONObject(0); // JO in userJO for JSONObject
                        String userID =Integer.toString( userJO.getInt("UP_StudentNO"));
                        String Name = userJO.getString("UP_StudentName");
                        String Surname = userJO.getString("UP_StudentLName");
                        String contact = userJO.getString("UP_StudentContact");
                        int itemsDonated = userJO.getInt("UP_NumOfItemsDonated");

                        User user = new User(userID , Name , Surname ,contact , itemsDonated );
                        Intent intent = new Intent(context , MainPage.class);
                        intent.putExtra("user",user);
                        startActivity(intent);


                    }catch (JSONException e){
                        e.printStackTrace();

                    }
                }
            }
        };

        asyncHTTPPost.execute();



    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();

    }
}
