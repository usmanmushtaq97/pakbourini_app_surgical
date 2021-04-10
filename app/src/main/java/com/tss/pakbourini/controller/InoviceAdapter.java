package com.tss.pakbourini.controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tss.pakbourini.DBhelperclasses.CartsTableModel;
import com.tss.pakbourini.DBhelperclasses.DataBaseCarts;
import com.tss.pakbourini.R;

import java.util.List;

public class InoviceAdapter extends RecyclerView.Adapter< InoviceAdapter.MyViewHolder > {
    Context mContex;
    List< CartsTableModel > mList;
    DataBaseCarts dataBase;
    View view;

    public InoviceAdapter(Context mCintex, List< CartsTableModel > mList) {
        this.mContex = mCintex;
        this.mList = mList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(mContex).inflate(R.layout.invoiceitems, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        dataBase = DataBaseCarts.getInstance(mContex);
        CartsTableModel rowp = mList.get(position);
        holder.mCartsProductstitle.setText(rowp.getMproductname());
        holder.sub_price_tv.setText(String.valueOf(rowp.getMprice()));
        holder.mCarts_item_Quantity.setText(String.valueOf(rowp.getMqunatity_product()));
    }

    // this method count the size of adapter
    @Override
    public int getItemCount() {

        return mList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mCartsProductstitle;
        TextView sub_price_tv;
        TextView mCartsPrice_item;
        TextView mCarts_item_Quantity;
        TextView tv_sr_no;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_sr_no = itemView.findViewById(R.id.tv_sr_id);
            mCartsProductstitle = itemView.findViewById(R.id.tv_item_id);
            sub_price_tv = itemView.findViewById(R.id.tv_subtotal_id);
            mCartsPrice_item = itemView.findViewById(R.id.tv_price_id);
            mCarts_item_Quantity = itemView.findViewById(R.id.tv_qty_id);
        }
    }
}

