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
import com.dileep.myapplication.pojos.OrderListPojo;
import com.dileep.myapplication.pojos.UploadProductPojo;


import java.util.ArrayList;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductsHolder>{

    ArrayList<UploadProductPojo> productPojoArrayList;
    Context context;
    RequestOptions requestOptions;

    public ProductsAdapter(ArrayList<UploadProductPojo> productPojoArrayList, Context context) {
        this.productPojoArrayList = productPojoArrayList;
        this.context = context;
        requestOptions=new RequestOptions();
        requestOptions.centerCrop();
    }

    @NonNull
    @Override
    public ProductsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list_item,parent,false);
       return new ProductsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsHolder holder, int position) {
        final UploadProductPojo productPojo=productPojoArrayList.get(position);
        holder.name.setText(productPojo.getProductName());
        holder.cost.setText("Rs "+productPojo.getProductCost());
        Glide.with(context)
                .load(productPojo.getImageUrls().get(0))
                .apply(requestOptions)
                .into(holder.imageView);

/*        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle=new Bundle();
                bundle.putStringArrayList("images",productPojo.getImageUrls());
                bundle.putString("name",productPojo.getProductName());
                bundle.putString("cost",productPojo.getProductCost());
                bundle.putString("number",productPojo.getWhatsAppNumber());
                bundle.putString("id",productPojo.getProductId());
                Intent displayIntent=new Intent(context, DisplayActivity.class);
                displayIntent.putExtras(bundle);
                context.startActivity(displayIntent);

            }
        });*/
        holder.addtobag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int addposition= Appconst.orderListPojoArrayList.size();
                Appconst.orderListPojoArrayList.add(addposition,new OrderListPojo(productPojo.getImageUrls().get(0),productPojo.getProductName(),productPojo.getProductCost()));
                Toast.makeText(context, "item aded to bag", Toast.LENGTH_SHORT).show();
            }
        });



    }

    @Override
    public int getItemCount() {
        return productPojoArrayList.size();
    }

    public class ProductsHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView name, cost,percent,addtobag;
        public ProductsHolder(@NonNull View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView1);
            name = (TextView) itemView.findViewById(R.id.item_name);
            cost = (TextView) itemView.findViewById(R.id.item_cost);
            percent=(TextView)itemView.findViewById(R.id.percent);
            addtobag=(TextView)itemView.findViewById(R.id.addtobag);
        }
    }
}
