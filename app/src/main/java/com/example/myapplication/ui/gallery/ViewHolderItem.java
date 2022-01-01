package com.example.myapplication.ui.gallery;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

public class ViewHolderItem extends RecyclerView.ViewHolder{

    public ImageView iv_icon;

    public ViewHolderItem(@NonNull View itemView) {
        super(itemView);
        // initializing our view with their ids.
        iv_icon = (ImageView)itemView.findViewById(R.id.iv_icon);

    }
}
