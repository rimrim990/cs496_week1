package com.example.myapplication.ui.tab;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;

public class ImageDetailFragment extends Fragment {

    ImageView fullView;
    FloatingActionButton deleteBtn;
    String imgPath;
    Context mContext;

    public ImageDetailFragment(String imgPath, Context context) {
        // empty constructor
        this.imgPath = imgPath;
        this.mContext = context;
    }

    public static ImageDetailFragment newInstance(String imgPath, Context context) {
        ImageDetailFragment fragment = new ImageDetailFragment(imgPath, context);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_image_detail, container, false);
        fullView = (ImageView)view.findViewById(R.id.full_iv_icon);
        deleteBtn = (FloatingActionButton)view.findViewById(R.id.deleteBtn);

        File imageFile = new File(imgPath);

        // if the file exits then we are loading that image in our image view.
        if (imageFile.exists()) {
            Glide.with(mContext).load(imageFile).into(fullView);
        }

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(imageFile.exists()) {
                    imageFile.delete();
                }
            }
        });

        return view;
    }

}
