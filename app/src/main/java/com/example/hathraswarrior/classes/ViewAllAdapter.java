package com.example.hathraswarrior.classes;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hathraswarrior.R;
import com.example.hathraswarrior.activites.ProductDetailActivity;

import java.util.List;

public class ViewAllAdapter extends RecyclerView.Adapter<ViewAllAdapter.ViewHolder> {

    List<ViewAllModel> list;

    public ViewAllAdapter(List<ViewAllModel> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewAllAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_all_layout,parent,false);
        return new ViewHolder(view1);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewAllAdapter.ViewHolder holder, int position) {

        holder.setData(
                list.get(position).getProductId(),
                list.get(position).getProductImage(),
                list.get(position).getProductTitle(),
                list.get(position).getProductSubtitle(),
                list.get(position).getProductPrice(),
                list.get(position).getProductNormalPrice());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView titleTv,subtitleTv,priceTv,actPriceTv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.prodImageView);
            titleTv = itemView.findViewById(R.id.prodTitle);
            subtitleTv = itemView.findViewById(R.id.prodSubtitle);
            priceTv = itemView.findViewById(R.id.prodPrice);
            actPriceTv = itemView.findViewById(R.id.prodActPrice);


        }


        public void setData(String productId,String resource,String title,String subTitle,String price,String actPrice){
            titleTv.setText(title);
            subtitleTv.setText(subTitle);
            priceTv.setText("Rs." + price + "/-");
            actPriceTv.setText("Rs." + actPrice + "/-");
//            imageView.setImageDrawable(itemView.getResources().getDrawable(resource));
            Glide.with(imageView.getContext()).load(resource).into(imageView);




            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(itemView.getContext(), ProductDetailActivity.class);
                    intent.putExtra("product_id",productId );
                    itemView.getContext().startActivity(intent);
                }
            });

        }




    }
}
