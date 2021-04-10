package com.tss.pakbourini.controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.tss.pakbourini.R;
import com.tss.pakbourini.model.NotificationModels;
import java.util.List;

public class NotificationsAdpater extends RecyclerView.Adapter<NotificationsAdpater.MyViewHolder> {
     Context mContex;
        List< NotificationModels > mList;
    public NotificationsAdpater(Context mCintex, List<NotificationModels> mList) {
        this.mContex = mCintex;
        this.mList = mList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContex).inflate(R.layout.notificationitmes, parent, false);
        return new MyViewHolder(view);
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle,descreptiontitle;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.ordertitle);
            descreptiontitle = itemView.findViewById(R.id.minidecreptions);
        }
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
       NotificationModels rowp = mList.get(position);
        holder.textViewTitle.setText(rowp.getTitle());
        holder.descreptiontitle.setText(rowp.getDescriptions());
    }
    @Override
    public int getItemCount() {
        return mList.size();
    }


}
