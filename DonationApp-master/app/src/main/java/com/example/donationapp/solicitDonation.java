package com.example.donationapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.icu.text.UnicodeSetSpanner;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.zip.Inflater;

public class solicitDonation extends AppCompatDialogFragment {

    private RadioGroup radioGroup;
    private RadioButton checkedRadioButton = null; // we will assign this to a checked radio button but initially it's null
    private EditText solAmt , shortBio;
    private User user;
    private String url = "https://lamp.ms.wits.ac.za/~s1832967/Solicit.php";
    Context context;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View v = inflater.inflate(R.layout.solicit_fragment,null);

        radioGroup = v.findViewById(R.id.rgSolicitItems);
        solAmt = v.findViewById(R.id.etSolicitAmt);
        shortBio = v.findViewById(R.id.etShortBio);
        context = v.getContext();

        builder.setView(v)
                .setNegativeButton("cancel",null)
                .setPositiveButton("OK",null);

        final AlertDialog mAlertDialog = builder.create();

        mAlertDialog.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(DialogInterface dialog) {

                Button b = mAlertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                b.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {

                        if(solAmt.getText().toString().trim().equals("")){
                            solAmt.setError("Please Enter the quantity you'd like to receive");
                            return;
                        }
                        if (shortBio.getText().toString().trim().equals("")){
                            shortBio.setError("Please write a short motivation");
                            return;
                        }
                        int amt = Integer.parseInt(solAmt.getText().toString().trim());
                        String bio = shortBio.getText().toString().trim();
                        solicit(mAlertDialog ,v , amt ,bio);

                    }
                });
            }


        });
        return  mAlertDialog;



    }

    private void solicit(final AlertDialog mAlertDialog, View v, int amt,String bio) {
        String item = checkSelectedRadioButton(v);
        int amount = amt;


        ContentValues cv = new ContentValues();
        cv.put("user" , user.getId());
        cv.put("item" , item);
        cv.put("amount",amount);
        cv.put("bio", bio);


        @SuppressLint("StaticFieldLeak")
        AsyncHTTPPost asyncHTTPPost = new AsyncHTTPPost(url , cv) {

            @Override
            protected void onPostExecute(String output) {
                if(output.equals("1")){
                    mAlertDialog.dismiss();
                }
                else {
                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        };

        asyncHTTPPost.execute();
    }



    public String checkSelectedRadioButton(View v){
        int radioButtonID = radioGroup.getCheckedRadioButtonId();
        checkedRadioButton = (RadioButton) v.findViewById(radioButtonID);
        return getItem(checkedRadioButton.getText().toString());
    }

    public void setUser(User user){
        this.user = user;
    }

    private String getItem(String doItem) {
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
}
