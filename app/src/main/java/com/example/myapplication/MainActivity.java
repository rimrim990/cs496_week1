package com.example.myapplication;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_CONTACTS;
import static android.Manifest.permission.VIBRATE;
import static android.Manifest.permission.WRITE_CONTACTS;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import android.content.pm.PackageManager;
import android.os.Bundle;

import com.example.myapplication.ui.gallery.ImageDetailFragment;
import com.google.android.material.tabs.TabLayout;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

//import com.example.myapplication.ui.main.SectionsPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager2 pager2;
    FragmentAdapter adapter;
    FragmentManager fm;

    private static final int PERMISSION_REQUEST_CODE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkAndRequestPermissions();

        tabLayout = findViewById(R.id.tabs);
        pager2 = findViewById(R.id.view_pager2);

        fm = getSupportFragmentManager();

        tabLayout.addTab(tabLayout.newTab().setText("Contacts"));
        tabLayout.addTab(tabLayout.newTab().setText("Gallery"));
        tabLayout.addTab(tabLayout.newTab().setText("Alarm"));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        pager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });


    }

    private void setAdapter() {
        adapter = new FragmentAdapter(fm, getLifecycle());
        pager2.setAdapter(adapter);
    }

    // function to check permission
    private void checkAndRequestPermissions() {
        int WExtstorePermission = ActivityCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE);
        int cameraPermission = ActivityCompat.checkSelfPermission(this, CAMERA);
        int WcontactPermission = ActivityCompat.checkSelfPermission(this, WRITE_CONTACTS);
        int RcontactPermission = ActivityCompat.checkSelfPermission(this, READ_CONTACTS);
        int VibratePermission = ActivityCompat.checkSelfPermission(this, VIBRATE);

        List<String> listPermissionsNeeded = new ArrayList<>();

        if (cameraPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(CAMERA);
        }
        if (WExtstorePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(WRITE_EXTERNAL_STORAGE);
        }

        if (WcontactPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(WRITE_CONTACTS);
        }

        if (RcontactPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(READ_CONTACTS);
        }

        if (VibratePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(VIBRATE);
        }

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),
                    PERMISSION_REQUEST_CODE);
        }
    }

    public void replaceFragments(String imgPath) {
        ImageDetailFragment newFragment = ImageDetailFragment.newInstance(imgPath);
        FragmentTransaction transaction = fm.beginTransaction();

        transaction.replace(R.id.fragment_second, newFragment, null);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();
    }
}