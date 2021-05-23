package com.example.donationapp;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.app.INotificationSideChannel;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemViewHolder> {

    private ArrayList<donItem> itemArrayList;
    public Context context;
    public User user;

    public static class ItemViewHolder extends RecyclerView.ViewHolder{

        public ImageView img;
        public TextView name;
        public Button donate;


        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.ivItem);
            name = itemView.findViewById(R.id.tvItemTitle);
            donate = itemView.findViewById(R.id.btnDonanteItem);
        }
    }

    public ItemsAdapter(ArrayList<donItem> itemArrayList , Context context,User user){

        this.itemArrayList = itemArrayList;
        this.context = context;
        this.user = user;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cv_item,parent,false);
        ItemViewHolder itemViewHolder = new ItemViewHolder(v);
        return itemViewHolder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final ItemViewHolder holder, int position) {
        final donItem item = itemArrayList.get(position);

        holder.img.setImageResource(item.getImg());
        holder.name.setText(item.getName());

        holder.donate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DonateTo.class);
                intent.putExtra("user", user);
                intent.putExtra("itemName",item.getName());
                context.startActivity(intent);
            }
        });
    }



    @Override
    public int getItemCount() {
        return itemArrayList.size();
    }

    private String getItem(String item) {
        String name ;

        switch (item){
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
}
