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
import com.example.hathraswarrior.activites.CategoryActivity;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.Viewholder> {

    private List<CategoryModel>list;

    public CategoryAdapter(List<CategoryModel> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public CategoryAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.categories_item_layout,parent,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.Viewholder holder, int position) {

        holder.setData(list.get(position).getCategoryTitle(),list.get(position).getImageAddress());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        private ImageView categoryImage;
        private TextView categoryTitle;
        public Viewholder(@NonNull View itemView) {
            super(itemView);

            categoryImage = itemView.findViewById(R.id.categoryImageIcon);
            categoryTitle = itemView.findViewById(R.id.categoryTitleTv);

        }
        public void setData(String name,String imageResource){
            categoryTitle.setText(name);
            if (!name.equals("")){

                Glide.with(itemView.getContext()).load(imageResource).into(categoryImage);

                if (!(getAdapterPosition()==0)){

                    itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(itemView.getContext(), CategoryActivity.class);
                            intent.putExtra("category_name",name);
                            itemView.getContext().startActivity(intent);
                        }
                    });

                }
            }else {

            }


        }
    }
}
