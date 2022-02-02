package com.example.hathraswarrior.classes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.hathraswarrior.R;

import java.util.List;

public class PostersAdapter extends PagerAdapter {

    private List<String> list;

    public PostersAdapter(List<String> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
       View view = LayoutInflater.from(container.getContext()).inflate(R.layout.posters_banner_layout,container,false);

        ImageView posterImageView = view.findViewById(R.id.posters_image);
        Glide.with(container.getContext()).load(list.get(position)).into(posterImageView);
        container.addView(view,0);

       return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
       container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view ==object;
    }
}
