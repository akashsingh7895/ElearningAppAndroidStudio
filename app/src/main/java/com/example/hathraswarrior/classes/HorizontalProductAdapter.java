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
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class HorizontalProductAdapter extends RecyclerView.Adapter<HorizontalProductAdapter.ViewHolder> {

    private List<HorizontalItemModel> list;


    public HorizontalProductAdapter(List<HorizontalItemModel> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public HorizontalProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_product_item_layout,parent,false);
       return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HorizontalProductAdapter.ViewHolder holder, int position) {

        holder.setData(list.get(position).getProductId(),
                list.get(position).getImageResource(),
                list.get(position).getTitle(),
                list.get(position).getSubTitle(),
                list.get(position).getPrice());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        TextView titleTv,subTitleTv,priceTv;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.productImageView);
            titleTv = itemView.findViewById(R.id.titleTv);
            subTitleTv = itemView.findViewById(R.id.subTitleTv);
            priceTv = itemView.findViewById(R.id.priceTv);
        }
        public void setData(String productId,String imageResource,String title,String subTitle,String price){
            titleTv.setText(title);

            if (!title.equals("")){

                subTitleTv.setText(subTitle);
                priceTv.setText("Rs." + price +"/-");
                Glide.with(imageView.getContext()).load(imageResource).into(imageView);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        // todo: to pass identification data throw intent
                        Intent intent = new Intent(itemView.getContext(), ProductDetailActivity.class);
                        intent.putExtra("product_id",productId);
                        itemView.getContext().startActivity(intent);
                    }
                });

            }else {
                priceTv.setText(price);
            }

        }
    }
}
