package com.ss2.library_ex;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by SunJae on 2017-01-21.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private ArrayList<RecyclerItem> itemList = new ArrayList<RecyclerItem>();
    private int itemLayout;
    private Context context;

    public RecyclerAdapter(ArrayList<RecyclerItem> items, int itemLayout, Context context) {
        this.itemList = items;
        this.itemLayout = itemLayout;
        this.context = context;
    }

    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.ViewHolder holder, int position) {
        RecyclerItem item = itemList.get(position);
        holder.textTitle.setText(item.getTitle());
        ImageTrans(holder, item.getBitmap());
        holder.itemView.setTag(item);
    }

    public Bitmap ImageTrans(RecyclerAdapter.ViewHolder holder, String url){
        Glide.with(context).load(url).into(holder.img);
        return null;
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView img;
        public TextView textTitle;

        public ViewHolder(View itemView) {
            super(itemView);

            img = (ImageView) itemView.findViewById(R.id.iv_list_Img);
            textTitle = (TextView) itemView.findViewById(R.id.tv_list_title);
        }

    }
}
