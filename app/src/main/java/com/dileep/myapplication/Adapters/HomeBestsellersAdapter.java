package com.dileep.myapplication.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dileep.myapplication.Appconst;
import com.dileep.myapplication.DisplayActivity;
import com.dileep.myapplication.R;
import com.dileep.myapplication.pojos.BestsellersPojo;
import com.dileep.myapplication.pojos.OrderListPojo;


import java.util.ArrayList;

public class HomeBestsellersAdapter extends RecyclerView.Adapter<HomeBestsellersAdapter.HomeBestsellersHolder> {

    ArrayList<BestsellersPojo> bestsellersPojoArrayList;
    Context context;
    RequestOptions requestOptions;


    public HomeBestsellersAdapter(ArrayList<BestsellersPojo> bestsellersPojoArrayList, Context context) {
        this.bestsellersPojoArrayList = bestsellersPojoArrayList;
        this.context = context;
        requestOptions=new RequestOptions();
        requestOptions.centerCrop();
    }

    @NonNull
    @Override
    public HomeBestsellersHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.bestsellers_list_item,parent,false);
       return new HomeBestsellersHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeBestsellersHolder holder, int position) {
        final BestsellersPojo bestsellersPojo=bestsellersPojoArrayList.get(position);
        holder.name.setText(bestsellersPojo.getProductName());
        holder.cost.setText("Rs "+bestsellersPojo.getProductCost());

        Glide.with(context)
                .load(bestsellersPojo.getImageUrls().get(0))
                .apply(requestOptions)
                .into(holder.imageView);
/*        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle=new Bundle();
                bundle.putStringArrayList("images",bestsellersPojo.getImageUrls());
                bundle.putString("name",bestsellersPojo.getProductName());
                bundle.putString("cost",bestsellersPojo.getProductCost());
                bundle.putString("number",bestsellersPojo.getWhatsAppNumber());
                bundle.putString("id",bestsellersPojo.getProductId());
                Intent displayIntent=new Intent(context, DisplayActivity.class);
                displayIntent.putExtras(bundle);
                context.startActivity(displayIntent);
            }
        });*/
            holder.addtobag.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int addposition= Appconst.orderListPojoArrayList.size();
                    Appconst.orderListPojoArrayList.add(addposition,new OrderListPojo(bestsellersPojo.getImageUrls().get(0),bestsellersPojo.getProductName(),bestsellersPojo.getProductCost()));
                    Toast.makeText(context, "item aded to bag", Toast.LENGTH_SHORT).show();
                }
            });
    }

    @Override
    public int getItemCount() {
        return bestsellersPojoArrayList.size();
    }

    public class HomeBestsellersHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView name, cost,percent,addtobag;
        public HomeBestsellersHolder(@NonNull View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView1);
            name = (TextView) itemView.findViewById(R.id.item_name);
            cost = (TextView) itemView.findViewById(R.id.item_cost);
            percent=(TextView)itemView.findViewById(R.id.percent);
            addtobag=(TextView)itemView.findViewById(R.id.addtobag);
        }
    }
}
