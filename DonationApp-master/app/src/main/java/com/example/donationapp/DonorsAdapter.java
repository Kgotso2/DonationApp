package com.example.donationapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DonorsAdapter extends RecyclerView.Adapter<DonorsAdapter.donorsViewHolder> {

    public ArrayList<Donor> donorList;
    public Context context;

    public DonorsAdapter(ArrayList<Donor> list , Context c) {
        this.donorList = list;
        this.context = c;
    }

    @NonNull
    @Override
    public donorsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.cv_donors,parent,false);
        donorsViewHolder  donorsViewHolder = new donorsViewHolder(v);
        return donorsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull donorsViewHolder holder, int position) {
        Donor donor = donorList.get(position);
        holder.donorsName.setText(donor.getDonor());
        holder.donateAmt.setText(donor.getDonatedAmt());
    }

    @Override
    public int getItemCount() {
        return donorList.size();
    }

    public class donorsViewHolder extends RecyclerView.ViewHolder {
        TextView donorsName , donateAmt;

        public donorsViewHolder(@NonNull View itemView) {
            super(itemView);
            donorsName = itemView.findViewById(R.id.tvDonorName);
            donateAmt = itemView.findViewById(R.id.tvNumOfItemsDonated);
        }
    }




}
