package com.example.donationapp;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.icu.text.UnicodeSetSpanner;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RecvAdapter extends RecyclerView.Adapter<RecvAdapter.RecvViewHolder> {

    public ArrayList<recversItem> list ;
    public Context context;
    public User user;
    private String itemsID;
    private String ReturnAmtUrl = "https://lamp.ms.wits.ac.za/~s1832967/ReturnAmt.php";
    private int Have_amt ;


    public class RecvViewHolder extends RecyclerView.ViewHolder {

        public TextView recvID , shortBio ,itemsRequest;
        public Button donate;
        public ImageView inc , dec;
        public TextView amount , contact;


        public RecvViewHolder(@NonNull View itemView) {
            super(itemView);
            amount = itemView.findViewById(R.id.tvNumOfItems);
            inc = itemView.findViewById(R.id.ivIncrease);
            dec = itemView.findViewById(R.id.ivDecrease);
            recvID = itemView.findViewById(R.id.tvRecvID);
            shortBio = itemView.findViewById(R.id.tvShortBio);
            itemsRequest = itemView.findViewById(R.id.tvItemsReq); // to be a number
            donate = itemView.findViewById(R.id.btnRecvDonate);
            contact = itemView.findViewById(R.id.tvContact);

        }
    }

    public RecvAdapter(ArrayList<recversItem> list , Context context , User user, String itemsID){
        this.list = list;
        this.context = context;
        this.user= user;
        this.itemsID = itemsID;
    }

    @NonNull
    @Override
    public RecvViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cv_recv_don,parent,false);
        return new RecvViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecvViewHolder holder, int position) {

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final RecvViewHolder holder, final int position, @NonNull final List<Object> payloads) {
        final recversItem item = new recversItem(list.get(position).getRecvID(),list.get(position).getShortBio(),list.get(position).getItemRequest(), list.get(position).getPnum());

        holder.recvID.setText("Receiver's ID: " + list.get(position).getRecvID());
        holder.shortBio.setText(list.get(position).getShortBio());
        holder.itemsRequest.setText(Integer.toString(list.get(position).getItemRequest()));
        holder.amount.setText(Integer.toString(item.getAmount()));
        holder.contact.setText(list.get(position).getPnum());



        holder.inc.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if(item.getItemRequest()> item.getAmount()) {
                    item.incAmount();
                    holder.amount.setText(Integer.toString(item.getAmount()));
                }
                else {
                    Toast.makeText(context,"can't donate more than the person needs",Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.dec.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                item.decAmount();
                holder.amount.setText(Integer.toString(item.getAmount()));
            }
        });

        holder.donate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ContentValues cv = new ContentValues();
                cv.put("user",user.getId());
                cv.put("item",itemsID);

                @SuppressLint("StaticFieldLeak")
                AsyncHTTPPost asyncHTTPPost = new AsyncHTTPPost(ReturnAmtUrl , cv) {

                    @Override
                    protected void onPostExecute(String output) {
                        int amtHave = 0;
                        int amtToDonate = item.getAmount();
                        int needAmt = item.getItemRequest();

                        try {
                            JSONArray array = new JSONArray(output);
                            JSONObject a = array.getJSONObject(0);
                            amtHave = Integer.parseInt(a.getString("Have_amt"));


                            if(amtHave < item.getAmount()){
                                Toast.makeText(context , "You only have "+ amtHave +" of this item , You can't donate what you don't have" , Toast.LENGTH_SHORT).show();
                                return;
                            }
                            if(item.getAmount() == 0){
                                return;
                            }
                            amtHave -= amtToDonate;
                            needAmt -= amtToDonate;
                            item.setItemRequest(needAmt);
                            item.setAmount(0);
                            holder.amount.setText(Integer.toString(0));
                            holder.itemsRequest.setText(Integer.toString(item.getItemRequest()));

                            ContentValues contentValues = new ContentValues();
                            contentValues.put("userID",user.getId());
                            contentValues.put("recvID",item.getRecvID());
                            contentValues.put("amtToDonate", amtToDonate);
                            contentValues.put("item",itemsID);

                            String url = "https://lamp.ms.wits.ac.za/~s1832967/Calculate.php";

                            AsyncHTTPPost post = new AsyncHTTPPost(url , contentValues) {
                                @Override
                                protected void onPostExecute(String output) {
                                    if(output.equals("1")){
                                        Toast.makeText(context, "Thank you for donating", Toast.LENGTH_SHORT).show();
                                    }
                                    else{
                                        Toast.makeText(context, "something went wrong",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            };
                            post.execute();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                };
                asyncHTTPPost.execute();
            }
        });


    }



    @Override
    public int getItemCount() {
        return list.size();
    }


}
