package com.example.myapplication;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_CONTACTS;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import android.content.pm.PackageManager;
import android.media.Image;
import android.os.Bundle;

import com.example.myapplication.ui.tab.ImageDetailFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

//import com.example.myapplication.ui.main.SectionsPagerAdapter;
import com.example.myapplication.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager2 pager2;
    FragmentAdapter adapter;
    FragmentManager fm;

    private boolean TAB1_PERMISSION = true;
    private boolean TAB2_PERMISSION = true;

    private static final int PERMISSION_REQUEST_CODE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = findViewById(R.id.tabs);
        pager2 = findViewById(R.id.view_pager2);

        fm = getSupportFragmentManager();
        adapter = new FragmentAdapter(fm, getLifecycle(), TAB1_PERMISSION, TAB2_PERMISSION);
        pager2.setAdapter(adapter);

        tabLayout.addTab(tabLayout.newTab().setText("Contacts"));
        tabLayout.addTab(tabLayout.newTab().setText("Gallery"));
        tabLayout.addTab(tabLayout.newTab().setText("Not Yet"));

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

        checkAndRequestPermissions();
    }

    // function to check permission
    private void checkAndRequestPermissions() {
        int WExtstorePermission = ActivityCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE);
        int cameraPermission = ActivityCompat.checkSelfPermission(this, CAMERA);
        int contactPermission = ActivityCompat.checkSelfPermission(this, WRITE_CONTACTS);
        List<String> listPermissionsNeeded = new ArrayList<>();

        if (cameraPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(CAMERA);
        }
        if (WExtstorePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(WRITE_EXTERNAL_STORAGE);
        }

        if (contactPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(WRITE_CONTACTS);
        }

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),
                    PERMISSION_REQUEST_CODE);
        }
    }

    // handled permission result
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (ActivityCompat.checkSelfPermission(this, CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    TAB2_PERMISSION = false;
                    Toast.makeText(this, "FlagUp Requires Access to Camera.", Toast.LENGTH_SHORT);
                } else if (ActivityCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    TAB2_PERMISSION = false;
                    Toast.makeText(this, "FlagUp Requires Access to Your Storage.", Toast.LENGTH_SHORT).show();
                } else if (ActivityCompat.checkSelfPermission(this, WRITE_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                  Toast.makeText(this, "FlagUp Requires Access to Your Contact.", Toast.LENGTH_SHORT).show();
                } else {
                    TAB2_PERMISSION = true;
                }
                break;
        }
    }

    public void replaceFragments(String imgPath) {
        ImageDetailFragment newFragment = ImageDetailFragment.newInstance(imgPath, this);
        FragmentTransaction transaction = fm.beginTransaction();

        transaction.replace(R.id.fragment_second, newFragment, null);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();
    }
}