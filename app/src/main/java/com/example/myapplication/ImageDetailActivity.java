package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.example.myapplication.databinding.ActivityMainBinding;
import com.google.android.material.tabs.TabLayout;

import java.io.File;

public class ImageDetailActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    //private int imageId;
    //private String imageName;
    private String imagePath;

    private ImageView fullScreen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detail);

        //imageId = getIntent().getExtras().getInt("imageId");
        //imageName = getIntent().getExtras().getString("imageName");
        imagePath = getIntent().getStringExtra("imagePath");

        fullScreen = (ImageView) findViewById(R.id.full_iv_icon);
        //fullScreen.setImageResource(imageId);

        File imageFile = new File(imagePath);

        // if the file exits then we are loading that image in our image view.
        if (imageFile.exists()) {
            // TODO : load image file using image path
            // Picasso.get().load(imgFile).placeholder(R.drawable.ic_launcher_background).into(imageView)
            Glide.with(this).load(imageFile).into(fullScreen);
        }

        //getSupportActionBar().setTitle(imageName);
        getSupportActionBar().setTitle("Activity2");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
