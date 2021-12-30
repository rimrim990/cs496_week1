package com.example.myapplication;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ViewHolderItem extends RecyclerView.ViewHolder{

    ImageView iv_icon;

    public ViewHolderItem(@NonNull View itemView, final RecyclerviewAdapter.OnItemClickEventListener itemClickListener) {
        super(itemView);

        iv_icon = (ImageView)itemView.findViewById(R.id.iv_icon);

        itemView.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int position = getAdapterPosition();
                itemClickListener.onItemClick(view, position);
            }
        }));
    }

    public void onBind(BearItem item) {
        iv_icon.setImageResource(item.getResId());
    }
}
