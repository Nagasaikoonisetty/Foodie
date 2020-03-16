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
import com.dileep.myapplication.pojos.OffersPojo;
import com.dileep.myapplication.pojos.OrderListPojo;


import java.util.ArrayList;


public class HomeOffersAdapter extends RecyclerView.Adapter<HomeOffersAdapter.HomeOffersHolder> {

    ArrayList<OffersPojo> offersPojoArrayList;
    Context context;
    RequestOptions requestOptions;

    public HomeOffersAdapter(ArrayList<OffersPojo> offersPojoArrayList, Context context) {
        this.offersPojoArrayList = offersPojoArrayList;
        this.context = context;
        requestOptions=new RequestOptions();
        requestOptions.centerCrop();

    }

    @NonNull
    @Override
    public HomeOffersHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cloud_menu_list_item, parent, false);
        return new HomeOffersHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeOffersHolder holder, int position) {
        final OffersPojo offersPojo = offersPojoArrayList.get(position);
        holder.name.setText(offersPojo.getProductName());
        holder.cost.setText("Rs "+offersPojo.getProductCost());
        holder.percent.setText(offersPojo.getOfferPercent()+"%");
        Glide.with(context)
                .load(offersPojo.getImageUrls().get(0))
                .apply(requestOptions)
                .into(holder.imageView);
/*        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle=new Bundle();
                bundle.putStringArrayList("images",offersPojo.getImageUrls());
                bundle.putString("name",offersPojo.getProductName());
                bundle.putString("cost",offersPojo.getProductCost());
                bundle.putString("number",offersPojo.getWhatsAppNumber());
                bundle.putString("id",offersPojo.getProductId());
                Intent displayIntent=new Intent(context, DisplayActivity.class);
                displayIntent.putExtras(bundle);
                context.startActivity(displayIntent);
            }
        });*/
        holder.addtobag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int addposition= Appconst.orderListPojoArrayList.size();
                Appconst.orderListPojoArrayList.add(addposition,new OrderListPojo(offersPojo.getImageUrls().get(0),offersPojo.getProductName(),offersPojo.getProductCost()));
                Toast.makeText(context, "item aded to bag", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return offersPojoArrayList.size();
    }

    public class HomeOffersHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView name, cost,percent,addtobag;

        public HomeOffersHolder(@NonNull View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView1);
            name = (TextView) itemView.findViewById(R.id.item_name);
            cost = (TextView) itemView.findViewById(R.id.item_cost);
            percent=(TextView)itemView.findViewById(R.id.percent);
            addtobag=(TextView)itemView.findViewById(R.id.addtobag);

        }
    }
}
