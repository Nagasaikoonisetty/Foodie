package com.dileep.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.dileep.myapplication.Fragments.BestsellerDshop;
import com.dileep.myapplication.Fragments.HomeDshop;
import com.dileep.myapplication.Fragments.MenuFragment;
import com.dileep.myapplication.Fragments.OffersDshop;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    public static ViewPager viewPager;
    public TabLayout tabLayout;
    public PagerAdpter pagerAdpter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager=(ViewPager)findViewById(R.id.viewPager);
        tabLayout=(TabLayout)findViewById(R.id.tabs);

        tabLayout.addTab(tabLayout.newTab().setText("Home").setIcon(R.drawable.homedark));
        tabLayout.addTab(tabLayout.newTab().setText("Menu").setIcon(R.drawable.pizza));
        tabLayout.addTab(tabLayout.newTab().setText("Offers").setIcon(R.drawable.offerlyt));
        tabLayout.addTab(tabLayout.newTab().setText("Best").setIcon(R.drawable.bestlyt));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        viewPager.setOffscreenPageLimit(3);

        pagerAdpter=new PagerAdpter(getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdpter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                switch (tab.getPosition()){
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
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
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
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
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
                    HomeDshop tab1=new HomeDshop();
                    return tab1;
                case 1:
                    MenuFragment menuFragment=new MenuFragment();
                    return menuFragment;

                case 2:
                    OffersDshop tab2=new OffersDshop();
                    return tab2;
                case 3:
                    BestsellerDshop tab3=new BestsellerDshop();
                    return tab3;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return tabcount;
        };
    }
    @Override
    public void onBackPressed() {
        if (tabLayout.getSelectedTabPosition()!=0){
            viewPager.setCurrentItem(0);
        }else {
            super.onBackPressed();
        }
    }
}
