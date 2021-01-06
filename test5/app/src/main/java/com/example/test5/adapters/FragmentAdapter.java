package com.example.test5.adapters;

import android.app.Activity;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import com.example.test5.fragment.gardenPlants;
import com.example.test5.fragment.indoorPlants;
import com.example.test5.fragment.outDoor;

public class FragmentAdapter extends FragmentPagerAdapter {
    public FragmentAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new indoorPlants();

            case 1:
                return new outDoor();

            case 2:
                return new gardenPlants();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
