package com.dileep.myapplication.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;


import com.dileep.myapplication.Adapters.HomeBestsellersAdapter;
import com.dileep.myapplication.Adapters.HomeOffersAdapter;
import com.dileep.myapplication.Adapters.ProductsAdapter;
import com.dileep.myapplication.Adapters.SliderAdapter;
import com.dileep.myapplication.DisplayActivity;
import com.dileep.myapplication.Login;
import com.dileep.myapplication.MainActivity;
import com.dileep.myapplication.OrderListActivity;
import com.dileep.myapplication.R;
import com.dileep.myapplication.pojos.BestsellersPojo;
import com.dileep.myapplication.pojos.OffersPojo;
import com.dileep.myapplication.pojos.UploadProductPojo;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

public class HomeDshop extends Fragment {
    View rootView;
    Toolbar toolbar;
    TextView offerviewall,bestviewall;
    private static ViewPager sliderPager;
    FirebaseDatabase database,bestdb,prodb;
    DatabaseReference reference,bestref,proref;
    FirebaseAuth firebaseAuth;
    OffersPojo offersPojo;
    BestsellersPojo bestsellersPojo;
    UploadProductPojo productPojo;
    ArrayList<OffersPojo> offersPojoArrayList=new ArrayList<>();
    ArrayList<BestsellersPojo>bestsellersPojoArrayList=new ArrayList<>();
    ArrayList<UploadProductPojo> productPojoArrayList=new ArrayList<>();

    ShimmerFrameLayout shimmerFrameLayout,shimmerFrameLayout1,shimmerFrameLayout2;
    RecyclerView offerscycle,bestsellerscycle,productcycle;
    HomeOffersAdapter homeOffersAdapter;
    HomeBestsellersAdapter homeBestsellersAdapter;
    ProductsAdapter productsAdapter;
    private int currentPage = 0;
    ArrayList<Integer> slideimages;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      rootView=inflater.inflate(R.layout.fragment_home_dshop,container,false);
      offerviewall=(TextView)rootView.findViewById(R.id.offerviewall);
        bestviewall=(TextView)rootView.findViewById(R.id.bestviewall);
      sliderPager=(ViewPager)rootView.findViewById(R.id.pager);
      toolbar=(Toolbar)rootView.findViewById(R.id.hometool);
        setHasOptionsMenu(true);
        toolbar.inflateMenu(R.menu.inner_menu);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.orderList:
                        Intent orlist=new Intent(getContext(), OrderListActivity.class);
                        startActivity(orlist);
                        return true;
                    case R.id.logout:
                        logout();
                        return true;
                    default:
                        return false;
                }

            }
        });
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("offers");

        shimmerFrameLayout=(ShimmerFrameLayout)rootView.findViewById(R.id.shimmer_view_container);
        shimmerFrameLayout1=(ShimmerFrameLayout)rootView.findViewById(R.id.shimmer_view_container1);
        shimmerFrameLayout2=(ShimmerFrameLayout)rootView.findViewById(R.id.shimmer_view_container2);
        offerscycle=(RecyclerView)rootView.findViewById(R.id.offercycle);
        bestsellerscycle=(RecyclerView)rootView.findViewById(R.id.bestcycle);
        productcycle=(RecyclerView)rootView.findViewById(R.id.productscycle);
        productcycle.setHasFixedSize(true);
        offerscycle.setHasFixedSize(true);
        productcycle.setNestedScrollingEnabled(false);
        bestsellerscycle.setNestedScrollingEnabled(false);
        bestsellerscycle.setHasFixedSize(true);
       // GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2);
       // gridLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
       // offerscycle.setLayoutManager(gridLayoutManager);
        offerscycle.setNestedScrollingEnabled(false);

        offerscycle.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false));
        bestsellerscycle.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false));
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getContext(),2);

        productcycle.setLayoutManager(gridLayoutManager);

        homeOffersAdapter=new HomeOffersAdapter(offersPojoArrayList,getContext());
        homeBestsellersAdapter=new HomeBestsellersAdapter(bestsellersPojoArrayList,getContext());
        productsAdapter=new ProductsAdapter(productPojoArrayList,getContext());

        offerscycle.setAdapter(homeOffersAdapter);
        bestsellerscycle.setAdapter(homeBestsellersAdapter);
        productcycle.setAdapter(productsAdapter);

        loadOffers();
        loadBestsellers();
        loadProducts();
        getImages();


        offerviewall.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {

              MainActivity.viewPager.setCurrentItem(2);
          }
      });
        bestviewall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.viewPager.setCurrentItem(3);
            }
        });
      return rootView;
    }

    private void logout() {
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseAuth.signOut();
        Intent homeintent=new Intent(getContext(),Login.class);
        homeintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(homeintent);
        getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_up);

    }

    private void getImages() {
        slideimages=new ArrayList<>();
        slideimages.add(R.drawable.edvregular_3);
        slideimages.add(R.drawable.edvmedium_1);
        slideimages.add(R.drawable.edvregular_1);
        slideimages.add(R.drawable.edvmedium_2);
        slideimages.add(R.drawable.edvmedium_3);
        slideimages.add(R.drawable.edvmedium_4);
        slideimages.add(R.drawable.edvregular_2);
        init();
     /*   restarents=new ArrayList<>();

        restarents.add(new RestuarentPojo(R.drawable.png2,"Hotel Grills","7729912345"));
        restarents.add(new RestuarentPojo(R.drawable.png3,"Alankar","9988231261"));
        restarents.add(new RestuarentPojo(R.drawable.png4,"RK Restaurant","7729958712"));
        restarents.add(new RestuarentPojo(R.drawable.png5,"Hotel Pasand","6655443312"));
        restarents.add(new RestuarentPojo(R.drawable.png6,"The KAY Hotel","9878675443"));
*/


    }

    private void loadProducts() {

        prodb = FirebaseDatabase.getInstance();
        proref = database.getReference("vegpizza");

        shimmerFrameLayout2.startShimmerAnimation();
        proref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                productPojoArrayList.clear();
                System.out.println("datacame:" + dataSnapshot);
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    productPojo  = ds.getValue(UploadProductPojo.class);
                    productPojoArrayList.add(productPojo);
                }
                shimmerFrameLayout2.stopShimmerAnimation();
                shimmerFrameLayout2.setVisibility(View.GONE);
                productcycle.setVisibility(View.VISIBLE);
               // bestviewall.setVisibility(View.VISIBLE);
                //init();
                //progressBar.setVisibility(View.GONE);
                // progressDialog.dismiss();
                //listview.setAdapter(adapter);
                // products_total.setText(String.valueOf(uploadProductPojoList.size()));
                productsAdapter.notifyDataSetChanged();
               // Toast.makeText(getContext(), "sizeD "+offersPojoArrayList.size(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "something went wrong...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadBestsellers() {

        bestdb = FirebaseDatabase.getInstance();
        bestref = bestdb.getReference("bestsellers");

        shimmerFrameLayout1.startShimmerAnimation();
        bestref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                bestsellersPojoArrayList.clear();
                System.out.println("datacame:" + dataSnapshot);
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                   bestsellersPojo  = ds.getValue(BestsellersPojo.class);
                    bestsellersPojoArrayList.add(bestsellersPojo);
                }
                shimmerFrameLayout1.stopShimmerAnimation();
                shimmerFrameLayout1.setVisibility(View.GONE);
                bestsellerscycle.setVisibility(View.VISIBLE);
                bestviewall.setVisibility(View.VISIBLE);
                //init();
                //progressBar.setVisibility(View.GONE);
                // progressDialog.dismiss();
                //listview.setAdapter(adapter);
                // products_total.setText(String.valueOf(uploadProductPojoList.size()));
                homeBestsellersAdapter.notifyDataSetChanged();
                //Toast.makeText(getContext(), "sizeD "+offersPojoArrayList.size(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "something went wrong...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadOffers() {
        // progressBar.setVisibility(View.VISIBLE);
        shimmerFrameLayout.startShimmerAnimation();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                offersPojoArrayList.clear();
                System.out.println("datacame:" + dataSnapshot);
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    offersPojo = ds.getValue(OffersPojo.class);
                    offersPojoArrayList.add(offersPojo);
                }
                shimmerFrameLayout.stopShimmerAnimation();
                shimmerFrameLayout.setVisibility(View.GONE);
                offerscycle.setVisibility(View.VISIBLE);
                offerviewall.setVisibility(View.VISIBLE);
                //init();
                //progressBar.setVisibility(View.GONE);
                // progressDialog.dismiss();
                //listview.setAdapter(adapter);
                // products_total.setText(String.valueOf(uploadProductPojoList.size()));
                homeOffersAdapter.notifyDataSetChanged();
              //  Toast.makeText(getContext(), "sizeD "+offersPojoArrayList.size(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "something went wrong...", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void init() {

      //  mPager = (ViewPager) findViewById(R.id.pager);
        sliderPager.setAdapter(new SliderAdapter(null, getContext(),slideimages));
        CircleIndicator indicator = (CircleIndicator)rootView. findViewById(R.id.indicator);
        indicator.setViewPager(sliderPager);

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == slideimages.size()) {
                    currentPage = 0;
                }
                sliderPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 2000,2000);

        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentPage = position;

            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        });
    }
}
