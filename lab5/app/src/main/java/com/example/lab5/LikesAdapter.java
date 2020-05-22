package com.example.lab5;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class LikesAdapter extends RecyclerView.Adapter<LikesAdapter.ItemViewHolder> {
    private Context context;
    private List<Photos> list;
    private ItemViewHolder viewHolder;

    public LikesAdapter(Context context, List<Photos> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public LikesAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.like_view_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        final Photos photo = list.get(position);
        viewHolder = (ItemViewHolder) holder;
        String imageUrl = photo.getImageUrl();
        Glide.with(context)
                .load(imageUrl)
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .into(viewHolder.imageView);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.liked);
        }
    }
}
