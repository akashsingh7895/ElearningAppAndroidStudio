package com.example.hathraswarrior.classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hathraswarrior.R;

import java.util.List;

public class CommonAdapter extends RecyclerView.Adapter<CommonAdapter.ViewHolder> {


    List<CommonModel> list;

    public CommonAdapter(List<CommonModel> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.common_purched_item_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.setData(list.get(position).getProductTitle(),
                list.get(position).getProductImage(),
                list.get(position).getProductSubtitle());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTv,subtitleTv;
        public ImageView productImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            titleTv = itemView.findViewById(R.id.product_title);
            subtitleTv = itemView.findViewById(R.id.product_subtitle);
            productImage = itemView.findViewById(R.id.product_image);
        }
        public void setData(String title,String image,String subtitle){
            titleTv.setText(title);
            subtitleTv.setText(subtitle);
            Glide.with(itemView.getContext()).load(image).into(productImage);

        }
    }
}
