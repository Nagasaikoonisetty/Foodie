package com.dileep.myapplication.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.dileep.myapplication.Adapters.HomeOffersAdapter;
import com.dileep.myapplication.R;
import com.dileep.myapplication.pojos.OffersPojo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OffersDshop extends Fragment {
    View rootView;
    RecyclerView offersview;

    HomeOffersAdapter homeOffersAdapter;;
    ArrayList<OffersPojo> offersPojoArrayList=new ArrayList<>();
    FirebaseDatabase database,bestdb,prodb;
    DatabaseReference reference,bestref,proref;
    OffersPojo offersPojo;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView=inflater.inflate(R.layout.offers_dshop_fragment,container,false);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("offers");
        offersview=(RecyclerView)rootView.findViewById(R.id.offersview);
        offersview.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getContext(),2);
        offersview.setLayoutManager(gridLayoutManager);
        homeOffersAdapter=new HomeOffersAdapter(offersPojoArrayList,getContext());
        offersview.setAdapter(homeOffersAdapter);
        loadOffers();
        return rootView;

    }
    private void loadOffers() {
        // progressBar.setVisibility(View.VISIBLE);
       // shimmerFrameLayout.startShimmerAnimation();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                offersPojoArrayList.clear();
                System.out.println("datacame:" + dataSnapshot);
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    offersPojo = ds.getValue(OffersPojo.class);
                    offersPojoArrayList.add(offersPojo);
                }
               // shimmerFrameLayout.stopShimmerAnimation();
                //shimmerFrameLayout.setVisibility(View.GONE);
                //offerscycle.setVisibility(View.VISIBLE);
                //offerviewall.setVisibility(View.VISIBLE);
                //init();
                //progressBar.setVisibility(View.GONE);
                // progressDialog.dismiss();
                //listview.setAdapter(adapter);
                // products_total.setText(String.valueOf(uploadProductPojoList.size()));
                homeOffersAdapter.notifyDataSetChanged();
                //Toast.makeText(getContext(), "sizeD "+offersPojoArrayList.size(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "something went wrong...", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
