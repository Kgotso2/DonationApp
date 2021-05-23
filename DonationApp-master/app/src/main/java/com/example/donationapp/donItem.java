package com.example.donationapp;

import android.graphics.drawable.Drawable;

public class donItem {
    private String name;
    private int img;
    private int amount;

    donItem(String Name , int img){
        this.name = Name;
        this.img = img;
        amount = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImg() {
        return img;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void incAmount(){
        this.amount++;
    }

    public void decAmount(){
        if(amount > 0) {
            this.amount--;
        }
    }
}
