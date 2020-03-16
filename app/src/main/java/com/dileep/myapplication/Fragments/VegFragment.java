package com.dileep.myapplication.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dileep.myapplication.Adapters.VegAdapter;
import com.dileep.myapplication.R;
import com.dileep.myapplication.pojos.BestsellersPojo;
import com.dileep.myapplication.pojos.OffersPojo;
import com.dileep.myapplication.pojos.OrderListPojo;
import com.dileep.myapplication.pojos.UploadProductPojo;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class VegFragment  extends Fragment {

    View rootview;
    RecyclerView vegrecyclerview;
    VegAdapter adapter;

    FirebaseDatabase database,bestdb,prodb;
    DatabaseReference reference,bestref,proref;
    UploadProductPojo productPojo;
    SharedPreferences sharedPreferences;
    ArrayList<OrderListPojo> itemlist;

    ArrayList<UploadProductPojo> productPojoArrayList=new ArrayList<>();

    ShimmerFrameLayout shimmerFrameLayout,shimmerFrameLayout1,shimmerFrameLayout2;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootview=inflater.inflate(R.layout.veg_fragment,container,false);

        vegrecyclerview=(RecyclerView)rootview.findViewById(R.id.vegrecyclerview);
        shimmerFrameLayout2=(ShimmerFrameLayout)rootview.findViewById(R.id.shimmer_view_container);
        vegrecyclerview.setHasFixedSize(true);
        vegrecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        sharedPreferences = getActivity().getSharedPreferences("items", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("dileep", null);
        Type type = new TypeToken<ArrayList<OrderListPojo>>() {
        }.getType();
        itemlist = gson.fromJson(json, type);
        if (itemlist == null) {
            itemlist = new ArrayList<>();
        }
        adapter=new VegAdapter(productPojoArrayList,getContext(),itemlist);
        vegrecyclerview.setAdapter(adapter);
        loadProducts();

        return rootview;
    }
    private void loadProducts() {

        prodb = FirebaseDatabase.getInstance();
        proref = prodb.getReference("vegpizza");

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
                vegrecyclerview.setVisibility(View.VISIBLE);
                // bestviewall.setVisibility(View.VISIBLE);
                //init();
                //progressBar.setVisibility(View.GONE);
                // progressDialog.dismiss();
                //listview.setAdapter(adapter);
                // products_total.setText(String.valueOf(uploadProductPojoList.size()));
                adapter.notifyDataSetChanged();
                // Toast.makeText(getContext(), "sizeD "+offersPojoArrayList.size(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "something went wrong...", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
