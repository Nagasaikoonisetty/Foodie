package com.dileep.myapplication.Fragments;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.dileep.myapplication.MainActivity;
import com.dileep.myapplication.R;
import com.google.android.material.tabs.TabLayout;

public class MenuFragment extends Fragment {
    View rootView;
    public static ViewPager viewPager;
    public TabLayout tabLayout;
    public PagerAdpter pagerAdpter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView=inflater.inflate(R.layout.menu_fragment,container,false);

        viewPager=(ViewPager)rootView.findViewById(R.id.menuviewpager);
        tabLayout=(TabLayout)rootView.findViewById(R.id.tablayout);

        tabLayout.addTab(tabLayout.newTab().setText("VEG PIZZA"));
        tabLayout.addTab(tabLayout.newTab().setText("NON-VEG PIZZA"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        viewPager.setOffscreenPageLimit(1);

        pagerAdpter=new PagerAdpter(getChildFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdpter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

/*                switch (tab.getPosition()){
                    case 0:
                        tab.setIcon(R.drawable.homedark);
                        break;
                    case 1:
                        tab.setIcon(R.drawable.pizza1);

                        break;
                    case 2:
                        tab.setIcon(R.drawable.offerdark);
                        break;
                    case 3:
                        tab.setIcon(R.drawable.bestdark);
                        break;
                }*/

            }

            @SuppressLint("ResourceAsColor")
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

/*                switch (tab.getPosition()){
                    case 0:
                        tab.setIcon(R.drawable.homelyt);
                        break;
                    case 1:
                        tab.setIcon(R.drawable.pizza);
                        break;
                    case 2:
                        tab.setIcon(R.drawable.offerlyt);

                        break;
                    case 3:
                        tab.setIcon(R.drawable.bestlyt);
                        break;
                }*/
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        return rootView;

    }
    public class PagerAdpter extends FragmentPagerAdapter {

        int tabcount;

        public PagerAdpter(FragmentManager fm, int tabcount) {
            super(fm);
            this.tabcount=tabcount;
        }

        @Override
        public Fragment getItem(int i) {
            switch (i){
                case 0:
                    VegFragment tab1=new VegFragment();
                    return tab1;
                case 1:
                    NonvegFragment nonvegFragment=new NonvegFragment();
                    return nonvegFragment;


                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return tabcount;
        };
    }
}
