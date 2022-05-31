package com.iqcollections;

import android.content.ContentResolver;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class colAdapter extends RecyclerView.Adapter<colAdapter.ViewHolder> {
    Context context;
    List<readCollections> lstCollection;
    public colAdapter(   Context context,List<readCollections> lstCollection){
this.context = context;
this.lstCollection = lstCollection;

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.displaycoll,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        readCollections col = lstCollection.get(position);
        holder.txtView.setText(col.getColName());

        String imageURI = null;
        imageURI = col.getColImgUrl();
        Picasso.get().load(imageURI).into(holder.imgView);
    }

    @Override
    public int getItemCount() {
        return lstCollection.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgView;
        TextView txtView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        imgView = itemView.findViewById(R.id.imgRecycle);
        txtView = itemView.findViewById(R.id.txtRecycle);

        }
    }

}
