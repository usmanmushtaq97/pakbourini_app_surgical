package com.tss.pakbourini.controller;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tss.pakbourini.Activity.ItemsByCategory;
import com.tss.pakbourini.R;
import com.tss.pakbourini.model.CategoryModel;
import java.util.List;
import spencerstudios.com.bungeelib.Bungee;

public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.MyViewHolder> {
    private final Context mContex;
    private final List< CategoryModel > mList;
    public SubCategoryAdapter(Context mCintex, List<CategoryModel> mList) {
        this.mContex = mCintex;
        this.mList = mList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContex).inflate(R.layout.subcategoryitems, parent, false);
        return new MyViewHolder(view);
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.subtitleid);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        CategoryModel rowp = mList.get(position);
        holder.textViewTitle.setText(rowp.getName());
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(mContex , ItemsByCategory.class);
            intent.putExtra("id",rowp.getId());
            intent.putExtra("name",rowp.getName());
            mContex.startActivity(intent);
            Bungee.slideUp(mContex);
        });

    }
    @Override
    public int getItemCount() {
        return mList.size();
    }
}
