package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;

public class RecyclerviewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    // adapter 에 들어갈 list
    private ArrayList<String> imagePaths;
    private Context context;
    //private OnItemClickEventListener mItemClickListener;

    // constructor
    public RecyclerviewAdapter(Context context, ArrayList<String> imagePaths) {
        this.context = context;
        this.imagePaths = imagePaths;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_list_item, parent, false);
        //return new ViewHolderItem(view, mItemClickListener);
        return new ViewHolderItem(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        //((ViewHolderItem)holder).onBind(listData.get(position));

        // on below line we are getting the file from the
        // path which we have stored in our list.
        File imgFile = new File(imagePaths.get(position));

        // on below line we are checking if the file exists or not.
        if (imgFile.exists()) {

            // if the file exists then we are displaying that file in our image view using library.
            // TODO : loading image file

            Glide.with(context)
                    .load(Uri.fromFile(imgFile))
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .error(R.drawable.ic_launcher_background)
                    .into(((ViewHolderItem)holder).iv_icon);

            // on below line we are adding clock listener to our item of recycler view.
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // inside on click listener we are creating a new intent
                    Intent i = new Intent(context, ImageDetailActivity.class);

                    // on below line we are passing the image path to our new activity.
                    i.putExtra("imagePath", imagePaths.get(holder.getAdapterPosition()));

                    // at last we are starting our activity.
                    context.startActivity(i);
                }
            });
        }
    }

    /*
    public interface OnItemClickEventListener {
        void onItemClick(View view, int pos);
    }

    public void setOnItemClickListener(OnItemClickEventListener listener) {
        mItemClickListener = listener;
    }
    */

    @Override
    public int getItemCount() {
        // return listData.size();
        return imagePaths.size();
    }

    void addItem(String imagePath) {
        // 외부에서 item 을 추가시킬 함수
        //listData.add(item);
        imagePaths.add(imagePath);
    }
}
