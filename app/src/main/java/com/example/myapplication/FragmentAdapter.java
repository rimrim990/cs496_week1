package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

<<<<<<< HEAD
import com.example.myapplication.ui.alarm.ThirdFragment;
=======
>>>>>>> 7c8d952 (gallery finish)
import com.example.myapplication.ui.gallery.SecondFragment;

public class FragmentAdapter extends FragmentStateAdapter{

    public FragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
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
