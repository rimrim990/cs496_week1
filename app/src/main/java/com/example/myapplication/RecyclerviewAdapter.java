package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerviewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    // adapter 에 들어갈 list
    private ArrayList<BearItem> listData;
    private OnItemClickEventListener mItemClickListener;

    // constructor
    public RecyclerviewAdapter(ArrayList<BearItem> list) {
        this.listData = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_list_item, parent, false);
        return new ViewHolderItem(view, mItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ViewHolderItem)holder).onBind(listData.get(position));
    }

    public interface OnItemClickEventListener {
        void onItemClick(View view, int pos);
    }

    public void setOnItemClickListener(OnItemClickEventListener listener) {
        mItemClickListener = listener;
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    void addItem(BearItem item) {
        // 외부에서 item 을 추가시킬 함수
        listData.add(item);
    }
}
