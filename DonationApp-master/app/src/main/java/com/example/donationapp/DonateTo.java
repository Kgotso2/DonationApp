package com.example.donationapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SearchRecentSuggestionsProvider;
import android.icu.text.UnicodeSetSpanner;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.appbar.CollapsingToolbarLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DonateTo extends AppCompatActivity {

    private ArrayList<recversItem> list = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private User user;
    private String doItem;
    private Context context;
    private String url = "https://lamp.ms.wits.ac.za/~s1832967/Receiver.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate_to);
        user = getIntent().getParcelableExtra("user");
        doItem = getIntent().getStringExtra("itemName");

        context = this;
        getSupportActionBar().setTitle(doItem);
        dispRecvers();


    }

    private String getItem() {
        String name ;

        switch (doItem){
            case "Sanitary Pads":
                name = "Pads";
                break;

            case "Blanket":
                name = "Blankets";
                break;

            case "Jacket":
                name = "Jacket";
                break;

            case "Boys' School Shoes":
                name = "Boys_shoes";
                break;

            case "Girls' School Shoes":
                name = "Girls_shoes";
                break;

            case "Socks":
                name = "Socks";
                break;

            case "Tin Stuff":
                name = "Tin_stuff";
                break;

            case "Trousers":
                name = "Trousers";
                break;

            default:
                name = "non";
                break;
        }

        return name;
    }

    private void dispRecvers() {
        ContentValues cv = new ContentValues();
        cv.put("item",getItem());

        @SuppressLint("StaticFieldLeak")
        AsyncHTTPPost asyncHTTPPost = new AsyncHTTPPost("https://lamp.ms.wits.ac.za/~s1832967/Receiver.php" , cv) {

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
                        int Want_amt = Integer.parseInt(obj.getString("Want_amt"));
                        String ID = obj.getString("UserID");
                        String Bio = obj.getString("shortBio");
                        String Pnum = obj.getString("UP_StudentContact");
                        if(ID.equals(user.getId())) continue;
                        recversItem recversItem = new recversItem(ID, Bio,Want_amt,Pnum);
                        list.add(recversItem);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                RecyclerView recyclerView = findViewById(R.id.rvRecv);
                RecvAdapter adapter = new RecvAdapter(list, context,user,getItem());
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
                recyclerView.setAdapter(adapter);
            }
        };
        asyncHTTPPost.execute();

    }

}
