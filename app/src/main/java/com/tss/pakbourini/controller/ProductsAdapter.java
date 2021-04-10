
package com.tss.pakbourini.controller;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tss.pakbourini.Activity.ItemDetails;
import com.tss.pakbourini.R;
import com.tss.pakbourini.model.FoodItem;

import java.util.List;

import spencerstudios.com.bungeelib.Bungee;

public class ProductsAdapter extends RecyclerView.Adapter< ProductsAdapter.ViewHolder> {

    private Context mContext;
    private List<FoodItem> mFoodItems;
    public ProductsAdapter(Context context, List<FoodItem> foodItems) {
        this.mContext = context;
        this.mFoodItems = foodItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_main, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FoodItem foodItem = mFoodItems.get(position);
        if(position%2==0) {
            holder.mListBG.setBackgroundResource(R.drawable.list_main_bg);
        }else {
            holder.mListBG.setBackgroundResource(R.drawable.list_main_bg2);
        }
      ///  holder.mListImage.setBackgroundResource(Integer.valueOf(foodItem.getImage()));
        Glide.with(mContext).load(foodItem.getImage()).into(holder.mListImage);
        holder.mListTitle.setText(foodItem.getTitle());
        holder.mListRating.setText(String.valueOf(foodItem.getRating()));
        holder.mListPrice.setText(String.valueOf(foodItem.getPrice()+"Rs"));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,ItemDetails.class);
                intent.putExtra("pid",foodItem.getId());
                intent.putExtra("name",foodItem.getTitle());
                intent.putExtra("descriptions",foodItem.getDescriptions());
                intent.putExtra("price",foodItem.getPrice());
                intent.putExtra("ratings",foodItem.getRating());
                intent.putExtra("min",foodItem.getMin());
                intent.putExtra("image_url",foodItem.getImage());
                Toast.makeText(mContext, ""+foodItem.getTitle(), Toast.LENGTH_SHORT).show();
                mContext.startActivity(intent);
                Bungee.slideUp(mContext);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mFoodItems.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        public LinearLayout mListBG;
     public ImageView mListImage;
     public TextView mListTitle;
        public TextView mListMins;
        public TextView mListRating;
        public TextView mListPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mListBG = itemView.findViewById(R.id.list_background);
            mListImage = itemView.findViewById(R.id.pimage);
            mListTitle = itemView.findViewById(R.id.list_title);
            mListPrice = itemView.findViewById(R.id.list_price);
            mListRating = itemView.findViewById(R.id.list_rating);

        }
    }

}
