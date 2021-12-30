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

import com.example.myapplication.databinding.ActivityMainBinding;
import com.google.android.material.tabs.TabLayout;

public class ImageDetailActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private String imageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detail);

        getSupportActionBar().setTitle("Activity2");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imageUrl = getIntent().getExtras().getString("imageUrl");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
