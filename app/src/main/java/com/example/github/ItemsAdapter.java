package com.example.github;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.github.Models.Item;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemHolder> {

    private List<Item> items;
    private Context mContext;
    public ItemsAdapter(List<Item> items , Context context) {
        this.items = items;
        mContext = context;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_row, viewGroup,false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder itemHolder, int i) {
        itemHolder.name.setText(items.get(i).getLogin());
        itemHolder.avatar.setText(items.get(i).getAvatarUrl());

        ImageView posterImageView = itemHolder.icon;

        String posterUri = items.get(i).getAvatarUrl();

        Picasso.with(mContext)
                .load(posterUri)
                .error(R.drawable.noimage)
                .into(posterImageView);
    }

    @Override
    public int getItemCount() {
        if (items.size() > 0)
            return items.size();
        else
            return 0;
    }

    public class ItemHolder extends RecyclerView.ViewHolder{
        TextView name,avatar;
        ImageView icon;
        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            avatar = itemView.findViewById(R.id.avatar);
            icon = itemView.findViewById(R.id.icon);
        }
    }

}
