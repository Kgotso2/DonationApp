package com.example.donationapp;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    private String id , name, surname , contact ;
    private int itemsDonated;

    User(String id , String name, String surname , String contact , int itemsDonated){
       this.id = id ;
       this.name = name;
       this.surname = surname;
       this.contact = contact;
       this.itemsDonated = itemsDonated;
    }


    protected User(Parcel in) {
        id = in.readString();
        name = in.readString();
        surname = in.readString();
        contact = in.readString();
        itemsDonated = in.readInt();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public int getItemsDonated() {
        return itemsDonated;
    }

    public void setItemsDonated(int itemsDonated) {
        this.itemsDonated = itemsDonated;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(surname);
        dest.writeString(contact);
        dest.writeInt(itemsDonated);
    }
}
