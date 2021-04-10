package com.tss.pakbourini.controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tss.pakbourini.DBhelperclasses.CartsTableModel;
import com.tss.pakbourini.DBhelperclasses.DataBaseCarts;
import com.tss.pakbourini.R;


import java.util.List;

public class CartsAdapter extends RecyclerView.Adapter<CartsAdapter.MyViewHolder> {
    Context mContex;
    List< CartsTableModel > mList;
    DataBaseCarts dataBase;
    int mQuantity;
    View view;
    public int listcount;
    TextView textcount;


    public CartsAdapter(Context mCintex, List<CartsTableModel> mList) {
        this.mContex = mCintex;
        this.mList = mList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(mContex).inflate(R.layout.cartsitems, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  MyViewHolder holder,  int position) {
        //dataBase = DataBaseCarts.getInstance(mContex);
         CartsTableModel rowp = mList.get(position);
        Glide.with(mContex).load(rowp.getPimagepath()).into(holder.mCarts_ImageView);
        holder.mCartsProductstitle.setText(rowp.getMproductname());
        holder.mCartsSize_item.setText(rowp.getMp_size());
        holder.mCartsPrice_item.setText(String.valueOf(rowp.getMprice()));
        holder.mCarts_item_Quantity.setText(String.valueOf(rowp.getMqunatity_product()));
        // holder.mCarts_item_Quantity.setText(String.valueOf(rowp.getMcartsid()));

    }

    // this method count the size of adapter
    @Override
    public int getItemCount() {

        return mList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mCartsProductstitle;
        ImageView mCarts_ImageView;
        TextView singleprice_tv;
        TextView mCartsSize_item;
        TextView mCartsPrice_item;
        TextView mCarts_item_Quantity;
        Button mCartDelete_item, mCartUpdtae_item;
        Button mIncreament_item_bt, mDecreament_item_bt;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mCarts_ImageView = itemView.findViewById(R.id.carts_Image);
            mCartsProductstitle = itemView.findViewById(R.id.carts_titleproduct_tv);
            mCartsSize_item = itemView.findViewById(R.id.carts_size_tv);
            mCartsPrice_item = itemView.findViewById(R.id.carts_price_tv);
            mCartDelete_item = itemView.findViewById(R.id.deletebtn);
            mCarts_item_Quantity = itemView.findViewById(R.id.quantity_products);
        }
    }

    // make method for count list size it change when delete
    public void Countitem(TextView textView) {
        textcount = textView;
        if (mList == null) {
            textView.setText("Your Carts is Empty");

        } else {
            listcount = mList.size();
            textView.setText(String.valueOf(listcount) + " Items Adeded!");
        }
    }

    // delete carts items
    private void DeleteCartsItem() {

    }
/// it now show empty card when the item is empty
    public void ViabilityCarts(CardView linearLayout, LinearLayout layout) {
        if (mList.size() > 0) {
            linearLayout.setVisibility(View.VISIBLE);
            layout.setVisibility(View.GONE);
        } else {
            layout.setVisibility(View.VISIBLE);
            linearLayout.setVisibility(View.GONE);

        }
    }

}

