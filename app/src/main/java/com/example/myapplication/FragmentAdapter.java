package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.myapplication.ui.tab.SecondFragment;

public class FragmentAdapter extends FragmentStateAdapter{
    private boolean TAB2_PERMISSION;
    private boolean TAB1_PERMISSION;

    public FragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle,
                           boolean TAB1_PERMISSION, boolean TAB2_PERMISSION) {
        super(fragmentManager, lifecycle);

        this.TAB1_PERMISSION = TAB1_PERMISSION;
        this.TAB2_PERMISSION = TAB2_PERMISSION;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position)
        {
            case 1:
                return new SecondFragment();
            case 2:
                return new ThirdFragment();
        }
        return new FirstFragment();
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
