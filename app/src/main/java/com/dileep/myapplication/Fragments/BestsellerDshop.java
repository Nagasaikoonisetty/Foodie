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


import com.dileep.myapplication.Adapters.HomeBestsellersAdapter;


import com.dileep.myapplication.R;
import com.dileep.myapplication.pojos.BestsellersPojo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BestsellerDshop extends Fragment {

    View rootView;
    FirebaseDatabase database,bestdb,prodb;
    DatabaseReference reference,bestref,proref;
    BestsellersPojo bestsellersPojo;
    ArrayList<BestsellersPojo> bestsellersPojoArrayList=new ArrayList<>();
    HomeBestsellersAdapter homeBestsellersAdapter;
    RecyclerView bestsellerview;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView=inflater.inflate(R.layout.bestseller_dshop_fragment,container,false);
        bestsellerview=(RecyclerView)rootView.findViewById(R.id.bestsellerview);
        bestsellerview.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getContext(),2);
        bestsellerview.setLayoutManager(gridLayoutManager);
        homeBestsellersAdapter=new HomeBestsellersAdapter(bestsellersPojoArrayList,getContext());
        bestsellerview.setAdapter(homeBestsellersAdapter);
        loadBestsellers();
        return rootView;
    }

    private void loadBestsellers() {

        bestdb = FirebaseDatabase.getInstance();
        bestref = bestdb.getReference("bestsellers");

        //shimmerFrameLayout1.startShimmerAnimation();
        bestref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                bestsellersPojoArrayList.clear();
                System.out.println("datacame:" + dataSnapshot);
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    bestsellersPojo  = ds.getValue(BestsellersPojo.class);
                    bestsellersPojoArrayList.add(bestsellersPojo);
                }
               // shimmerFrameLayout1.stopShimmerAnimation();
               // shimmerFrameLayout1.setVisibility(View.GONE);
               // bestsellerscycle.setVisibility(View.VISIBLE);
               // bestviewall.setVisibility(View.VISIBLE);
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
}
