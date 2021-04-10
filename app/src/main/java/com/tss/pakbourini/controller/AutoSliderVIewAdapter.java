package com.tss.pakbourini.controller;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.tss.pakbourini.R;
import com.tss.pakbourini.model.SliderItem;
import com.smarteist.autoimageslider.SliderViewAdapter;
import java.util.List;

public class AutoSliderVIewAdapter extends
        SliderViewAdapter<AutoSliderVIewAdapter.SliderAdapterVH> {
    private final Context context;
    private List< SliderItem > mSliderItems;

    public AutoSliderVIewAdapter(Context context, List<SliderItem> mSliderItems) {
        this.context = context;
        this.mSliderItems = mSliderItems;
    }

    public void renewItems(List<SliderItem> sliderItems) {
        this.mSliderItems = sliderItems;
        notifyDataSetChanged();
    }

    public void deleteItem(int position) {
        this.mSliderItems.remove(position);
        notifyDataSetChanged();
    }

    public void addItem(SliderItem sliderItem) {
        this.mSliderItems.add(sliderItem);
        notifyDataSetChanged();
    }

    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_slider_layout_item, null);
        return new SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, final int position) {

        SliderItem sliderItem = mSliderItems.get(position);
        Glide.with(context)
                .load(sliderItem.getImageUrl())
                .into(viewHolder.imageViewBackground);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ///  Toast.makeText(context, "T " + position, Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public int getCount() {
        //slider view count could be dynamic size
        return mSliderItems.size();
    }
    class SliderAdapterVH extends SliderViewAdapter.ViewHolder {
        View itemView;
        ImageView imageViewBackground;
        public SliderAdapterVH(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.image_slider);
            this.itemView = itemView;
        }
    }

}