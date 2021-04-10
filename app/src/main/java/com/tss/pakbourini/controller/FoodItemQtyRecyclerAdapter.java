package com.tss.pakbourini.controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tss.pakbourini.R;
import com.tss.pakbourini.model.FoodItemQty;

import java.util.ArrayList;

public class FoodItemQtyRecyclerAdapter extends RecyclerView.Adapter<FoodItemQtyRecyclerAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<FoodItemQty> mFoodItemQties;

    public FoodItemQtyRecyclerAdapter(Context context, ArrayList<FoodItemQty> foodItemQties) {
        mContext = context;
        mFoodItemQties = foodItemQties;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_details_qty, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        FoodItemQty foodItemQty = mFoodItemQties.get(position);
        holder.gram.setText(foodItemQty.getGram());
        holder.type.setText(foodItemQty.getType());
        holder.family.setText(foodItemQty.getFamily());

    }

    @Override
    public int getItemCount() {
        return mFoodItemQties.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
      public TextView gram, type, family;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            gram = itemView.findViewById(R.id.qty_gram);
            type = itemView.findViewById(R.id.qty_type);
            family = itemView.findViewById(R.id.qty_family);
        }
    }
}
