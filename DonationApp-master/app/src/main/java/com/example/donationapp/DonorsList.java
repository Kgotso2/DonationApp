package com.example.donationapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DonorsList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donors_list);

        ContentValues cv = new ContentValues();
        final ArrayList<Donor> list = new ArrayList<>();
        String url = "https://lamp.ms.wits.ac.za/~s1832967/Donors.php";
        final Context context = this;

        @SuppressLint("StaticFieldLeak")
        AsyncHTTPPost asyncHTTPPost = new AsyncHTTPPost(url , cv) {
            @Override
            protected void onPostExecute(String output) {

                JSONArray array = null;
                try {
                    array = new JSONArray(output);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                for(int i = 0; i < array.length() ; i++) {
                    try {
                        JSONObject obj = array.getJSONObject(i);

                        String name = obj.getString("UP_StudentName");
                        String amt = obj.getString("UP_NumOfItemsDonated");

                        Donor donor = new Donor(name , amt);

                        list.add(donor);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                RecyclerView recyclerView = findViewById(R.id.rvDonors);
                DonorsAdapter adapter = new DonorsAdapter(list, context);
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
                recyclerView.setAdapter(adapter);
            }

        };

        asyncHTTPPost.execute();


    }
}