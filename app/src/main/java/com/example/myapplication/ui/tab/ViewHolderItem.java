package com.example.myapplication.ui.tab;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.ArrayList;

public class ViewHolderItem extends RecyclerView.ViewHolder{

    public ImageView iv_icon;

    public ViewHolderItem(@NonNull View itemView) {
        super(itemView);
        // initializing our view with their ids.
        iv_icon = (ImageView)itemView.findViewById(R.id.iv_icon);

    }
}
