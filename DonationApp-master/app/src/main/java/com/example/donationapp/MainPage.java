package com.example.donationapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainPage extends AppCompatActivity {
    private ArrayList<donItem> donItemList = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private User user;
    private solicitDonation solicitDonation;
    private Addto addto;

    private Button addItems , donorList , solicitItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        user = getIntent().getParcelableExtra("user");
        addItems = findViewById(R.id.btnAddItems);
        donorList = findViewById(R.id.btnDonors);
        solicitItem = findViewById(R.id.btnSolicit);
       // Toast.makeText(this,"You are Logged in as: " + user.getName(),Toast.LENGTH_SHORT).show();

        populateList();

        recyclerView = findViewById(R.id.rvDonate);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        adapter = new ItemsAdapter(donItemList,this,user);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);



        addItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addto = new Addto();
                addto.setUser(user);
                addto.show(getSupportFragmentManager(),"");
            }
        });

        donorList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainPage.this , DonorsList.class));
            }
        });

        solicitItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                solicitDonation = new solicitDonation();
                solicitDonation.setUser(user);
                solicitDonation.show(getSupportFragmentManager() , "");
            }
        });
    }

    private void populateList() {
            donItemList.add(new donItem("Sanitary Pads", R.drawable.pads));
        donItemList.add(new donItem("Blanket",R.drawable.blanket));
        donItemList.add(new donItem("Jacket" , R.drawable.jacket));
        donItemList.add(new donItem("Boys' School Shoes", R.drawable.schoolb));
        donItemList.add(new donItem("Girls' School Shoes", R.drawable.schoolg));
        donItemList.add(new donItem("Socks", R.drawable.socks));
        donItemList.add(new donItem("Tin Stuff", R.drawable.tinstuff));
        donItemList.add(new donItem("Trousers",R.drawable.trousers));

    }


}
