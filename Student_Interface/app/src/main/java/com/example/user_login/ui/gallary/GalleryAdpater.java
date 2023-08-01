package com.example.user_login.ui.gallary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.user_login.R;

import java.util.ArrayList;
import java.util.List;

public class GalleryAdpater extends RecyclerView.Adapter<GalleryAdpater.GalleryViewAdpater> {

    private Context context;
    private List<String> images;

    public GalleryAdpater(Context context, List<String> images) {
        this.context = context;
        this.images = images;
    }

    @NonNull
    @Override
    public GalleryViewAdpater onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gallery_image,parent,false);
        return new GalleryViewAdpater(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryViewAdpater holder, int position) {

        Glide.with(context).load(images.get(position)).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public class GalleryViewAdpater extends RecyclerView.ViewHolder {


        ImageView imageView;
        public GalleryViewAdpater(@NonNull View itemView) {


            super(itemView);
            imageView=itemView.findViewById(R.id.image);
        }
    }
}
